package vn.edu.topica.edumall.api.lms.service;

import vn.edu.topica.edumall.api.lms.dto.MediaToolResultDTO;

import java.util.List;

public interface MediaToolService {
    void insertOrUpdate(List<MediaToolResultDTO> mediaToolResultList);
}
