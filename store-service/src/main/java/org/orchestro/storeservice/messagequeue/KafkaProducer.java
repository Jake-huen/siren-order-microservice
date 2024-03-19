package org.orchestro.storeservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orchestro.storeservice.dto.OrderReceiptFromStoreDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderReceiptFromStoreDto send(String topic, OrderReceiptFromStoreDto orderReceiptFromStoreDto) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = mapper.writeValueAsString(orderReceiptFromStoreDto);
        } catch (JsonProcessingException exception) {
            log.info("머야 왜 JSON으로 안바껴요!!");
            exception.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the Order microservice: " + orderReceiptFromStoreDto);

        return orderReceiptFromStoreDto;
    }
}
