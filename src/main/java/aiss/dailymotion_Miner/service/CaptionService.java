package aiss.dailymotion_Miner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import aiss.dailymotion_Miner.model.dailymotion.CaptionResponse;

@Service
public class CaptionService {

    private final RestTemplate restTemplate;

    @Value("${dailymotion.baseuri}")
    private String baseUri;

    @Autowired
    public CaptionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CaptionResponse getCaptions(String videoId) {
        String url = baseUri + "/video/" + videoId + "/subtitles?fields=id,language,url";
        return restTemplate.getForObject(url, CaptionResponse.class);
    }
}