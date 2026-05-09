package aiss.dailymotion_Miner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import aiss.dailymotion_Miner.model.dailymotion.VideoResponse;

@Service
public class VideoService {

    private final RestTemplate restTemplate;

    @Value("${dailymotion.baseuri}")
    private String baseUri;

    @Autowired
    public VideoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public VideoResponse getChannelVideos(String channelId, int maxPages) {
        int videosPerPage = 10;  // Dailymotion default limit
        int totalVideos = videosPerPage * maxPages;

        String url = baseUri + "/playlist/" + channelId + "/videos?limit=" + totalVideos +
                "&fields=id,title,channel,owner,description,created_time";

        return restTemplate.getForObject(url, VideoResponse.class);
    }
}