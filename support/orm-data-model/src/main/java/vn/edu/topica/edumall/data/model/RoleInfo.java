package vn.edu.topica.edumall.data.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role") @Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RoleInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "long_des")
	private String description;

	@Column(name = "name")
	private String name;
	
	@Column(name = "created_at")
	private Date createdAt;
	@CreationTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@OneToMany(mappedBy = "id.role")
	private Set<UserRole> listUserRole;
}
