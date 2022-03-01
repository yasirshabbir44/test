package com.yasir.assignment.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    void processCSVFile(MultipartFile file);
}
