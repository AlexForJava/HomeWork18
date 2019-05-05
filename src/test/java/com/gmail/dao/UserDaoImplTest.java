package com.gmail.dao;

import com.gmail.config.JavaConfig;
import com.gmail.entity.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

public class UserDaoImplTest extends TestCase {
    private static final String SQL_GET_COUNT = "SELECT count(*) FROM users";
    private static final String SQL_GET_NAME = "SELECT name FROM users WHERE id=?";

    private ApplicationContext applicationContext;
    private DataSource dataSource;
    private UserDao userDao;
    private User user;

    @Before
    private void init() {
        applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
        dataSource = applicationContext.getBean(DataSource.class);
        userDao = applicationContext.getBean(UserDao.class);
        user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setBirthdayDate(LocalDateTime.of(1992, 5, 31, 9, 45));
    }

    @Test
    public void testInsertUser() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int before = jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
        userDao.insertUser(user);
        int after = jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
        assertFalse(before == after);
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = userDao.getUserById(1L);
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getPassword());
        assertNotNull(user.getDateCreate());
        assertNotNull(user.getBirthdayDate());
    }

    @Test
    public void testUpdateUser() throws Exception {
        String newName = "update";
        user.setName(newName);
        userDao.updateUser(user);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String nameInDatabase = jdbcTemplate.queryForObject(SQL_GET_NAME, new Object[]{user.getId()}, String.class);
        assertEquals(newName, nameInDatabase);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUsersByDateCreate() throws Exception {
        LocalDateTime dateCreate = user.getDateCreate();
        List<User> users = userDao.getUsersByDateCreate(dateCreate);
        users.forEach(user -> assertEquals(user.getDateCreate(), dateCreate));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int before = jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
        userDao.deleteUserById(2L);
        int after = jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
        assertFalse(before == after);
    }

    @Test
    public void testDeleteUser() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int before = jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
        userDao.deleteUser(user);
        int after = jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
        assertFalse(before == after);
    }
}
