package szulowski.michal.capstone.messagingwithrabbitmq.message_consumer.metrics.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetricsListener {

    @RabbitListener(queues = "#{@environment.getProperty('rabbit.consumer.queue')}")
    public void onMessage(String message) {
        log.info("Received metrics:\n{}", message);
    }

}
