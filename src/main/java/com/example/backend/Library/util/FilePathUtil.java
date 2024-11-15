package com.example.backend.Library.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathUtil {
    public static Path getDefaultSavePath(String fileName) {
        // Đường dẫn mặc định để lưu file excel
        return Paths.get(System.getProperty("user.home"), "Documents/customer", "exports", fileName);
    }
}
