package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class UserActivityLogDTO {

    private String createdAt;

    private String updatedAt;

    private long userId;

    private String action;

    private String browser;

    private String controller;

    private String ipAddress;

    private String note;

    private String params;



}
