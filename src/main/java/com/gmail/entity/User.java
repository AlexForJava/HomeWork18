package com.gmail.entity;

import com.gmail.annotations.InjectRandomInt;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * User entity object
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
@Data
@Component
public class User extends BaseEntityWithDateCreate {
    private String password;
    private LocalDateTime birthdayDate;

    @InjectRandomInt(min = 2, max = 20)
    private Integer randomInt;
}
