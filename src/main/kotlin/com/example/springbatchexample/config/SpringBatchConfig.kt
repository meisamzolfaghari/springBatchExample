package com.example.springbatchexample.config

import com.example.springbatchexample.entity.User
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.LineTokenizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
class SpringBatchConfig {

    @Bean
    fun job(
        jobBuilderFactory: JobBuilderFactory,
        stepBuilderFactory: StepBuilderFactory,
        itemReader: ItemReader<User>,
        itemProcessor: ItemProcessor<User, User>,
        itemWriter: ItemWriter<User>
    ): Job {

        val step: Step = stepBuilderFactory.get("ETL-file-load")
            .chunk<User, User>(100)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build()

        return jobBuilderFactory.get("ETL-Load")
            .incrementer(RunIdIncrementer())
            .start(step)
            .build();
    }

    @Bean
    fun fileItemReader(
        lineMapper: LineMapper<User>
    ): FlatFileItemReader<User> = FlatFileItemReader<User>().apply {
        setResource(FileSystemResource("/home/meizolf/IdeaProjects/springBatchExample/src/main/resources/users.csv"))
        setName("CSV-Reader")
        setLinesToSkip(1)
        setLineMapper(lineMapper)
    }

    @Bean
    fun lineMapper(): LineMapper<User> {

        val defaultLineMapper: DefaultLineMapper<User> = DefaultLineMapper()

        val lineTokenizer: LineTokenizer = DelimitedLineTokenizer().apply {
            setDelimiter(",")
            setStrict(false)
            setNames("id", "name", "department", "salary")
        }

        val fieldSetMapper: BeanWrapperFieldSetMapper<User> =
            BeanWrapperFieldSetMapper<User>().apply {
                setTargetType(User::class.java)
            }

        defaultLineMapper.setLineTokenizer(lineTokenizer)
        defaultLineMapper.setFieldSetMapper(fieldSetMapper)

        return defaultLineMapper;
    }


}