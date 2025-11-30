package com.rohit.careerNetworkingPlatform.UploaderService.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {

    String upload(MultipartFile file);
}
