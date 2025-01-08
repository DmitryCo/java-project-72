package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls(name, created_at) VALUES(?,?)";
        LocalDateTime dateTime = LocalDateTime.now();
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.setObject(2, dateTime);
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
                url.setCreatedAt(dateTime);
            } else {
                throw new SQLException("Ошибка при сохранении");
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Url> result = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getObject("created_at", LocalDateTime.class);
                Url url = new Url(name);
                url.setId(id);
                url.setCreatedAt(dateTime);
                result.add(url);
            }
            return result;
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getObject("created_at", LocalDateTime.class);
                long urlId = resultSet.getLong("id");
                Url url = new Url(name);
                url.setId(urlId);
                url.setCreatedAt(dateTime);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> findByName(String urlName) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, urlName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getObject("created_at", LocalDateTime.class);
                long id = resultSet.getLong("id");
                Url url = new Url(name);
                url.setId(id);
                url.setCreatedAt(dateTime);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }
}
