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

    // Obtener un canal por ID con todos los campos necesarios
    public Channel getChannel(String channelId) {
        // Añadimos ?fields= con los campos que queremos
        String url = baseUri + "/channel/" + channelId
                + "?fields=id,name,description,created_time";
        return restTemplate.getForObject(url, Channel.class);
    }
}