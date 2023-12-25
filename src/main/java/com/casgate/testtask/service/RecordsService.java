package com.casgate.testtask.service;

import com.casgate.testtask.entity.RecordEntity;
import com.casgate.testtask.repository.RecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RecordsService {

    private RecordRepository recordRepository;

    public List<RecordEntity> findAll() {
        return recordRepository.findAll();
    }

    public List<RecordEntity> getRecordsByClientId(long clientId) {
        var currentTime = LocalDateTime.now();
        var recordEntitiesByUserId = recordRepository.findRecordEntitiesByUserId(clientId);

        recordEntitiesByUserId.forEach(record -> {
            record.setLastRead(currentTime);
        });
        return recordEntitiesByUserId;
    }
}
