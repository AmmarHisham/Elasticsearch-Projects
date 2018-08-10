package com.nbc.app.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nbc.app.jobs.ConvergenceJobListener;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class ConvergenceJobConfiguration {

    @Bean
    public Job convergenceJob(JobBuilderFactory jobs,Step createIndexStep, ConvergenceJobListener convergenceJobJobListener){
    	return jobs.get("convegenceJob")
                    .incrementer(new RunIdIncrementer())
                    .listener(convergenceJobJobListener)
                    .flow(createIndexStep)
                    .end()
                    .build();
    }

}
