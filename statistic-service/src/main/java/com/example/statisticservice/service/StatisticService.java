package com.example.statisticservice.service;

import com.example.statisticservice.entity.Statistic;
import com.example.statisticservice.repository.StatisticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        log.info("Received: {}", statistic.getMessage());
        statisticRepository.save(statistic);
    }

}
