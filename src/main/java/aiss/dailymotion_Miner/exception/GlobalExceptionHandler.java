package aiss.dailymotion_Miner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Canal no encontrado (404)
    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleChannelNotFound(ChannelNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Canal no encontrado");
        response.put("message", ex.getMessage());
        response.put("channelId", ex.getChannelId());
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 2. Error de la API de Dailymotion (502)
    @ExceptionHandler(DailymotionApiException.class)
    public ResponseEntity<Map<String, Object>> handleDailymotionApi(DailymotionApiException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_GATEWAY.value());
        response.put("error", "Error al obtener datos de Dailymotion");
        response.put("message", ex.getMessage());
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    // 3. Error al enviar a VideoMiner (502)
    @ExceptionHandler(VideoMinerApiException.class)
    public ResponseEntity<Map<String, Object>> handleVideoMinerApi(VideoMinerApiException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_GATEWAY.value());
        response.put("error", "Error al enviar datos a VideoMiner");
        response.put("message", ex.getMessage());
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    // 4. Error 4xx de Dailymotion (404, 400, etc.)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, Object>> handleHttpClientError(HttpClientErrorException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.getStatusCode().value());
        response.put("error", "Error en la petición a Dailymotion");
        response.put("message", ex.getMessage());
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    // 5. Error 5xx de Dailymotion (502, 503, etc.)
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleHttpServerError(HttpServerErrorException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.getStatusCode().value());
        response.put("error", "Dailymotion está teniendo problemas");
        response.put("message", ex.getMessage());
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    // 6. Error genérico de RestClient
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, Object>> handleRestClient(RestClientException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error al comunicarse con API externa");
        response.put("message", ex.getMessage());
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 7. Conexión fallida (503)
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAccess(ResourceAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", "Servicio no disponible");

        if (ex.getMessage().contains("Dailymotion") || ex.getMessage().contains("dailymotion")) {
            response.put("message", "No se pudo conectar con Dailymotion. ¿La API está disponible?");
        } else {
            response.put("message", "No se pudo conectar con VideoMiner. ¿El servicio está corriendo?");
        }
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    // 8. Error genérico (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error interno del servidor");
        response.put("message", ex.getMessage());
        response.put("path", "/dailymotion");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}