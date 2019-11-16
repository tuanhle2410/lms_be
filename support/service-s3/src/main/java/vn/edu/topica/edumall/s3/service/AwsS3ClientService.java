package vn.edu.topica.edumall.s3.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 *
 */
public interface AwsS3ClientService {
    /**
     * @param bucketName
     * @return
     */
    List<S3ObjectSummary> listObjects(String bucketName);

    /**
     * @param bucketName
     * @param objName
     * @return
     */
    S3Object getObjectDetail(String bucketName, String objName);

    /**
     * Used to upload file
     *
     * @param bucketName
     * @param multipartFile
     * @param objName
     * @return
     */
    String uploadFile(String bucketName, MultipartFile multipartFile, String objName);

//    /**
//     * @param bucketName
//     * @param multipartFile
//     * @param objName
//     * @return
//     */
//    String uploadFile(String bucketName, MultipartFile multipartFile, String objName);

    /**
     * @param bucketName
     * @param objName
     */
    void deleteFile(String bucketName, String objName);

    /**
     * @param sourceBucketName
     * @param sourceKey
     * @param desBucketName
     * @param desKey
     * @return
     */
    CopyObjectResult copyFile(String sourceBucketName, String sourceKey, String desBucketName, String desKey);

    /**
     * @param bucketName
     * @param objName
     * @return
     * @throws IOException
     */
    public byte[] downloadFile(String bucketName, String objName) throws IOException, NoSuchAlgorithmException;
}