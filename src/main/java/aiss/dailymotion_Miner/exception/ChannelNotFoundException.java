package aiss.dailymotion_Miner.exception;

public class ChannelNotFoundException extends RuntimeException {

    private String channelId;

    public ChannelNotFoundException(String channelId) {
        super("Canal no encontrado: " + channelId);
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }
}