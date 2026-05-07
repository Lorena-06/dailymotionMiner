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

    // Obtener videos de un canal (con paginación)
    public VideoResponse getChannelVideos(String channelId, int maxVideos, int maxPages) {
        int limit = Math.min(maxVideos, 100); // Dailymotion max es 100
        int page = Math.min(maxPages, 10);    // Limitar páginas
        String url = baseUri + "/channel/" + channelId + "/videos?limit=" + limit + "&page=" + page;
        return restTemplate.getForObject(url, VideoResponse.class);
    }
}