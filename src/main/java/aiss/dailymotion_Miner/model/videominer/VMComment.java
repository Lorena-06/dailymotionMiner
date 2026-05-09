package aiss.dailymotion_Miner.model.videominer;

public class VMComment {
    private String id;
    private String text;
    private String createdOn;
    private String releaseTime;

    public VMComment(String id, String text, String createdOn) {
        this.id = id;
        this.text = text;
        this.createdOn = createdOn;
       
    }

    public String getId() { return id; }

    public void setId(String id) {this.id = id; }

    public String getText() { return text; }

    public void setText(String text) {this.text = text; }

    public String getCreatedOn() { return createdOn; }

    public void setCreatedOn(String createdOn) {this.createdOn = createdOn; }

   

}
