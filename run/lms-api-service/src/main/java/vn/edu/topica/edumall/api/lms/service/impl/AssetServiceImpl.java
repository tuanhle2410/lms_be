package vn.edu.topica.edumall.api.lms.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.repository.AssetRepository;
import vn.edu.topica.edumall.api.lms.repository.FileRepository;
import vn.edu.topica.edumall.api.lms.service.AssetService;
import vn.edu.topica.edumall.data.model.Asset;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.locale.config.Translator;
import vn.edu.topica.edumall.s3.service.AwsS3ClientService;

@Slf4j
@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    AssetRepository assetRepository;

    @Value("${aws.s3.buckets.app-storage}")
    String bucketBackupName;

    @Autowired
    AwsS3ClientService awsS3ClientService;

    @Override
    public boolean deleteAsset(Long id) {

        Asset asset = assetRepository.findById(id).orElse(null);
        if (asset == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"Asset"}), null);
        }

        File file = asset.getFile();
        if (file == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"File"}), null);
        }
        try {
//            awsS3ClientService.deleteFile(bucketBackupName, file.getObjectKey());

            assetRepository.deleteAssetById(asset.getId());

            fileRepository.deleteFileById(file.getId());
            return true;

        } catch (Exception e) {
            log.error(e.toString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("cannot.delete.resource", new Object[]{"Asset"}), null);
        }
    }
}
