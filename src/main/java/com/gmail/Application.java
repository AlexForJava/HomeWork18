package com.gmail;


import com.gmail.config.JavaConfig;
import com.gmail.dao.UserDao;
import com.gmail.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Space on 04.05.2019.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        UserDao userDao = context.getBean(UserDao.class);
        List<User> users = userDao.getAllUsers();
        users.forEach(System.out::println);
        LocalDate localDate = LocalDate.now();
    }
}
