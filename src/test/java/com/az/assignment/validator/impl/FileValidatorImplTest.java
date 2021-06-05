package com.az.assignment.validator.impl;

import com.az.assignment.util.TestUtil;
import com.az.assignment.exception.CustomRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class FileValidatorImplTest {

    @InjectMocks
    private FileValidatorImpl fileValidator;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateFileIsNull() {
        assertThrows(CustomRuntimeException.class, () -> fileValidator.validateFile(null), "File is missing");
    }

    @Test
    void validateFileIsEmpty() {
        MultipartFile file = TestUtil.buildMultipartFile("test.csv");
        assertThrows(CustomRuntimeException.class, () -> fileValidator.validateFile(file), "File is invalid");
    }

    @Test
    void validateFile() {
        MultipartFile file = TestUtil.buildMultipartFile("users.csv");
        assertDoesNotThrow( () -> fileValidator.validateFile(file));
    }
}