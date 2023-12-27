package com.casgate.testtask.service;

import com.casgate.testtask.entity.RecordEntity;
import com.casgate.testtask.repository.RecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class RecordsService {

    private RecordRepository recordRepository;

    public List<RecordEntity> findAll() {
        return recordRepository.findAll();
    }

    public List<RecordEntity> getRecordsByClientId(long clientId) {

        var recordEntitiesByUserId = recordRepository.findRecordEntitiesByUserId(clientId);
        AtomicInteger counter = new AtomicInteger(0);
        recordEntitiesByUserId.forEach(record -> {
            var currentTime = LocalDateTime.now().plusSeconds(counter.getAndIncrement());
            record.setLastRead(currentTime);
            var oldLastRead = record.getLastRead();
            record.setLastRead(currentTime);
            record.setLastRead(oldLastRead);
            recordRepository.save(record);
        });

        recordEntitiesByUserId.sort(Comparator.comparing(RecordEntity::getLastRead,
                        Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(RecordEntity::getCreateDate));

        return recordEntitiesByUserId;
    }
}
