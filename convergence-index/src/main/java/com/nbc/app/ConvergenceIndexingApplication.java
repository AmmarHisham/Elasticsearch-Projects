package com.nbc.app;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableJpaRepositories("com.nbc.app.dao")
public class ConvergenceIndexingApplication extends SpringBootServletInitializer {

	@Autowired
	JobRepository jobRepository;
	@Autowired
	JobRegistry jobRegistry;
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	JobExplorer jobExplorer;

	@Bean
	public JobOperator jobOperator() {
		SimpleJobOperator jobOperator = new SimpleJobOperator();
		jobOperator.setJobExplorer(jobExplorer);
		jobOperator.setJobLauncher(jobLauncher);
		jobOperator.setJobRegistry(jobRegistry);
		jobOperator.setJobRepository(jobRepository);
		return jobOperator;
	}

	public static void main(String args[]) {
		SpringApplication.run(ConvergenceIndexingApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<ConvergenceIndexingApplication> applicationClass = ConvergenceIndexingApplication.class;

}