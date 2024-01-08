package com.casgate.testtask.service;

import com.casgate.testtask.entity.RecordEntity;
import com.casgate.testtask.repository.RecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RecordsServiceTest {
    @Mock
    private RecordRepository recordRepository;
    @InjectMocks
    private RecordsService recordsService;

    List<RecordEntity> recordEntity = List.of(
            new RecordEntity(1L, "record1", "desc 1",
                    LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1));

    @BeforeEach
    public void SetUpBeforeEach() {
        recordEntity = Mockito.mock(RecordRepository.class).findAll();
        recordsService = new RecordsService(recordRepository);
    }

    @Test
    public void findRecordAll() {
        Mockito.when(recordRepository.findAll()).thenReturn(recordEntity);
        var records = recordsService.findAll();

        Assertions.assertNotNull(records);
        Assertions.assertEquals(recordEntity, records);
    }
}
