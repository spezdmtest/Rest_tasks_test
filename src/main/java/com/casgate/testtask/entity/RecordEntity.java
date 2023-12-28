package com.casgate.testtask.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RECORD")
public class RecordEntity {
    @Id
    private long id;
    private String title;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime lastRead;
    private long userId;
}
