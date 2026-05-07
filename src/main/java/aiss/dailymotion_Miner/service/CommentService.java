package aiss.dailymotion_Miner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {

    private final RestTemplate restTemplate;

    @Autowired
    public CommentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getComments(String videoId, int maxResults) {
        // Por ahora devolvemos null
        return null;
    }
}