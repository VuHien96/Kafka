package com.example.accountservice.service;

import com.example.accountservice.model.MessageDTO;
import com.example.accountservice.model.StatisticDTO;
import com.example.accountservice.repository.MessageRepository;
import com.example.accountservice.repository.StatisticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PollingService {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Scheduled(fixedDelay = 1000)
    public void producer() {

        List<MessageDTO> messageDTOS = messageRepository.findByStatus(false);
        for (MessageDTO messageDTO : messageDTOS) {
            kafkaTemplate.send("notification", messageDTO).addCallback(new KafkaSendCallback<String, Object>() {
                @Override
                public void onSuccess(SendResult<String, Object> result) {
                    //handle success
                    log.info("Success messageDTO");
                    messageDTO.setStatus(true);
                    messageRepository.save(messageDTO);
                }

                @Override
                public void onFailure(KafkaProducerException e) {
                    //handle fail, save db event failed
                    log.info("FAIL: {}", e.getMessage());
                }
            });
        }

        List<StatisticDTO> statisticDTOS = statisticRepository.findByStatus(false);
        for (StatisticDTO statisticDTO : statisticDTOS) {
            kafkaTemplate.send("notification", statisticDTO).addCallback(new KafkaSendCallback<String, Object>() {
                @Override
                public void onSuccess(SendResult<String, Object> result) {
                    //handle success
                    log.info("Success statisticDTO");
                    statisticDTO.setStatus(true);
                    statisticRepository.save(statisticDTO);
                }

                @Override
                public void onFailure(KafkaProducerException e) {
                    //handle fail, save db event failed
                    log.info("FAIL: {}", e.getMessage());
                }
            });
        }

    }

    @Scheduled(fixedDelay = 5000)
    public void delete() {
        List<MessageDTO> messageDTOS = messageRepository.findByStatus(true);
        messageRepository.deleteAllInBatch(messageDTOS);
        List<StatisticDTO> statisticDTOS = statisticRepository.findByStatus(true);
        statisticRepository.deleteAllInBatch(statisticDTOS);
    }

}
