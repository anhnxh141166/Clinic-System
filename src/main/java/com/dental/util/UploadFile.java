package com.dental.util;

import java.io.*;
import java.nio.file.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadFile {
    public static String getFileName(MultipartFile multipartFile) {
        return System.currentTimeMillis() + "-" + StringUtils.cleanPath(multipartFile.getOriginalFilename());
    }

    public static void saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        File saveFile = new ClassPathResource("static/assets/uploads").getFile();
        Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+fileName);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
