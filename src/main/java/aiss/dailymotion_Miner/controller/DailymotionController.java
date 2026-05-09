package aiss.dailymotion_Miner.controller;

import aiss.dailymotion_Miner.model.dailymotion.*;
import aiss.dailymotion_Miner.model.videominer.*;
import aiss.dailymotion_Miner.service.*;
import aiss.dailymotion_Miner.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dailymotion")
public class DailymotionController {

    private final ChannelService channelService;
    private final VideoService videoService;
    private final CaptionService captionService;
    private final TagService tagService;
    private final RestTemplate restTemplate;

    @Value("${videominer.uri}")
    private String videoMinerUri;

    @Autowired
    public DailymotionController(ChannelService channelService,
                                 VideoService videoService,
                                 CaptionService captionService,
                                 TagService tagService,
                                 RestTemplate restTemplate) {
        this.channelService = channelService;
        this.videoService = videoService;
        this.captionService = captionService;
        this.tagService = tagService;
        this.restTemplate = restTemplate;
    }

    // ========== GET para pruebas ==========
    @GetMapping("/{channelId}")
    public ResponseEntity<VMChannel> getChannel(
            @PathVariable String channelId,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxPages) {

        Channel channel = channelService.getChannel(channelId);
        if (channel == null) {
            throw new ChannelNotFoundException(channelId);
        }

        VideoResponse videoResponse = videoService.getChannelVideos(channelId, maxPages);
        VMChannel vmChannel = convertToVMChannel(channel, videoResponse);

        return ResponseEntity.ok(vmChannel);
    }

    // ========== POST que envía a VideoMiner ==========
    @PostMapping("/{channelId}")
    public ResponseEntity<VMChannel> processChannel(
            @PathVariable String channelId,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxPages) {

        Channel channel = channelService.getChannel(channelId);
        if (channel == null) {
            throw new ChannelNotFoundException(channelId);
        }

        VideoResponse videoResponse = videoService.getChannelVideos(channelId, maxPages);
        VMChannel vmChannel = convertToVMChannel(channel, videoResponse);

        try {
            VMChannel createdChannel = restTemplate.postForObject(videoMinerUri, vmChannel, VMChannel.class);
            return ResponseEntity.ok(createdChannel);
        } catch (RestClientException e) {
            throw new VideoMinerApiException("No se pudo enviar el canal a VideoMiner: " + e.getMessage(), e);
        }
    }

    // ========== Métodos de conversión ==========
    private VMChannel convertToVMChannel(Channel channel, VideoResponse videoResponse) {

        String description = channel.getDescription() != null ? channel.getDescription() : "";
        String createdTime = channel.getCreatedTime() != null ? channel.getCreatedTime() : "";

        VMChannel vmChannel = new VMChannel(channel.getId(), channel.getName(), description, createdTime);

        if (videoResponse != null && videoResponse.getList() != null) {
            List<VMVideo> vmVideos = new ArrayList<>();

            for (Video video : videoResponse.getList()) {
                VMVideo vmVideo = convertToVMVideo(video);
                vmVideos.add(vmVideo);
            }

            vmChannel.setVideos(vmVideos);
        } else {
            vmChannel.setVideos(new ArrayList<>());
        }

        return vmChannel;
    }

    private VMVideo convertToVMVideo(Video video) {

        String description = video.getDescription() != null ? video.getDescription() : "";
        String releaseTime = video.getCreatedTime() != null ? video.getCreatedTime() : "";

        VMVideo vmVideo = new VMVideo(video.getId(), video.getTitle(), description, releaseTime);

        // 1. Añadir tags (hashtags del video) como comentarios
        try {
            Tag tag = tagService.getTags(video.getId());
            String videoCreatedTime = video.getCreatedTime();  // Fecha del video

            if (tag != null && tag.getHashtags() != null && !tag.getHashtags().isEmpty()) {
                List<VMComment> vmComments = tag.getHashtags().stream()
                        .map(hashtag -> convertHashtagToVMComment(hashtag, videoCreatedTime))
                        .collect(Collectors.toList());
                vmVideo.setComments(vmComments);
            } else {
                vmVideo.setComments(new ArrayList<>());
            }
        } catch (Exception e) {
            vmVideo.setComments(new ArrayList<>());
        }

        // 2. Añadir captions
        try {
            CaptionResponse captionResponse = captionService.getCaptions(video.getId());
            if (captionResponse != null && captionResponse.getList() != null && !captionResponse.getList().isEmpty()) {
                List<VMCaption> vmCaptions = captionResponse.getList().stream()
                        .map(this::convertToVMCaption)
                        .collect(Collectors.toList());
                vmVideo.setCaptions(vmCaptions);
            } else {
                vmVideo.setCaptions(new ArrayList<>());
            }
        } catch (Exception e) {
            vmVideo.setCaptions(new ArrayList<>());
        }

        // 3. Añadir usuario (owner)
        if (video.getOwner() != null && !video.getOwner().isEmpty()) {
            VMUser vmUser = new VMUser();
            vmUser.setId(UUID.randomUUID().toString());  // ID único
            vmUser.setName(video.getOwner());
            vmUser.setUser_link("https://www.dailymotion.com/" + video.getOwner());
            vmUser.setPicture_link("");
            vmVideo.setUser(vmUser);
        }

        return vmVideo;
    }

    // Convierte un hashtag a VMComment con la fecha del video
    private VMComment convertHashtagToVMComment(String hashtag, String videoCreatedTime) {
        String id = "tag_" + hashtag.replace("#", "");
        String text = hashtag;
        String createdOn = videoCreatedTime;  // ← Usa la fecha del video

        VMComment vmComment = new VMComment(id, text, createdOn);
        return vmComment;
    }

    private VMCaption convertToVMCaption(Caption caption) {
        String language = caption.getLanguage() != null ? caption.getLanguage() : "unknown";
        String url = caption.getUrl() != null ? caption.getUrl() : "";

        VMCaption vmCaption = new VMCaption(caption.getId(), url, language);
        return vmCaption;
    }
}