package szulowski.michal.capstone.messagingwithrabbitmq.message_producer.metrics.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import szulowski.michal.capstone.messagingwithrabbitmq.message_producer.metrics.model.domain.MetricMessage;
import szulowski.michal.capstone.messagingwithrabbitmq.message_producer.util.json.JsonMapper;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class DefaultMetricService implements MetricService {

    private static final String QUEUE_NAME = "metrics";

    private static final int MESSAGE_COUNT = 100;

    private JsonMapper jsonMapper;
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishMetrics() {
        generateMetrics()
                .map(jsonMapper::toJson)
                .forEach(metricMessage -> rabbitTemplate.convertAndSend(QUEUE_NAME, metricMessage));
    }

    private Stream<MetricMessage> generateMetrics() {
        return Stream.generate(this::generateMetric).limit(MESSAGE_COUNT);
    }

    private MetricMessage generateMetric() {
        int value = new Random().nextInt();
        return new MetricMessage(value);
    }

}
