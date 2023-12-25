package com.casgate.testtask.repository;

import com.casgate.testtask.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
    List<RecordEntity> findRecordEntitiesByUserId(long clientId);
}
