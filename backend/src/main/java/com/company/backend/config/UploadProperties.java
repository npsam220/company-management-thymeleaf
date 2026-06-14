package com.company.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadProperties {

    @Value("${app.file.photo-dir}")
    private String photoDir;

    @Value("${app.file.resume-dir}")
    private String fileDir;

    public String getPhotoDir() {
        return photoDir;
    }

    public String getFileDir() {
        return fileDir;
    }
}
