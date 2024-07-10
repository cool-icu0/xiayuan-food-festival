package com.cool.take_out_file.controller;

import com.cool.take_out_file.service.FileService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/admin/common")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        return fileService.upload(file);
    }
}
