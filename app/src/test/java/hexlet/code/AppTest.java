package hexlet.code;

import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;

import static org.assertj.core.api.Assertions.assertThat;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public final class AppTest {
    private static Javalin app;
    private static MockWebServer mockServer;

    @BeforeEach
    public void startServer() throws IOException, SQLException {
        app = App.getApp();
    }

    private static Path getFixturePath() {
        return Paths.get("src", "test", "resources", "fixtures", "tested_page.html")
                .toAbsolutePath().normalize();
    }

    private static String readFixture() throws IOException {
        Path filePath = getFixturePath();
        return Files.readString(filePath).trim();
    }

    @BeforeAll
    public static void startMockServer() throws IOException {
        mockServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse()
                .setBody(readFixture());
        mockServer.enqueue(mockResponse);
        mockServer.start();
    }

    @AfterAll
    public static void stopMockServer() throws IOException {
        mockServer.shutdown();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        }));
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        }));
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, ((server, client) -> {
            var requestBody = "name=urlname";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains("urlname"));
        }));
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.delete("/urls/00000");
            client.get("/urls/00000");
            assertThat(response.code()).isEqualTo(404);
        }));
    }

    @Test
    public void testCheck() {
        String testUrl = mockServer.url("/").toString().replaceAll("/$", "");
        JavalinTest.test(app, ((server, client) -> {
            var requestBody = "url=" + testUrl;

            // Проверка создания URL
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);

            var actualUrl = UrlRepository.findByName(testUrl).orElse(null);
            assertThat(actualUrl).as("URL should be found").isNotNull();

            // Проверка проверки URL
            client.post("/urls/" + actualUrl.getId() + "/checks");
            response = client.get("/urls/" + actualUrl.getId());
            assertThat(response.code()).isEqualTo(200);

            String responseBody = response.body().string();
            assertThat(responseBody).contains(testUrl);

            // Получение последних проверок URL
            var urlCheck = UrlCheckRepository.findLatestChecks().get(actualUrl.getId());
            assertThat(urlCheck).as("URL check should exist").isNotNull();

            // Проверка параметров ответа
            assertThat(urlCheck.getStatusCode()).as("Check response status code").isEqualTo(200);
            assertThat(urlCheck.getTitle()).as("Check title").isEqualTo("Test title");
            assertThat(urlCheck.getH1()).as("Check h1").isEqualTo("Test h1");
            assertThat(urlCheck.getDescription()).as("Check description").isEqualTo("Test description");
        }));
    }
}
