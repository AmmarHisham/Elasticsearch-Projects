package com.nbc.app.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nbc.app.domain.convgdeal.ConvergenceDeal;
import com.nbc.app.domain.index.ConvergenceIndex;
import com.nbc.app.jobs.ConvergenceProcessor;
import com.nbc.app.jobs.ConvergenceReader;
import com.nbc.app.jobs.ConvergenceWriter;

@Configuration
@EnableBatchProcessing
public class ConvergenceStepConfiguration {

    @Bean
    public Step createIndexStep(
            StepBuilderFactory stepBuilderFactory,
            @Qualifier("convergenceReader") ItemReader<ConvergenceDeal> planReader,
            @Qualifier("convergenceProcessor") ItemProcessor<ConvergenceDeal,ConvergenceIndex> convergenceIndexProcessor,
            @Qualifier("convergenceWriter") ConvergenceWriter indexWriter) {

        return stepBuilderFactory.get("createIndexStep")
        		.<ConvergenceDeal, ConvergenceIndex> chunk(5000)
                .reader(planReader)
                .processor(convergenceIndexProcessor)
                .writer(indexWriter)
                .build();
    }

    @Bean(name = "convergenceReader")
    public ItemReader<ConvergenceDeal> planReader() {
        return new ConvergenceReader();
    }

    @Bean(name = "convergenceProcessor")
    public ItemProcessor<ConvergenceDeal, ConvergenceIndex> planIndexProcessor() {
        return new ConvergenceProcessor();
    }

    @Bean(name = "convergenceWriter")
    public ConvergenceWriter indexWriter() {
        return new ConvergenceWriter();
    }  
    

}
