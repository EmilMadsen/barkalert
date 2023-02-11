package dk.emilmadsen.barkalert.model.discord;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestObject {
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private List<EmbedObject> embeds = new ArrayList<>();
}
