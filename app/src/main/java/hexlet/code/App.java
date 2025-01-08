package hexlet.code;

import hexlet.code.controller.MainPageController;
import hexlet.code.controller.UrlPageController;
import hexlet.code.controller.UrlsController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.Routes;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    private static int getPort() {
        String port = System.getenv().getOrDefault(
                "PORT",
                "7070");
        return Integer.parseInt(port);
    }

    private static String getDatabaseUrl() {
        return System.getenv().getOrDefault(
                "JDBC_DATABASE_URL",
                "jdbc:h2:mem:project");
    }

    private static String readResourceFile() throws IOException, URISyntaxException {
        var resource = App.class.getClassLoader().getResource("schema.sql");
        if (resource == null) {
            throw new IOException("Resource not found: " + "schema.sql");
        }
        Path path = Paths.get(resource.toURI());
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver(
                "templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static Javalin getApp() throws IOException, SQLException, URISyntaxException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        String sql = readResourceFile();
        log.info(sql);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(javalinConfig -> {
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.fileRenderer(new JavalinJte(createTemplateEngine()));
        });
        app.get(Routes.mainPath(), MainPageController::welcomeMain);
        app.post(Routes.urlsPath(), UrlsController::createUrl);
        app.get(Routes.urlsPath(), UrlsController::showUrls);
        app.get(Routes.urlPath("{id}"), UrlPageController::showUrlPage);
        app.post(Routes.urlChecks("{id}"), UrlPageController::urlCheck);
        return app;
    }

    public static void main(String[] args) throws IOException, SQLException, URISyntaxException {
        var app = getApp();
        app.start(getPort());
    }
}
