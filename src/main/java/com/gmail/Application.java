package com.gmail;


import com.gmail.config.JavaConfig;
import com.gmail.dao.UserDao;
import com.gmail.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;


/**
 * Start application class
 *
 * @author Oleksii Chernii
 * @version 1.0
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
