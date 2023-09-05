package com.example.daitso.admin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3Service {

	@Autowired
	AmazonS3 amazonS3Client;
	
	@Value("${cloud.aws.s3.bucket}")
    private String bucket;
	
	// MultipartFile을 전달받아 S3에 저장
    public List<String> upload(List<MultipartFile> files) {
    	List<String> imagePathList = new ArrayList<>();
        files.stream().forEach((file) -> {
        	String originalName =  file.getOriginalFilename(); // 파일 이름
			long size = file.getSize(); // 파일 크기
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(file.getContentType());
			objectMetaData.setContentLength(size);
			
			// S3에 업로드
			try {
				amazonS3Client.putObject(
					new PutObjectRequest(bucket, originalName, file.getInputStream(), objectMetaData)
						.withCannedAcl(CannedAccessControlList.PublicRead)
				);
			} catch (SdkClientException | IOException e) {
				log.info("S3 등록 실패");
				e.printStackTrace();
			}
			
			String imagePath = amazonS3Client.getUrl(bucket, originalName).toString(); // 접근가능한 URL 가져오기
			imagePathList.add(imagePath);
        });
        
        return imagePathList;
    }
    
    // 이미지 삭제
    public void deleteImage(String fileUrl) {
        String splitStr = ".com/";
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());

        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
