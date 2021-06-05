package com.az.assignment.controller;

import com.az.assignment.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("assignment/v1")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "file", notes = "Upload users.csv file")
    @PostMapping("/users")
    public ResponseEntity processCsvData(@RequestPart(value  = "file") MultipartFile file) {
        userService.processCSVFile(file);
        return ResponseEntity.ok().body("files have been processed");
    }
}
