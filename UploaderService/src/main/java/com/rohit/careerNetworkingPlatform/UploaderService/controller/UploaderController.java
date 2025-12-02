package com.rohit.careerNetworkingPlatform.UploaderService.controller;

import com.rohit.careerNetworkingPlatform.UploaderService.service.UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class UploaderController {

    private final UploaderService uploaderService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String url = uploaderService.upload(file);
        return ResponseEntity.ok(url);
    }
}
