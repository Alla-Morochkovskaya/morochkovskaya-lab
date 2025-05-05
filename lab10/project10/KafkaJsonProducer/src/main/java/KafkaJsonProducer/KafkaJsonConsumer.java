package KafkaJsonProducer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.*;
import java.time.Duration;
import java.util.*;

public class KafkaJsonConsumer {
    private static final String TOPIC = "user_actions";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "json-consumer-group";
    private static final String DB_URL = "jdbc:sqlite:messages.db";

    public static void main(String[] args) {
        // Установим имя консюмера через аргументы
        String consumerName = args.length > 0 ? args[0] : "Консюмер";

        // Подключение к базе данных SQLite
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            createTableIfNotExists(connection);

            // Конфигурация консюмера
            Properties consumerProps = new Properties();
            consumerProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            consumerProps.put("group.id", GROUP_ID);
            consumerProps.put("key.deserializer", StringDeserializer.class.getName());
            consumerProps.put("value.deserializer", StringDeserializer.class.getName());
            consumerProps.put("auto.offset.reset", "earliest");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
            consumer.subscribe(Collections.singletonList(TOPIC));

            System.out.println(consumerName + " запущен, ожидаем сообщения...");

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    processMessage(connection, record, consumerName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

    private static void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS messages ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "user_id INTEGER, "
                + "action TEXT, "
                + "timestamp TEXT, "
                + "raw_message TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    private static void processMessage(Connection connection, ConsumerRecord<String, String> record, String consumerName) {
        try {
            String message = record.value();
            Map<String, Object> parsedMessage = parseJson(message);

            // Сохраняем данные в базу данных
            saveToDatabase(connection, parsedMessage, message);

            System.out.println("[" + consumerName + "]: Сообщение обработано и сохранено: " + message);
        } catch (Exception e) {
            System.err.println("[" + consumerName + "]: Ошибка обработки сообщения: " + e.getMessage());
        }
    }

    private static Map<String, Object> parseJson(String jsonMessage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonMessage, Map.class);
    }

    private static void saveToDatabase(Connection connection, Map<String, Object> parsedMessage, String rawMessage) throws SQLException {
        String insertSQL = "INSERT INTO messages (user_id, action, timestamp, raw_message) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, parsedMessage.getOrDefault("user_id", null));
            preparedStatement.setString(2, parsedMessage.getOrDefault("action", "").toString());
            preparedStatement.setString(3, parsedMessage.getOrDefault("timestamp", "").toString());
            preparedStatement.setString(4, rawMessage);
            preparedStatement.executeUpdate();
        }
    }
}
