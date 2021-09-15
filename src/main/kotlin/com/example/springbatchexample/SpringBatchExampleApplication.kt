package com.example.springbatchexample

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableBatchProcessing
class SpringBatchExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchExampleApplication>(*args)
}
