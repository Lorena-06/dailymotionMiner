package aiss.dailymotion_Miner.model.videominer;
import java.util.ArrayList;
import java.util.List;

public class VMVideo {
    private String id;
    private String name;
    private String description;
    private String releaseTime;
    private List<VMComment> comments;
    private List<VMCaption> captions;
    private VMUser user;  // ← NUEVO CAMPO

    // Constructor con parámetros
    public VMVideo(String id, String name, String description, String releaseTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseTime = releaseTime;
        this.comments = new ArrayList<VMComment>();
        this.captions = new ArrayList<VMCaption>();
    }

    // Constructor vacío (necesario para Spring)
    public VMVideo() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReleaseTime() { return releaseTime; }
    public void setReleaseTime(String releaseTime) { this.releaseTime = releaseTime; }

    public List<VMCaption> getCaptions() { return captions; }
    public void setCaptions(List<VMCaption> captions) { this.captions = captions; }

    public List<VMComment> getComments() { return comments; }
    public void setComments(List<VMComment> comments) { this.comments = comments; }

    // NUEVO Getter y Setter para user
    public VMUser getUser() { return user; }
    public void setUser(VMUser user) { this.user = user; }
}