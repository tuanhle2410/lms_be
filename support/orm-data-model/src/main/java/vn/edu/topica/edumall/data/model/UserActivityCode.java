package vn.edu.topica.edumall.data.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_activity_code")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserActivityCode extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "code", length = 20)
	private String code;

	@Column(name = "long_des", columnDefinition = "Text")
	private String longDescription;

	@OneToMany(mappedBy = "userActivityCode")
	private Set<UserActivityLog> userActivityLogs;

	@Column(name = "created_by")
	private Long createdBy;
}
