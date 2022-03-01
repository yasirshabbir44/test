package com.yasir.assignment.controller;

import com.yasir.assignment.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductServiceImpl productService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processCsvData() {
        Mockito.doNothing().when(productService).processCSVFile(any());
        Assert.assertEquals(ResponseEntity.ok().body("files have been processed"), productController.processCsvData(any()));
    }
}