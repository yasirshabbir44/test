package com.az.assignment.component;

import com.az.assignment.util.TestUtil;
import com.az.assignment.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserItemProcessorTest {

    @InjectMocks
    private UserItemProcessor userItemProcessor;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() throws Exception {
        User user = TestUtil.buildUser();
        assertDoesNotThrow(() -> userItemProcessor.process(user));
    }
}