package vn.edu.topica.edumall.api.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.topica.edumall.api.lms.service.AssetService;


@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    AssetService assetService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> allCategory(@PathVariable("id") long id) {
        return new ResponseEntity<>(assetService.deleteAsset(id), HttpStatus.OK);
    }

}
