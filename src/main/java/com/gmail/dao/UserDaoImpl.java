package com.gmail.dao;

import com.gmail.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 *
 */
@Component
public class UserDaoImpl implements UserDao {
    private static final String SQL_INSERT_USER = "INSERT INTO users (name, password, date_create, birthday_date, random_int) VALUES(:name,:password,:date_create,:birthday_date,:random_int)";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id=:id";
    private static final String SQL_UPDATE_USER = "UPDATE users SET name=:name, password=:password, date_create=:date_create,birthday_date=:bithday_date,random_int=:random_int WHERE id=:id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id=:id";
    private static final String SQL_SELECT_ALL = "SELECT * FROM users";
    private static final String SQL_SELECT_ALL_BY_DATE_CREATE = "SELECT * FROM users WHERE date_create=:date_create";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<User> rowMapper = (resultSet, rowNumber) -> {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        user.setRandomInt(resultSet.getInt("random_int"));
        user.setDateCreate(new Timestamp(resultSet.getLong("date_create")).toLocalDateTime());
        user.setBirthdayDate(new Timestamp(resultSet.getLong("birthday_date")).toLocalDateTime());
        return user;
    };

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public void insertUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("date_create", user.getDateCreate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .addValue("birthday_date", user.getBirthdayDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .addValue("random_int", user.getRandomInt());
        namedParameterJdbcTemplate.update(SQL_INSERT_USER, params);
    }

    @Override
    public User getUserById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, params, rowMapper);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("date_create", user.getDateCreate())
                .addValue("birthday_date", user.getBirthdayDate())
                .addValue("random_int", user.getRandomInt());
        namedParameterJdbcTemplate.update(SQL_UPDATE_USER, params);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcTemplate.update(SQL_DELETE_BY_ID, params);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        deleteUserById(user.getId());
    }

    @Override
    public List<User> getAllUsers() {
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public List<User> getUsersByDateCreate(LocalDateTime dateCreate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("date_create", dateCreate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL_BY_DATE_CREATE, params, rowMapper);
    }
}
