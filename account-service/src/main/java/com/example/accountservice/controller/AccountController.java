package com.example.accountservice.controller;

import com.example.accountservice.model.AccountDTO;
import com.example.accountservice.model.MessageDTO;
import com.example.accountservice.model.StatisticDTO;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.MessageRepository;
import com.example.accountservice.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @PostMapping("/new")
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        StatisticDTO statisticDTO = new StatisticDTO();
        //set statistic
        statisticDTO.setMessage("Account " + accountDTO.getEmail() + " is created ");
        statisticDTO.setCreatedDate(new Date());
        statisticDTO.setStatus(false);
        //set notification
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(accountDTO.getEmail());
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Kafka");
        messageDTO.setContent("welcome to kafka course.");
        messageDTO.setStatus(false);

        //save db
        accountRepository.save(accountDTO);
        messageRepository.save(messageDTO);
        statisticRepository.save(statisticDTO);

        //send notification
//        for (int i = 0; i < 100; i++) {
//            kafkaTemplate.send("notification", messageDTO).addCallback(new KafkaSendCallback<String, Object>() {
//
//                @Override
//                public void onSuccess(SendResult<String, Object> result) {
//                    //handle success
//                    System.out.println(result.getRecordMetadata().partition());
//
//                }
//
//                @Override
//                public void onFailure(KafkaProducerException e) {
//                    //handle fail, save db event failed
//                    e.printStackTrace();
//                }
//            });
//        }
//        //send statistic
//        kafkaTemplate.send("statistic", statisticDTO);

        return accountDTO;
    }
}
