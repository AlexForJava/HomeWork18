package com.gmail.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Base class for entity object with date create *
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
@Data
public class BaseEntityWithDateCreate extends BaseEntity {
    private LocalDateTime dateCreate;

    public BaseEntityWithDateCreate() {
        this.dateCreate = LocalDateTime.now();
    }
}
