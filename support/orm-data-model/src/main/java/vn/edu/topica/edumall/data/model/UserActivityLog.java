package vn.edu.topica.edumall.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@Table(name="user_activity_log")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserActivityLog extends BaseModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="user_id")
	private int userId;
	
	@Column(name="browser", length=255)
	private String browser;
	
	@Column(name="ip_address", length=50)
	private String ipAddress;
	
	@Column(name="controller", length=100)
	private String controller;

	@Column(name = "method", length = 10)
	private String method;
	
	@Column(name="action", length=50)
	private String action;
	
	@Column(name="params", columnDefinition="Text")
	private String params;
	
	@Column(name="note", length=100)
	private String note;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="activity_code_id")
	private UserActivityCode userActivityCode;
}
