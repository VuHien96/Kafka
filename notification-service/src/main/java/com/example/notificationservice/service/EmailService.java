package com.example.notificationservice.service;

import com.example.notificationservice.model.MessageDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(MessageDTO messageDTO);
}
