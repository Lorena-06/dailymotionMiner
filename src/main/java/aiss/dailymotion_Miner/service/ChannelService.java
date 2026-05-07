package aiss.dailymotion_Miner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import aiss.dailymotion_Miner.model.dailymotion.Channel;

@Service
public class ChannelService {

    private final RestTemplate restTemplate;
    
    @Value("${dailymotion.baseuri}")
    private String baseUri;

    @Autowired
    public ChannelService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Obtener un canal por ID
    public Channel getChannel(String channelId) {
        String url = baseUri + "/channel/" + channelId;
        return restTemplate.getForObject(url, Channel.class);
    }
}