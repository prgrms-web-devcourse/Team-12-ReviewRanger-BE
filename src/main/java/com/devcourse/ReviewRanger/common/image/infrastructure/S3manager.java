package com.devcourse.ReviewRanger.common.image.infrastructure;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devcourse.ReviewRanger.common.exception.RangerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class S3manager implements ImageManager {
	private static final List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png");

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public S3manager(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	@Override
	public String upload(MultipartFile multipartFile, String directory, String fileName) {
		validateImage(multipartFile);
		File uploadFile = convert(multipartFile)
			.orElseThrow(() -> new RangerException(MISSING_IMAGE_CONVERT));

		return uploadS3(uploadFile, directory, fileName);
	}

	@Override
	public void delete(String fileName, String directory) {
		String fileNameWithDir = directory + "/" + fileName;
		DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileNameWithDir);
		amazonS3.deleteObject(request);
	}

	private void validateImage(MultipartFile file) {
		if (file.isEmpty()) {
			log.warn("이미지가 없습니다. {}", file.getOriginalFilename());
			throw new RangerException(EMPTY_IMAGE);
		}

		String filename = file.getOriginalFilename();
		String extension = filename.substring(filename.lastIndexOf(".") + 1);

		boolean isExtension = imageExtensions.contains(extension.toLowerCase());

		if (!isExtension) {
			log.warn("이미지 확장자 검증 오류 : {}", filename);
			throw new RangerException(INVALID_IMAGE_EXTENSION);
		}

	}

	private String uploadS3(File uploadFile, String directory, String fileName) {
		String fileNameWithDir = directory + "/" + fileName;
		String uploadImageUrl = putS3(uploadFile, fileNameWithDir);

		removeNewFile(uploadFile);

		return uploadImageUrl;
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3.putObject(
			new PutObjectRequest(bucket, fileName, uploadFile)
				.withCannedAcl(CannedAccessControlList.PublicRead)
		);

		return amazonS3.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		String message = targetFile.delete() ? "파일이 삭제되었습니다." : "파일이 삭제되지 못했습니다.";
		log.info(message);
	}

	private Optional<File> convert(MultipartFile file) {
		File convertFile = new File(file.getOriginalFilename());
		boolean existFile = false;

		try {
			existFile = convertFile.createNewFile();
		} catch (IOException e) {
			throw new RangerException(MISSING_IMAGE_CONVERT);
		}

		if (existFile) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());

				return Optional.of(convertFile);
			} catch (IOException e) {
				throw new RangerException(MISSING_IMAGE_CONVERT);
			}
		}

		return Optional.empty();
	}
}
