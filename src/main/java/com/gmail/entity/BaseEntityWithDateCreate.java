package com.gmail.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Created by Space on 04.05.2019.
 */
@Data
public class BaseEntityWithDateCreate extends BaseEntity {
    private LocalDateTime dateCreate;

    public BaseEntityWithDateCreate() {
        this.dateCreate = LocalDateTime.now();
    }
}
