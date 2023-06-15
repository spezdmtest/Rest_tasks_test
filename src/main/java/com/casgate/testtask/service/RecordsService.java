package com.casgate.testtask.service;

import com.casgate.testtask.entity.RecordEntity;
import com.casgate.testtask.repository.RecordRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecordsService {

    private RecordRepository recordRepository;

    public List<RecordEntity> findAll() {
        return recordRepository.findAll();
    }
}
