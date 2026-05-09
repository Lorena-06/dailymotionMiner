package aiss.dailymotion_Miner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import aiss.dailymotion_Miner.model.dailymotion.Tag;

@Service
public class TagService {

    private final RestTemplate restTemplate;

    @Value("${dailymotion.baseuri}")
    private String baseUri;

    @Autowired
    public TagService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Tag getTags(String videoId) {
        String url = baseUri + "/video/" + videoId + "?fields=hashtags";
        return restTemplate.getForObject(url, Tag.class);
    }
}