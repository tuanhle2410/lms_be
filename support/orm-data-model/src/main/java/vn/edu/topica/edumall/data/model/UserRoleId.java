package vn.edu.topica.edumall.data.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class UserRoleId implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id")
	private RoleInfo role;

	@Override
	public int hashCode() {
		if (getUser() != null && getRole() != null) {
			Long userId = getUser().getId();
			Long roleId = getRole().getId();
			return userId.hashCode() + roleId.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof UserRoleId))
			return false;
		if (getRole() == null)
			return false;
		if (getUser() == null)
			return false;
		UserRoleId other = (UserRoleId) obj;
		if (other != null) {
			if (other.getUser() != null && other.getRole() != null) {
				return other.getUser().getId() == getUser().getId() && other.getRole().getId() == getRole().getId();
			}
		}
		return false;
	}
}
