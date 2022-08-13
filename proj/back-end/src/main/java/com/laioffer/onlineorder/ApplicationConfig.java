package com.laioffer.onlineorder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration//定义bean的文件
@EnableWebMvc
public class ApplicationConfig {

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        String PAKAGE_NAME = "com.laioffer.onlineorder.entity";//哪些class是entity，这个项目只用了一个DB，所以只有一个sessionFactory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();//新建factory的bean
        sessionFactory.setDataSource(dataSource());//连接DB，设置factory的各项参数
        sessionFactory.setPackagesToScan(PAKAGE_NAME);//指定entity的名称，hibernate会帮你创建数据库
        sessionFactory.setHibernateProperties(hibernateProperties());//hibernateProperties定义再后面
        return sessionFactory;
    }//创建factory，hibernate连接

    @Bean(name = "dataSource")//datasource的bean
    public DataSource dataSource() {//设置数据库的各项属性
        String RDS_ENDPOINT = "laiproj-database-1.cgrficnacow7.us-east-1.rds.amazonaws.com";
        String USERNAME = "admin";
        String PASSWORD = "";
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //DBname
        dataSource.setUrl("jdbc:mysql://" + RDS_ENDPOINT + ":3306/onlineOrder?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        return hibernateProperties;
    }
}


//里面也有Component，这个类大约就是创建springMVC运行所需要的object，以及其他需要用到的，比如我要用到hashmap，那我就定义在这里面