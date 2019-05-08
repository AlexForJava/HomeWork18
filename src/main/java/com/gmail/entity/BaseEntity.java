package com.gmail.entity;

import lombok.Data;

/**
 * Base class for entity object
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
@Data
public class BaseEntity {
    private Long id;
    private String name;
}
