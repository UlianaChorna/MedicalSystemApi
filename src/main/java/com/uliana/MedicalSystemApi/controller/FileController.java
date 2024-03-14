package com.uliana.MedicalSystemApi.controller;//package com.uliana.MedicalSystemApi.controller;


import com.uliana.MedicalSystemApi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public void saveImage(@RequestParam("file") MultipartFile file) {
        fileService.upload(file);
    }
}
