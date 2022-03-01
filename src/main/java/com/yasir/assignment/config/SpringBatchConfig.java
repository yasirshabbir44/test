package com.yasir.assignment.config;

import com.yasir.assignment.component.FileDeletingTasklet;
import com.yasir.assignment.component.JobCompletionNotificationListener;
import com.yasir.assignment.component.ProductItemProcessor;
import com.yasir.assignment.entity.Product;
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

    private final String[] fieldNames = new String[] {"name", "short_name", "price"};

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${inputFile:products.csv}")
    private Resource resource;

    @Bean
    public Job importProductJob(JobCompletionNotificationListener listener, Step step1, Step step2,
                                JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("importProductJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Product> writer, ItemReader<Product> itemReader, TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("step1-for-read-process-write")
                .<Product, Product> chunk(100)
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
    public FlatFileItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("ProductItemReader")
                .resource(resource)
                .linesToSkip(1)
                .delimited()
                .names(fieldNames)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
                    setTargetType(Product.class);
                }})
                .build();
    }

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO product (name, short_name, price) VALUES (:name, :shortName, :price)")
                .dataSource(dataSource)
                .build();
    }
}
