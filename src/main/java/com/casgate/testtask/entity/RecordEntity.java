package com.casgate.testtask.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
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
