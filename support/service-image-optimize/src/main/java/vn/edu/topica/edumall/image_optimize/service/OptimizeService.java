package vn.edu.topica.edumall.image_optimize.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

@Service
public class OptimizeService {

    public File resize(MultipartFile image, int width, Scalr.Mode scaleMethod) throws IOException {
        String imageOriginalName = image.getOriginalFilename();
        String extension = FilenameUtils.getExtension(imageOriginalName);

        BufferedImage img = convertMultipartFileToBufferedImage(image, imageOriginalName);

        BufferedImage resizedImage = Scalr.resize(img, Scalr.Method.QUALITY, scaleMethod, width);

        File outputFile = new File(imageOriginalName);
        ImageIO.write(resizedImage, extension, outputFile);

        return outputFile;

    }

    public File compress(MultipartFile image, Float quality) throws IOException {
        String imageOriginalName = image.getOriginalFilename();
        String extension = FilenameUtils.getExtension(imageOriginalName);

        BufferedImage img = convertMultipartFileToBufferedImage(image, imageOriginalName);
        File compressedImageFile = compressProcess(img, imageOriginalName, extension, quality);
        return compressedImageFile;
    }

    public File optimize(MultipartFile image, int width, Scalr.Mode scaleMethod, Float quality) throws IOException {
        String imageOriginalName = image.getOriginalFilename();
        String extension = FilenameUtils.getExtension(imageOriginalName);
        BufferedImage img = convertMultipartFileToBufferedImage(image, imageOriginalName);

        BufferedImage resizedImage = Scalr.resize(img, Scalr.Method.QUALITY, scaleMethod, width);

        File compressedImageFile = compressProcess(resizedImage, imageOriginalName, extension, quality);
        return compressedImageFile;
    }

    private BufferedImage convertMultipartFileToBufferedImage(MultipartFile image, String imageName) throws IOException {
        File file = new File(imageName);
        FileUtils.writeByteArrayToFile(file, image.getBytes());
        BufferedImage img = ImageIO.read(file);
        if(img == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Image");
        }
        return img;
    }

    private File compressProcess(BufferedImage img, String imageOriginalName, String extension, Float quality) throws IOException {
        File compressedImageFile = new File(imageOriginalName);
        OutputStream os =new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers =  ImageIO.getImageWritersByFormatName(extension);
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        writer.write(null, new IIOImage(img, null, null), param);

        return compressedImageFile;
    }
}
