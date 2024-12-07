package szulowski.michal.capstone.messagingwithrabbitmq.message_producer.metrics.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import szulowski.michal.capstone.messagingwithrabbitmq.message_producer.metrics.model.domain.MetricMessage;
import szulowski.michal.capstone.messagingwithrabbitmq.message_producer.util.json.JsonMapper;

import java.util.Random;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DefaultMetricService implements MetricService {

    private static final int MESSAGE_COUNT = 100;

    @Value("${rabbit.producer.queue}")
    private String queueName;
    private final JsonMapper jsonMapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishMetrics() {
        generateMetrics()
                .map(jsonMapper::toJson)
                .forEach(metricMessage -> rabbitTemplate.convertAndSend(queueName, metricMessage));
    }

    private Stream<MetricMessage> generateMetrics() {
        return Stream.generate(this::generateMetric).limit(MESSAGE_COUNT);
    }

    private MetricMessage generateMetric() {
        int value = new Random().nextInt();
        return new MetricMessage(value);
    }

}
