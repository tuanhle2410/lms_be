package vn.edu.topica.edumall.data.model;

import java.util.Set;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "user")
public class User extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "avatar")
	private String avatar;

	@OneToMany(mappedBy = "id.user")
	private Set<UserRole> listUserRole;

	@OneToOne(mappedBy = "user")
	private Teacher teacher;

	@Column(name = "kelly_id")
	private String kellyId;
}
