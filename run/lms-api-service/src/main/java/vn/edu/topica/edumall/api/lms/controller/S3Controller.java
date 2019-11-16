//package vn.edu.topica.edumall.api.lms.controller;
//
//import com.amazonaws.services.s3.model.CopyObjectResult;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectSummary;
//import com.amazonaws.util.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import vn.edu.topica.edumall.s3.service.AwsS3ClientService;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/s3")
//public class S3Controller {
//	@Value("${aws.s3.buckets.user-upload}")
//	private String userUploadBucketName;
//
//	@Autowired
//	AwsS3ClientService s3ClientService;
//
//	@PostMapping
//	public Map<String, String> uploadFile() throws IOException {
//		File f = new File("./run/lms-api-service/resource/images/cho.jpg");
//		FileInputStream input = new FileInputStream(f);
//		MultipartFile multipartFile = new MockMultipartFile("file", f.getName(), "image/jpg",
//				IOUtils.toByteArray(input));
//
//		this.s3ClientService.uploadFile("lms-stg-user-upload", multipartFile, true);
//
//		Map<String, String> response = new HashMap<>();
//		response.put("message",
//				"file [" + multipartFile.getOriginalFilename() + "] uploading request submitted successfully.");
//
//		return response;
//	}
//
//	@DeleteMapping
//	public Map<String, String> deleteFile(@RequestParam("file_name") String fileName) {
//		this.s3ClientService.deleteFile("lms-stg-user-upload", fileName);
//
//		Map<String, String> response = new HashMap<>();
//		response.put("message", "file [" + fileName + "] removing request submitted successfully.");
//
//		return response;
//	}
//
//	@GetMapping("/list")
//	public List<S3ObjectSummary> listObjects() {
//		List<S3ObjectSummary> listObjects = this.s3ClientService.listObjects(userUploadBucketName);
//		return listObjects;
//	}
//
//	@GetMapping("/object")
//	public S3Object getObject(@RequestParam String keyS3) {
//		S3Object s3Object = this.s3ClientService.getObjectDetail(userUploadBucketName, keyS3);
//		return s3Object;
//	}
//
//	@PostMapping("/copy_object")
//	public CopyObjectResult copyObject(@RequestParam String sourceKey, @RequestParam String desKey) {
//		CopyObjectResult result = this.s3ClientService.copyFile(userUploadBucketName, sourceKey, userUploadBucketName,
//				desKey);
//		return result;
//	}
//
//	@GetMapping("/{keyS3}")
//	public S3Object s3ObjectDetail(@PathVariable String keyS3) {
//		S3Object s3Object = this.s3ClientService.getObjectDetail("lms-stg-user-upload", keyS3);
//		return s3Object;
//	}
//}
