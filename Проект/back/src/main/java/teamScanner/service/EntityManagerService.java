package teamScanner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntityManagerService {
    @Value("${spring.datasource.url}")
    String DATABASE_URL;

    @Value("${spring.datasource.username}")
    String DATABASE_USER;

    @Value("${spring.datasource.password}")
    String DATABASE_PASS;

//    private static EntityManagerService instance;
//
//    public static EntityManagerService getInstance() {
//        if (instance == null) {
//            instance = new EntityManagerService();
//        }
//        return instance;
//    }
//
//    private EntityManagerService() {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }
    private Mapper<Long> maxUserIdMapper = resultSet -> resultSet.getLong("id");

    public List<Long> getIdEventsWhereUserExist(Long id) {
        String query = "select id from events join user_event ue on events.id = ue.event_id where user_id = 50 and creator_id<>50;";
        return executeQuery(query, maxUserIdMapper);
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
