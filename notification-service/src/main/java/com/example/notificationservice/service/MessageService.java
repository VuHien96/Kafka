package com.example.notificationservice.service;

import com.example.notificationservice.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MessageDTO messageDTO) {
        log.info("Received data='{}'", messageDTO.getTo());
        emailService.sendEmail(messageDTO);
    }
}
