package com.yasir.assignment.validator.impl;

import com.yasir.assignment.exception.CustomRuntimeException;
import com.yasir.assignment.validator.FileValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileValidatorImpl implements FileValidator {

    @Override
    public void validateFile(MultipartFile file) {
        log.debug("Validate File {}", file);
        if(file == null)
            throw new CustomRuntimeException("File is missing", HttpStatus.BAD_REQUEST);
        if(file.isEmpty())
            throw new CustomRuntimeException("File is invalid", HttpStatus.BAD_REQUEST);
    }
}
