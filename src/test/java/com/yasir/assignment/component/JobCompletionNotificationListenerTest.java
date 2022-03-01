package com.yasir.assignment.component;

import com.yasir.assignment.HugeDataProcessorApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= HugeDataProcessorApplication.class)
class JobCompletionNotificationListenerTest {

    @Autowired
    private JobCompletionNotificationListener jobCompletionNotificationListener;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void afterJob() {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1-for-read-process-write");
        assertDoesNotThrow(() -> jobCompletionNotificationListener.afterJob(jobExecution));
    }
}