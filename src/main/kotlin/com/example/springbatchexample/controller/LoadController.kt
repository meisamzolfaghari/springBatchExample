package com.example.springbatchexample.controller

import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("load")
class LoadController(
    private val job: Job,
    private val jobLauncher: JobLauncher
) {
    @GetMapping
    fun load(): BatchStatus {

        val jobParameters = JobParameters(
            hashMapOf<String, JobParameter>(
                Pair("time", JobParameter(System.currentTimeMillis()))
            )
        )

        val jobExecution = jobLauncher.run(job, jobParameters)

        println("JobExecution: " + jobExecution.status)

        println("Batch is running...")

        while (jobExecution.isRunning)
            println("....")

        return jobExecution.status;
    }

}