package com.gmail.config;

import com.gmail.annotations.InjectRandomInt;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;

/**
 * Configuration Spring class
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
@Log4j
@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = "com.gmail.")
public class JavaConfig {
    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    /**
     * This method create dataSource connection
     *
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    /**
     * This method inserts a random number in the field with annotation @InjectRandomInt
     *
     * @return BeanPostProcessor where in the object injected random number
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
     * This method is used to create a random number
     *
     * @param min random number
     * @param max random number
     * @return int random number between min and max values
     */
    private int getRandomIntInRange(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }
}
