package aiss.dailymotion_Miner.exception;

public class DailymotionApiException extends RuntimeException {

    public DailymotionApiException(String message) {
        super(message);
    }

    public DailymotionApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
