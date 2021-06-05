package com.az.assignment.util;

import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    private FileUtil(){}

    public static void closeOutputStream(OutputStream outputStream) {
        try {
            if(outputStream != null)
                outputStream.close();
        } catch (IOException e) {
            log.error("Exception occurs while closing outputStream: ", e);
        }
    }
}
