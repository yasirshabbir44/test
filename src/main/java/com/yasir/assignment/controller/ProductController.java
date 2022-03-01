package com.yasir.assignment.controller;

import com.yasir.assignment.service.ProductService;
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
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "file", notes = "Upload products.csv file")
    @PostMapping("/products")
    public ResponseEntity processCsvData(@RequestPart(value  = "file") MultipartFile file) {
        productService.processCSVFile(file);
        return ResponseEntity.ok().body("files have been processed");
    }
}
