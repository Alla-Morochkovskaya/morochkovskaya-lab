package KafkaJsonProducer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class KafkaJsonProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите JSON-сообщения для отправки в Kafka (или 'exit' для выхода):");

        while (true) {
            System.out.print("> ");
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            ProducerRecord<String, String> record = new ProducerRecord<>("user_actions", message);
            producer.send(record);
            System.out.println("Сообщение отправлено: " + message);
        }

        producer.close();
        scanner.close();
    }
}
