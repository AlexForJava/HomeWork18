package com.gmail.entity;

import com.gmail.annotations.InjectRandomInt;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by Space on 04.05.2019.
 */
@Data
@Component
public class User extends BaseEntityWithDateCreate {
    private String password;
    private LocalDateTime birthdayDate;

    @InjectRandomInt(min = 2, max = 20)
    private Integer randomInt;
}
