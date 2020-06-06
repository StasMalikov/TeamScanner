package teamScanner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EntityManagerService {
    @Value("${spring.datasource.url}")
    String DATABASE_URL;

    @Value("${spring.datasource.username}")
    String DATABASE_USER;

    @Value("${spring.datasource.password}")
    String DATABASE_PASS;

    private Mapper<Long> maxUserIdMapper = resultSet -> resultSet.getLong("id");
    private Mapper<String> userNameMapper = resultSet -> resultSet.getString("login");

    public List<Long> getIdEventsWhereUserExist(Long id) {
        String query = "select id from events join user_event ue on events.id = ue.event_id where user_id = " + id + " and creator_id<>" + id + ";";
        return executeQuery(query, maxUserIdMapper);
    }

    public String getLoginById(Long id) {
        try (
                FileInputStream fis = new FileInputStream("src\\main\\resources\\application.properties");
        ) {
            Properties property = new Properties();
            property.load(fis);
            DATABASE_URL = property.getProperty("spring.datasource.url");
            DATABASE_USER = property.getProperty("spring.datasource.username");
            DATABASE_PASS = property.getProperty("spring.datasource.password");
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        String query = "select login from users where id = " + id + ";";
        List<String> strings = executeQuery(query, userNameMapper);
        if (strings != null && strings.size() > 0)
            return strings.get(0);
        else return null;
    }

    private <R> List<R> executeQuery(String query, Mapper<R> mapper) {
        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(query);
            List<R> items = new ArrayList<>();
            while (resultSet.next()) {
                R item = mapper.map(resultSet);
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
