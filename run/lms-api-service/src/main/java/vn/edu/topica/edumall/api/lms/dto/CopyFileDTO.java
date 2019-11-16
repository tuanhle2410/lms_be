package vn.edu.topica.edumall.api.lms.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class CopyFileDTO {
	@NotNull(message = "Folder_id can be not null")
	@JsonProperty("folder_id")
	Long folderId;

	@NotNull(message = "File_id can be not null")
	@JsonProperty("file_id")
	Long fileId;
}
