package com.devcourse.ReviewRanger.common.image.infrastructure;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devcourse.ReviewRanger.common.exception.RangerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3manager implements ImageManager {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public S3manager(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	@Override
	public String upload(MultipartFile multipartFile, String directory, String fileName) throws IOException {
		File uploadFile = convert(multipartFile)
			.orElseThrow(() -> new RangerException(MISSING_IMAGE_CONVERT));

		return upload(uploadFile, directory, fileName);
	}

	@Override
	public void delete(String fileName, String directory) {
		String fileNameWithDir = directory + "/" + fileName;
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileNameWithDir));
	}

	private String upload(File uploadFile, String directory, String fileName) {
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
		if (targetFile.delete()) {
			log.info("파일이 삭제되었습니다.");
		} else {
			log.info("파일이 삭제되지 못했습니다.");
		}
	}

	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());

		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}

			return Optional.of(convertFile);
		}
		return Optional.empty();
	}
}
