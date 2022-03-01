package com.yasir.assignment.component;

import com.yasir.assignment.exception.CustomRuntimeException;
import java.io.File;
import java.io.IOException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

public class FileDeletingTasklet implements Tasklet, InitializingBean {

    private Resource resource;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)  {

        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new CustomRuntimeException("Could not delete file " + file.getPath(),
                    HttpStatus.EXPECTATION_FAILED);
        }
        boolean deleted = file.delete();
        if (!deleted) {
            throw new CustomRuntimeException("Could not delete file " + file.getPath(),
                    HttpStatus.EXPECTATION_FAILED);
        }
        return RepeatStatus.FINISHED;
    }

    public void setResources(Resource resource) {
        this.resource = resource;
    }

    public void afterPropertiesSet() {
        Assert.notNull(resource, "directory must be set");
    }
}