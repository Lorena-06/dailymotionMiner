package aiss.dailymotion_Miner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

@Service
public class TagsService {

    private final RestTemplate restTemplate;

    @Value("${dailymotion.baseuri}")
    private String baseUri;

    @Autowired
    public TagsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Obtener tags de un video (los tags actúan como comentarios simplificados)
    public List<String> getTags(String videoId) {
        try {
            String url = baseUri + "/video/" + videoId + "?fields=tags";
            // Necesitas crear una clase TagsResponse o manejar la respuesta
            // Por ahora devolvemos lista vacía
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}