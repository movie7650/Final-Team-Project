package com.example.daitso.admin.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
	
	// MultipartFile을 전달받아 S3에 저장 (다중 이미지)
    public List<String> upload(List<MultipartFile> files) throws Exception {
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
			}
			
			String imagePath = amazonS3Client.getUrl(bucket, originalName).toString(); // 접근가능한 URL 가져오기
			imagePathList.add(imagePath);
        });
        
        return imagePathList;
    }
    
    
//    public void deleteImage(String fileUrl) {
//        String splitStr = ".com/";
//        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());
//
//        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
//    }
    
    // URL에서 파일 이름 추출
    private String extractFileNameFromUrl(String fileUrl) {
        try {
            // URL 디코딩하여 파일 이름 추출
            String decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8);
            String[] parts = decodedUrl.split("/");
            return parts[parts.length - 1];
        } catch (Exception e) {
            // 디코딩 오류 처리
            log.error("URL 디코딩 실패: {}", fileUrl, e);
            return null;
        }
    }
    
    // s3에 이미지 삭제하기
    public void deleteImage(String fileUrl) {
        String fileName = extractFileNameFromUrl(fileUrl);
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
            System.out.println("이미지 삭제 성공: " + fileName);
        } catch (Exception e) {
        	System.out.println("이미지 삭제 실패: " + fileName);
        }
    }

    // 단일 이미지를 s3에 저장하기
    public String uploadSingle(MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            long size = file.getSize();

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(file.getContentType());
            objectMetaData.setContentLength(size);

            // S3에 업로드
            amazonS3Client.putObject(
                new PutObjectRequest(bucket, originalName, file.getInputStream(), objectMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            String imageUrl = amazonS3Client.getUrl(bucket, originalName).toString(); 
            return imageUrl;
            
        } catch (SdkClientException | IOException e) {
            log.info("S3 등록 실패");
            e.printStackTrace();
            throw new RuntimeException("S3 등록 실패");
        }
    }

//    public String uploads(MultipartFile file) {
//        try {
//            String originalName = file.getOriginalFilename();
//            long size = file.getSize();
//            System.out.println(originalName);
//            
//            ObjectMetadata objectMetaData = new ObjectMetadata();
//            objectMetaData.setContentType(file.getContentType());
//            objectMetaData.setContentLength(size);
//            
//            // S3에 업로드
//            amazonS3Client.putObject(
//                new PutObjectRequest(bucket, originalName, file.getInputStream(), objectMetaData)
//                    .withCannedAcl(CannedAccessControlList.PublicRead)
//            );
//            
//            String imagePath = amazonS3Client.getUrl(bucket, originalName).toString(); // 접근 가능한 URL 가져오기
//            return imagePath;
//            
//        } catch (SdkClientException | IOException e) {
//            log.error("S3 등록 실패", e);
//            throw new RuntimeException("S3 등록 실패", e);
//        }
//    }


}
