package com.mycom.SpringAWSLearning.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.mycom.SpringAWSLearning.backend.persistence")
@EnableJpaRepositories(basePackages="com.mycom.SpringAWSLearning.backend.persistence")
@EnableTransactionManagement
public class ApplicationConfig {

}
