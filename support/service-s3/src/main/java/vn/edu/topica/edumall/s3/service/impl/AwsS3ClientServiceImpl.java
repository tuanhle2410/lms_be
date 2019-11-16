package vn.edu.topica.edumall.s3.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.xuggle.xuggler.IContainer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.s3.service.AwsS3ClientService;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class AwsS3ClientServiceImpl implements AwsS3ClientService {
    private AmazonS3 amazonS3;
    private String awsS3AudioBucket;
    private static final Logger logger = LoggerFactory.getLogger(AwsS3ClientServiceImpl.class);

    @Value("${aws.access.key.id}")
    private String awsAccessKeyId;

    @Value("${aws.access.key.secret}")
    private String awsAccessKeySecret;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${random-key-upload-file.min-random}")
    Long minRandom;

    @Value("${random-key-upload-file.max-random}")
    Long maxRandom;

    @Autowired
    public AwsS3ClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider,
                                  String awsS3AudioBucket) {
        this.amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
    }

    @Override
    public List<S3ObjectSummary> listObjects(String bucketName) {
        ObjectListing objectListing = this.amazonS3.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }

    @Override
    public S3Object getObjectDetail(String bucketName, String objName) {
        S3Object object = this.amazonS3.getObject(bucketName, objName);
        return object;
    }

    public String uploadFile(String bucketName, MultipartFile multipartFile, String objName) {
        byte[] bufferedbytes = new byte[8196];
        long strRandom = ThreadLocalRandom.current().nextLong(minRandom, maxRandom + 1);
        String pathFileName = new Date().getTime() + "_" + strRandom + "_" + multipartFile.getOriginalFilename();
        File file = new File(pathFileName);
        FileOutputStream outStream;
        int count = 0;
        Integer countByte = 0;
        Long fileDuration = null;
        try {
            BufferedInputStream fileInputStream = new BufferedInputStream(multipartFile.getInputStream());
            outStream = new FileOutputStream(file);
            while ((count = fileInputStream.read(bufferedbytes)) != -1) {
                outStream.write(bufferedbytes, 0, count);
                countByte++;
                log.info("Making file in server........................................" + (countByte) + "KB");
            }
            outStream.close();

            IContainer container = IContainer.make();
            container.open(pathFileName, IContainer.Type.READ, null);
            fileDuration = container.getDuration() / 1000 / 1000; //second

            long contentLength = file.length();
            long partSize = 5 * 1024 * 1024;
            long countPartFile = 0;
            try {
                BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKeyId, awsAccessKeySecret);
                AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                        .withRegion(awsRegion)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                        .build();
                // Create a list of ETag objects. You retrieve ETags for each object part uploaded,
                // then, after each individual part has been uploaded, pass the list of ETags to
                // the request to complete the upload.
                List<PartETag> partETags = new ArrayList<PartETag>();
                // Initiate the multipart upload.
                InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, objName);
                initRequest.setCannedACL(CannedAccessControlList.PublicRead);
                InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);
                // Upload the file parts.
                long filePosition = 0;
                for (int i = 1; filePosition < contentLength; i++) {
                    // Because the last part could be less than 5 MB, adjust the part size as needed.
                    partSize = Math.min(partSize, (contentLength - filePosition));
                    // Create the request to upload a part.
                    UploadPartRequest uploadRequest = new UploadPartRequest()
                            .withBucketName(bucketName)
                            .withKey(objName)
                            .withUploadId(initResponse.getUploadId())
                            .withPartNumber(i)
                            .withFileOffset(filePosition)
                            .withFile(file)
                            .withPartSize(partSize);
                    // Upload the part and add the response's ETag to our list.
                    UploadPartResult uploadResult = s3Client.uploadPart(uploadRequest);
                    partETags.add(uploadResult.getPartETag());
                    filePosition += partSize;

                    countPartFile++;
                    log.info("S3Service Part file:......." + countPartFile * 5 + "MB");
                }
                // Complete the multipart upload.
                CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, objName,
                        initResponse.getUploadId(), partETags);
                s3Client.completeMultipartUpload(compRequest);
                log.info("S3Service Uploading:.....................................");
                if(file.exists()){
                    file.delete();
                }
                log.info("Server IS: File is deleted:.....................................");
                log.info("S3Service upload to s3 success.................................");
                return String.valueOf(fileDuration);
            } catch (AmazonServiceException e) {
                // The call was transmitted successfully, but Amazon S3 couldn't process
                // it, so it returned an error response.
                if(file.exists()){
                    file.delete();
                }
                log.info("Server IS: File is deleted when has AmazonServiceException:.....................................");
                e.printStackTrace();
                throw new RuntimeException("AmazonServiceException: " + e);
            } catch (SdkClientException e) {
                // Amazon S3 couldn't be contacted for a response, or the client
                // couldn't parse the response from Amazon S3.
                if(file.exists()){
                    file.delete();
                }
                log.info("Server IS: File is deleted when has SdkClientException:.....................................");
                e.printStackTrace();
                throw new RuntimeException("SdkClientException: " + e);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e);
        }
    }

    @Async
    public void deleteFile(String bucketName, String objName) {
        try {
            this.amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, objName));
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + objName + "] ");
        }
    }

    @Override
    public CopyObjectResult copyFile(String sourceBucketName, String sourceKey, String desBucketName, String desKey) {
        try {
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
                    sourceBucketName, sourceKey, desBucketName, desKey);
            copyObjectRequest.setCannedAccessControlList(CannedAccessControlList.PublicRead);

            CopyObjectResult result = this.amazonS3.copyObject(copyObjectRequest);
            return result;
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while copy [" + sourceKey + "] ");
            return null;
        }
    }

    @Override
    public byte[] downloadFile(String bucketName, String objName) throws IOException, NoSuchAlgorithmException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objName);

        S3Object s3Object = this.amazonS3.getObject(getObjectRequest);

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

//        byte[] bytes = IOUtils.toByteArray(objectInputStream);
//        return bytes;

        byte[] bufferedbytes = new byte[1024];
        File file = new File(s3Object.getKey());

        FileOutputStream outStream;
        int count = 0;

        BufferedInputStream fileInputStream = new BufferedInputStream(objectInputStream);
        outStream = new FileOutputStream(file);
        Integer countByte = 0;
        while ((count = fileInputStream.read(bufferedbytes)) != -1) {
            outStream.write(bufferedbytes, 0, count);
            countByte++;
            String str = "Making file in server.........................................." + countByte.toString() + "KB";
            log.info(str);
        }
        outStream.close();

        byte[] fileContent = Files.readAllBytes(file.toPath());
        file.delete();

        return fileContent;
    }
}
