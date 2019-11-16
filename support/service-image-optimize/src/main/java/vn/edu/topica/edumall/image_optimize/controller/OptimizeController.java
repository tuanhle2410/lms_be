package vn.edu.topica.edumall.image_optimize.controller;

import vn.edu.topica.edumall.image_optimize.service.OptimizeService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;

@RestController
public class OptimizeController {

    @Autowired
    OptimizeService optimizeService;

    @PostMapping("/image/resize")
    public File resizeImage(@RequestBody MultipartFile image,
                            @RequestParam(defaultValue = "500", required = false) int width,
                            @RequestParam(defaultValue = "AUTOMATIC", required = false) Scalr.Mode scaleMethod) {
        try {
            return optimizeService.resize(image, width, scaleMethod);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/image/compress")
    public File compressImage(@RequestBody MultipartFile image,
                              @RequestParam(defaultValue = "0.5", required = false) Float quality) {
        try {
            return optimizeService.compress(image, quality);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/image/optimize")
    public File optimizeImage(@RequestBody MultipartFile image,
                              @RequestParam(defaultValue = "500", required = false) int width,
                              @RequestParam(defaultValue = "AUTOMATIC", required = false) Scalr.Mode scaleMethod,
                              @RequestParam(defaultValue = "0.5", required = false) Float quality) {
        try {
            return optimizeService.optimize(image, width, scaleMethod, quality);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
