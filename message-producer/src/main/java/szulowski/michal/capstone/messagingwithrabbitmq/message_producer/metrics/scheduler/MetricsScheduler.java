package szulowski.michal.capstone.messagingwithrabbitmq.message_producer.metrics.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import szulowski.michal.capstone.messagingwithrabbitmq.message_producer.metrics.service.MetricService;

@Component
@Slf4j
@AllArgsConstructor
public class MetricsScheduler {

    private final MetricService metricService;

    @Scheduled(fixedRate = 10_000)
    public void publishMetrics() {
        log.info("Starting publishing metrics");
        metricService.publishMetrics();
        log.info("Finished publishing metrics");
    }

}
