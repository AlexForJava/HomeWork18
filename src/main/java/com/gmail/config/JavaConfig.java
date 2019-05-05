package com.gmail.config;

import com.gmail.annotations.InjectRandomInt;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Configuration Spring class
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
@Log4j
@Configuration
@ComponentScan(basePackages = "com.gmail.")
public class JavaConfig {
    private static final String DRIVER_CLASS_NAME = "driverClassName";
    private static final String URL = "URL";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String SOURCE = "src/main/resources/config.properties";

    /**
     * This method create dataSource connection
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(properties().getProperty(URL));
        dataSource.setUsername(properties().getProperty(USER));
        dataSource.setPassword(properties().getProperty(PASSWORD));
        dataSource.setDriverClassName(properties().getProperty(DRIVER_CLASS_NAME));
        return dataSource;
    }

    /**
     * This method inserts a random number in the field with annotation @InjectRandomInt
     * @return BeanPostProcessor
     */
    @Bean
    public BeanPostProcessor beanPostProcessor() {
        BeanPostProcessor beanPostProcessor = new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                Class beanClass = bean.getClass();
                Field[] fields = beanClass.getFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(InjectRandomInt.class)) {
                        field.setAccessible(true);
                        InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
                        ReflectionUtils.setField(field, bean, getRandomIntInRange(annotation.min(), annotation.max()));
                    }
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
        return beanPostProcessor;
    }

    /**
     * This method load properties from the file
     *
     * @return Properties loaded properties
     */
    private static Properties properties() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE)) {
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            log.error("java config ", e);
        } catch (IOException e) {
            log.error("java config ", e);
        }
        return properties;
    }

    /**
     * This method is used to create a random number
     *
     * @param min random number
     * @param max random number
     * @return
     */
    private int getRandomIntInRange(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }
}
