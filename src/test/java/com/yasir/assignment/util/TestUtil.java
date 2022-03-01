package com.yasir.assignment.util;

import com.yasir.assignment.entity.Product;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class TestUtil {


    public static MultipartFile buildMultipartFile(String fileName) {
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(fileName,
                    new FileInputStream(new File("src/test/resources/"+fileName)));
        } catch (IOException e) {
            return null;
        }
        return multipartFile;
    }

    public static Product buildProduct() {
        return new Product("Apple","Iphone" ,1);
    }
}
