package dk.emilmadsen.barkalert.model.discord;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EmbedObject {

    private String title;
    private String description;
    private String url;
//    private Color color;
    private Footer footer;
    private Thumbnail thumbnail;
    private Image image;
    private Author author;
    private java.util.List<Field> fields = new ArrayList<>();

    public EmbedObject addField(String name, String value, boolean inline) {
        this.fields.add(new Field(name, value, inline));
        return this;
    }
}
