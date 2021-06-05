package com.az.assignment.service;


import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void processCSVFile(MultipartFile file);
}
