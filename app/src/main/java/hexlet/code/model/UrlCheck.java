package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UrlCheck {
    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private Long urlId;
    private LocalDateTime createdAt;

    public UrlCheck(Long urlId, int statusCode, String title, String h1, String description) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
    }
}
