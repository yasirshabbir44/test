package com.yasir.assignment.service;

import com.yasir.assignment.service.impl.ProductServiceImpl;
import com.yasir.assignment.util.TestUtil;
import com.yasir.assignment.exception.CustomRuntimeException;
import com.yasir.assignment.validator.FileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job job;

    @Mock
    private FileValidator fileValidator;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Scenario")
    void processCSVFile() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        Mockito.doNothing().when(fileValidator).validateFile(any());
        MultipartFile multipartFile = TestUtil.buildMultipartFile("products.csv");
        Mockito.when(jobLauncher.run(any(), any())).thenReturn(any());
        assertDoesNotThrow(() -> productService.processCSVFile(multipartFile));
    }

    @Test
    @DisplayName("Failure Scenario-1 for JobExecutionAlreadyRunningException")
    void processCSVFileFailure() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        Mockito.doNothing().when(fileValidator).validateFile(any());
        MultipartFile multipartFile = TestUtil.buildMultipartFile("products.csv");
        Mockito.doThrow(JobExecutionAlreadyRunningException.class).when(jobLauncher).run(any(), any());
        assertThrows(CustomRuntimeException.class, () -> productService.processCSVFile(multipartFile),
                "Exception occurs while processing file");
    }

    @Test
    @DisplayName("Failure Scenario-1 for JobExecutionAlreadyRunningException")
    void processCSVFileFailureC2() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        Mockito.doNothing().when(fileValidator).validateFile(any());
        MultipartFile multipartFile = TestUtil.buildMultipartFile("products.csv");
        Mockito.doThrow(JobExecutionAlreadyRunningException.class).when(jobLauncher).run(any(), any());
        assertThrows(CustomRuntimeException.class, () -> productService.processCSVFile(multipartFile),
                "Exception occurs while processing file");
    }

    @Test
    @DisplayName("Failure Scenario-1 for JobParametersInvalidException")
    void processCSVFileFailureC3() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        Mockito.doNothing().when(fileValidator).validateFile(any());
        MultipartFile multipartFile = TestUtil.buildMultipartFile("products.csv");
        Mockito.doThrow(JobParametersInvalidException.class).when(jobLauncher).run(any(), any());
        assertThrows(CustomRuntimeException.class, () -> productService.processCSVFile(multipartFile),
                "Exception occurs while processing file");
    }

    @Test
    @DisplayName("Failure Scenario-1 for JobRestartException")
    void processCSVFileFailureC4() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        Mockito.doNothing().when(fileValidator).validateFile(any());
        MultipartFile multipartFile = TestUtil.buildMultipartFile("products.csv");
        Mockito.doThrow(JobRestartException.class).when(jobLauncher).run(any(), any());
        assertThrows(CustomRuntimeException.class, () -> productService.processCSVFile(multipartFile),
                "Exception occurs while processing file");
    }

    @Test
    @DisplayName("Failure Scenario-1 for Exception")
    void processCSVFileFailureC5() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        Mockito.doNothing().when(fileValidator).validateFile(any());
        assertThrows(CustomRuntimeException.class, () -> productService.processCSVFile(null),
                "Exception occurs while processing file");
    }
}