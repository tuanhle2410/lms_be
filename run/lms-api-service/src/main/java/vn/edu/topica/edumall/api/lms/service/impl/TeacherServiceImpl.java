package vn.edu.topica.edumall.api.lms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.bus.Event;
import reactor.bus.EventBus;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.repository.UserRepository;
import vn.edu.topica.edumall.api.lms.service.TeacherService;
import vn.edu.topica.edumall.api.lms.upload.queue.ConsumerEnum;
import vn.edu.topica.edumall.api.lms.utility.NormalizeStringUtility;
import vn.edu.topica.edumall.data.model.User;
import vn.edu.topica.edumall.s3.service.AwsS3ClientService;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Value("${kelley.url.get-teacher-detail}")
    String kelleyGetTeacherDetailUrl;

    @Value("${kelley.url.update-teacher}")
    String kelleyUpdateTeacherUrl;

    @Value("${random-key-upload-file.min-random}")
    Long minRandom;

    @Value("${random-key-upload-file.max-random}")
    Long maxRandom;

    @Autowired
    AwsS3ClientService awsS3ClientService;

    @Value("${aws.s3.buckets.user-upload}")
    String bucketName;

    @Value("${personal-info.type.individual}")
    String individual;

    @Value("${personal-info.type.company}")
    String company;

    @Autowired
    UserRepository userRepository;
    ;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EventBus eventBus;

    @Override
    public TeacherDetailDTO getTeacherDetail(String email) {
        try {
            URIBuilder uriBuilder = new URIBuilder(kelleyGetTeacherDetailUrl);
            uriBuilder.setParameter("email", email);
            HttpGet requestGet = new HttpGet(uriBuilder.build());

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(requestGet);
            if (response.getStatusLine().getStatusCode() == 200) {

                String strResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject jsonResponse = new JSONObject(strResponse);
                String teacher = null;

                if (!jsonResponse.isEmpty()) {
                    teacher = jsonResponse.get("teacher").toString();
                }

                //todo map with kelley
                ObjectMapper objectMapper = new ObjectMapper();
                TeacherDetailDTO teacherDetail = objectMapper.readValue(teacher, TeacherDetailDTO.class);

                return teacherDetail;
            }
        } catch (URISyntaxException e) {
            log.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", null);

    }

    @Override
    @Transactional
    public TeacherInfoUpdateToKelleyDTO updateTeacherDetail(Authentication authentication, TeacherInfoUpdateDTO teacherInfoUpdateDTO) {
        try {
            String email = ((UserPrincipal) authentication.getCredentials()).getEmail();
            if (!teacherInfoUpdateDTO.getEmail().equals(email)) {
                //update email on IS when user change email
                User user = userRepository.findFirstByEmail(email);
                user.setEmail(teacherInfoUpdateDTO.getEmail());
            }

            String domain = "https://" + bucketName + ".s3.ap-southeast-1.amazonaws.com/";

            URIBuilder uriBuilder = new URIBuilder(kelleyUpdateTeacherUrl);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut clientPut = new HttpPut(uriBuilder.build());

            MultipartFile avatar = teacherInfoUpdateDTO.getAvatar();
            MultipartFile identificationFrontImage = teacherInfoUpdateDTO.getIdentificationFrontImage();
            MultipartFile identificationBehindImage = teacherInfoUpdateDTO.getIdentificationBehindImage();
            MultipartFile identificationImage3 = teacherInfoUpdateDTO.getIdentificationImage3();

            long strRandom = ThreadLocalRandom.current().nextLong(minRandom, maxRandom + 1);
            TeacherInfoUpdateToKelleyDTO teacherInfoUpdateToKelleyDTO = modelMapper.map(teacherInfoUpdateDTO, TeacherInfoUpdateToKelleyDTO.class);
            if (avatar != null) {
                String s3AvatarFilename = new Date().getTime() + "_" + strRandom + "_" + avatar.getOriginalFilename();
                uploadFile(s3AvatarFilename, avatar);
                teacherInfoUpdateToKelleyDTO.setAvatar(domain + s3AvatarFilename);
            }

            if (identificationFrontImage != null) {
                String s3IdentificationFrontImageFilename = new Date().getTime() + "_" + strRandom + "_" + identificationFrontImage.getOriginalFilename();
                uploadFile(s3IdentificationFrontImageFilename, identificationFrontImage);
                teacherInfoUpdateToKelleyDTO.setIdentificationFrontImagePath(domain + s3IdentificationFrontImageFilename);
            }

            if (identificationBehindImage != null) {
                String s3IdentificationBehindImageFilename = new Date().getTime() + "_" + strRandom + "_" + identificationBehindImage.getOriginalFilename();
                uploadFile(s3IdentificationBehindImageFilename, identificationBehindImage);
                teacherInfoUpdateToKelleyDTO.setIdentificationBehindImagePath(domain + s3IdentificationBehindImageFilename);
            }

            if (identificationImage3 != null) {
                String s3IdentificationImage3Filename = new Date().getTime() + "_" + strRandom + "_" + identificationImage3.getOriginalFilename();
                uploadFile(s3IdentificationImage3Filename, identificationImage3);
                teacherInfoUpdateToKelleyDTO.setIdentificationImage3Path(domain + s3IdentificationImage3Filename);
            }

            teacherInfoUpdateToKelleyDTO.setOldEmail(email);

            ObjectMapper objectMapper = new ObjectMapper();
            String teacherJson = objectMapper.writeValueAsString(teacherInfoUpdateToKelleyDTO);

            StringEntity entity = new StringEntity(teacherJson, "UTF-8");
            clientPut.setEntity(entity);
            clientPut.setHeader("Accept", "application/json");
            clientPut.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(clientPut);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return teacherInfoUpdateToKelleyDTO;
            } else {
                String strResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject jsonResponse = new JSONObject(strResponse);
                String message = null;
                if (!jsonResponse.isEmpty()) {
                    message = jsonResponse.get("message").toString();
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message, null);
            }
        } catch (URISyntaxException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", null);
    }

    @Override
    public boolean validateTeacherInfo(Authentication authentication) {

        String email = ((UserPrincipal) authentication.getCredentials()).getEmail();
        TeacherDetailDTO teacherDetail = getTeacherDetail(email);
        if (teacherDetail == null) {
            return false;
        }
        if (NormalizeStringUtility.strIsNull(teacherDetail.getBankAccountNumber())
                || NormalizeStringUtility.strIsNull(teacherDetail.getIdentificationImage2())
                || NormalizeStringUtility.strIsNull(teacherDetail.getIdentificationImage1())
                || NormalizeStringUtility.strIsNull(teacherDetail.getTaxNumber())
                || NormalizeStringUtility.strIsNull(teacherDetail.getBankName())
                || NormalizeStringUtility.strIsNull(teacherDetail.getBankAccountName())
                || teacherDetail.getProfile() == null || teacherDetail.getProfile().isEmpty()
                || NormalizeStringUtility.strIsNull(teacherDetail.getBranch())
                || NormalizeStringUtility.strIsNull(teacherDetail.getEmail())
                || NormalizeStringUtility.strIsNull(teacherDetail.getContactEmail())
                || NormalizeStringUtility.strIsNull(teacherDetail.getPhone())
                || NormalizeStringUtility.strIsNull(teacherDetail.getAddress())) {
            return false;
        }
        if (teacherDetail.getType().equalsIgnoreCase(individual)) {
            if (NormalizeStringUtility.strIsNull(teacherDetail.getDateOfBirth())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getIdentificationNumber())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getLicenceDate())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getLicenceAddress())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getFullName())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getGender())) {
                return false;
            }
        }
        if (teacherDetail.getType().equalsIgnoreCase(company)) {
            if (NormalizeStringUtility.strIsNull(teacherDetail.getRepresentativeLevel())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getIdentificationImage3())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getCompanyName())
                    || NormalizeStringUtility.strIsNull(teacherDetail.getRepresentative())) {
                return false;
            }
        }
        return true;
    }

    private void uploadFile(String fileName, MultipartFile file) {
        UploadS3DTO uploadS3DTO = UploadS3DTO.builder()
                .s3FileName(fileName)
                .bucketName(bucketName)
                .multipartFile(file)
                .build();
        eventBus.notify(ConsumerEnum.UPLOADS3, Event.wrap(uploadS3DTO));
    }


}
