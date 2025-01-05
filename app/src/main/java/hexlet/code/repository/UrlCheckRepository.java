package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlCheckRepository extends BaseRepository {
    public static void saveUrlCheck(UrlCheck newCheck) throws SQLException {
        String sql = "INSERT INTO url_checks(url_id, status_code, title, h1, description, created_at)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        LocalDateTime dateTime = LocalDateTime.now();
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, newCheck.getUrlId());
            preparedStatement.setInt(2, newCheck.getStatusCode());
            preparedStatement.setString(3, newCheck.getTitle());
            preparedStatement.setString(4, newCheck.getH1());
            preparedStatement.setString(5, newCheck.getDescription());
            preparedStatement.setObject(6, dateTime);
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newCheck.setId(generatedKeys.getLong(1));
                newCheck.setCreatedAt(dateTime);
            } else {
                throw new SQLException("Ошибка при сохранении");
            }
        }
    }

    public static Map<Long, UrlCheck> findLatestChecks() throws SQLException {
        String sql = "SELECT DISTINCT ON (url_id) * from url_checks order by url_id DESC, id DESC";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            var result = new HashMap<Long, UrlCheck>();
            while (resultSet.next()) {
                long urlId = resultSet.getLong("url_id");
                int statusCode = resultSet.getInt("status_code");
                String title = resultSet.getString("title");
                String h1 = resultSet.getString("h1");
                String description = resultSet.getString("description");
                LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
                UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description);
                urlCheck.setCreatedAt(createdAt);
                result.put(urlId, urlCheck);
            }
            return result;
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, urlId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<UrlCheck> result = new ArrayList<>();

            while (resultSet.next()) {
                int statusCode = resultSet.getInt("status_code");
                String title = resultSet.getString("title");
                String h1 = resultSet.getString("h1");
                String description = resultSet.getString("description");
                LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
                UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description);
                urlCheck.setCreatedAt(createdAt);
                result.add(urlCheck);
            }
            return result;
        }
    }
}
