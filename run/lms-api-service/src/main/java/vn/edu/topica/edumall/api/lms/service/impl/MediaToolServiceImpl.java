package vn.edu.topica.edumall.api.lms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.edumall.api.lms.dto.MediaToolResultDTO;
import vn.edu.topica.edumall.api.lms.repository.MediaToolResultRepository;
import vn.edu.topica.edumall.api.lms.service.MediaToolService;
import vn.edu.topica.edumall.data.model.MediaToolResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaToolServiceImpl implements MediaToolService {

    @Autowired
    MediaToolResultRepository mediaToolResultRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void insertOrUpdate(List<MediaToolResultDTO> mediaToolResultList) {
        List<Long> fileIdList = new ArrayList<>();
        if (mediaToolResultList != null) {
            fileIdList = mediaToolResultList.stream()
                    .map(MediaToolResultDTO::getFileId)
                    .collect(Collectors.toList());
        }

        List<MediaToolResult> mediaToolResults = new ArrayList<>();
        if (!fileIdList.isEmpty()) {
            mediaToolResults = mediaToolResultRepository.getMediaToolResultListByFileIds(fileIdList);
        }
        if (!mediaToolResults.isEmpty()) {
            mediaToolResultRepository.deleteAll(mediaToolResults);
        }
        List<MediaToolResult> listMediaToolResultForInsert = new ArrayList<>();
        for (MediaToolResultDTO mediaToolResultDTO : mediaToolResultList) {
            MediaToolResult mediaToolResult = modelMapper.map(mediaToolResultDTO, MediaToolResult.class);
            listMediaToolResultForInsert.add(mediaToolResult);
        }
        mediaToolResultRepository.saveAll(listMediaToolResultForInsert);
    }
}
