package com.devcourse.ReviewRanger.common.image.infrastructure;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageManager {

	String upload(MultipartFile multipartFile, String directory, String profileImage) throws IOException;

	void delete(String fileName, String directory);
}
