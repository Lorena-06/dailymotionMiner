package aiss.dailymotion_Miner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import aiss.dailymotion_Miner.model.dailymotion.Channel;
import aiss.dailymotion_Miner.exception.ChannelNotFoundException;
import aiss.dailymotion_Miner.exception.DailymotionApiException;

@Service
public class ChannelService {

    private final RestTemplate restTemplate;

    @Value("${dailymotion.baseuri}")
    private String baseUri;

    @Autowired
    public ChannelService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Channel getChannel(String channelId) {
        try {
            String url = baseUri + "/playlist/" + channelId + "?fields=id,name,description,created_time";
            return restTemplate.getForObject(url, Channel.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ChannelNotFoundException(channelId);
        } catch (RestClientException e) {
            throw new DailymotionApiException("Error al obtener el canal '" + channelId + "' desde Dailymotion: " + e.getMessage(), e);
        }
    }
}