package vn.edu.topica.edumall.api.lms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.edumall.data.model.RoleInfo;
import vn.edu.topica.edumall.locale.config.Translator;

@RestController
@Slf4j
public class RoleController {

	@GetMapping("/listRole")
//	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public List<RoleInfo> loadAllRoleInfo() {
		log.info(Translator.toLocale("lang.change"));
		return new ArrayList<RoleInfo>();
	}
}
