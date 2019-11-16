package vn.edu.topica.edumall.data.enumtype;

public enum RoleEnum {

	ADMIN(1L, "ADMIN", ""), TEACHER(2L, "TEACHER", ""), GUEST(3L, "GUEST", "");

	private Long roleId;
	private String roleName;
	private String description;

	RoleEnum(Long roleId, String roleName, String description) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.description = description;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
