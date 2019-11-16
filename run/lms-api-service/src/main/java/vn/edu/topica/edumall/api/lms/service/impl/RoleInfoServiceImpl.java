package vn.edu.topica.edumall.api.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.topica.edumall.api.lms.repository.RoleInfoRepository;
import vn.edu.topica.edumall.api.lms.service.RoleInfoService;
import vn.edu.topica.edumall.data.model.RoleInfo;

@Service
@Transactional
public class RoleInfoServiceImpl implements RoleInfoService {
	@Autowired
	private RoleInfoRepository roleInfoRepository;

	public List<RoleInfo> loadAllRoleInfo() {
		return roleInfoRepository.findAll();
	}
}
