package vn.edu.topica.edumall.api.lms.service;

import java.util.List;

import vn.edu.topica.edumall.data.model.RoleInfo;

/**
 * @author trungnt9
 *
 */
public interface RoleInfoService {
	/**
	 * @return
	 */
	List<RoleInfo> loadAllRoleInfo();
}
