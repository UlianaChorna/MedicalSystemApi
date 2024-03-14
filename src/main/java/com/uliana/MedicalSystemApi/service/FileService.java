package com.uliana.MedicalSystemApi.service;//package com.uliana.MedicalSystemApi.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file);
}
