package vn.edu.topica.edumall.api.lms.controller;

import brave.internal.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.FolderDTO;
import vn.edu.topica.edumall.api.lms.dto.ItemNameDTO;
import vn.edu.topica.edumall.api.lms.dto.MoveItemDTO;
import vn.edu.topica.edumall.api.lms.factory.GeneralPageResponse;
import vn.edu.topica.edumall.api.lms.service.FolderService;
import vn.edu.topica.edumall.api.lms.utility.OffsetBasedPageRequest;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.data.model.Folder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/folders")
public class FolderController {

	@Autowired
	FolderService folderService;

	@Autowired
	private ModelMapper modelMapper;

	@PreAuthorize("isFolderOwner(#folderId)")
	@GetMapping("/{folderId}")
	@ResponseBody
	public FolderDTO loadFolderDetail(@PathVariable(value = "folderId") Long folderId) {
		Folder folder = folderService.getFolderDetail(folderId);
		if (folder == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder Not Found", null);
		}
		FolderDTO folderDTO = modelMapper.map(folder, FolderDTO.class);

		return folderDTO;
	}

	@PreAuthorize("isFolderOwner(#folderId)")
	@PutMapping("/{folderId}")
	@ResponseBody
	public ResponseEntity<String> changeFolderName(@PathVariable(value = "folderId") Long folderId,
			@RequestBody @Valid ItemNameDTO folderNameDTO) {

		folderService.changeFolderName(folderId, folderNameDTO.getName());

		return new ResponseEntity<>("Successfully updated folder name", HttpStatus.OK);
	}

	@PostMapping("/moveToFolder")
	public ResponseEntity<Boolean> moveToFolder(@RequestBody MoveItemDTO moveItemDTO) {
		Boolean result = folderService.moveToFolder(moveItemDTO);

		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@PreAuthorize("isFolderOwner(#folderId)")
	@GetMapping("/detail")
	public ResponseEntity<Folder> detail(@RequestParam @NotEmpty long folderId) {
		Folder folder = folderService.detail(folderId);
		if (folder != null)
			return new ResponseEntity<Folder>(folder, HttpStatus.OK);
		else
			return new ResponseEntity<Folder>(HttpStatus.NOT_FOUND);
	}

	@PreAuthorize("isFolderOwner(#folder.getParentFolder() == null ? null: #folder.getParentFolder().getId())")
	@PostMapping("")
	public ResponseEntity<Object> createFolder(@RequestBody @Valid Folder folder) {
		return new ResponseEntity<>(folderService.createFolder(folder), HttpStatus.CREATED);
	}

	@PreAuthorize("isFolderOwner(#folderId)")
	@GetMapping("/{folderId}/avatar")
	@ResponseBody
	public FolderDTO loadFolderDetailForUploadingAvatar(@PathVariable(value = "folderId") Long folderId, @RequestParam FileTypeEnum fileType) {
		FolderDTO folderDTO = folderService.detailForAvatarUploading(folderId, fileType);
		return folderDTO;
	}

	@PreAuthorize("isFolderOwner(#folderId)")
	@GetMapping("/{folderId}/avatar/files")
	@ResponseBody
	public ResponseEntity<GeneralPageResponse> loadMoreFilesUploadingAvatar(@PathVariable(value = "folderId") Long folderId,
																			@RequestParam FileTypeEnum fileType, @RequestParam int pageNum, @RequestParam int pageSize, @RequestParam int offset) {
		Page<File> files = folderService.getFilesForAvatarUploading(folderId, fileType, new OffsetBasedPageRequest(offset + pageNum * pageSize, pageSize));

		GeneralPageResponse fileResponses = new GeneralPageResponse();

		return new ResponseEntity<>(fileResponses.toResponse(files, offset), HttpStatus.OK);
	}
}
