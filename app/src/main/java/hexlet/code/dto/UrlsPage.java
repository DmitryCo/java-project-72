package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasicPage {
    private List<Url> allUrls;
    private Map<Long, UrlCheck> latestChecks;
}
