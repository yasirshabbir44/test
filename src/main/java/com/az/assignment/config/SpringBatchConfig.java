package com.az.assignment.config;

import com.az.assignment.component.FileDeletingTasklet;
import com.az.assignment.component.JobCompletionNotificationListener;
import com.az.assignment.component.UserItemProcessor;
import com.az.assignment.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {

    private final String[] fieldNames = new String[] {"first_name", "last_name", "age"};

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${inputFile:users.csv}")
    private Resource resource;

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1, Step step2,
                             JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<User> writer, ItemReader<User> itemReader, TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("step1-for-read-process-write")
                .<User, User> chunk(100)
                .reader(itemReader)
                .processor(processor())
                .writer(writer)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step step2() {
        FileDeletingTasklet task = new FileDeletingTasklet();
        task.setResources(resource);
        return stepBuilderFactory.get("step2-for-deleting-file")
                .tasklet(task)
                .build();
    }

    @Bean
    public FlatFileItemReader<User> reader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("UserItemReader")
                .resource(resource)
                .linesToSkip(1)
                .delimited()
                .names(fieldNames)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                    setTargetType(User.class);
                }})
                .build();
    }

    @Bean
    public UserItemProcessor processor() {
        return new UserItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<User> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<User>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO user (first_name, last_name, age) VALUES (:firstName, :lastName, :age)")
                .dataSource(dataSource)
                .build();
    }
}
