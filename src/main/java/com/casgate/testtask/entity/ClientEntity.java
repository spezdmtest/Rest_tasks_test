package com.casgate.testtask.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "CLIENT")
public class ClientEntity {
    @Id
    private long id;
    private String name;
    private LocalDateTime createDate;
}
