package aiss.dailymotion_Miner.controller;


//comprobar todos los errores cuando estén creados todos los videominer models

import aiss.dailymotion_Miner.model.dailymotion.*;
import aiss.dailymotion_Miner.model.videominer.*; 
import aiss.dailymotion_Miner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dailymotion")
public class ChannelController {

    private final ChannelService channelService;
    private final VideoService videoService;
    private final CaptionService captionService;
    private final RestTemplate restTemplate;

    @Value("${videominer.uri}")
    private String videoMinerUri;

    @Autowired
    public ChannelController(ChannelService channelService,
                             VideoService videoService,
                             CaptionService captionService,
                             RestTemplate restTemplate) {
        this.channelService = channelService;
        this.videoService = videoService;
        this.captionService = captionService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getChannelVideos(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxPages) {
        
        VideoResponse videos = videoService.getChannelVideos(id, maxVideos, maxPages);
        return ResponseEntity.ok(videos);
    }

    @PostMapping("/{id}")
    public ResponseEntity<VMChannel> processChannel(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxPages) {
        
        Channel channel = channelService.getChannel(id);
        VideoResponse videoResponse = videoService.getChannelVideos(id, maxVideos, maxPages);
        VMChannel vmChannel = convertToVMChannel(channel, videoResponse);
        VMChannel createdChannel = restTemplate.postForObject(videoMinerUri, vmChannel, VMChannel.class);
        
        return ResponseEntity.ok(createdChannel);
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<VMChannel> testConversion(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxPages) {
        
        Channel channel = channelService.getChannel(id);
        VideoResponse videoResponse = videoService.getChannelVideos(id, maxVideos, maxPages);
        VMChannel vmChannel = convertToVMChannel(channel, videoResponse);
        
        return ResponseEntity.ok(vmChannel);
    }

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
        
        VMVideo vmVideo = new VMVideo();
        vmVideo.setId(video.getId());
        vmVideo.setName(video.getTitle());
        vmVideo.setDescription("");
        vmVideo.setReleaseTime("");
        
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
        
        vmVideo.setComments(new ArrayList<>());
        
        return vmVideo;
    }
    
    private VMCaption convertToVMCaption(Caption caption) {
        VMCaption vmCaption = new VMCaption();
        vmCaption.setId(caption.getId());
        vmCaption.setLanguage(caption.getLanguage());
        vmCaption.setLink("");
        return vmCaption;
    }
}