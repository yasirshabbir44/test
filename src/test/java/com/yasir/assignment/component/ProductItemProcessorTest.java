package com.yasir.assignment.component;

import com.yasir.assignment.util.TestUtil;
import com.yasir.assignment.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ProductItemProcessorTest {

    @InjectMocks
    private ProductItemProcessor productItemProcessor;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() throws Exception {
        Product product = TestUtil.buildProduct();
        Assertions.assertDoesNotThrow(() -> productItemProcessor.process(product));
    }
}