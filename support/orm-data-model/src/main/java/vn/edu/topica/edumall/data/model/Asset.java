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
import vn.edu.topica.edumall.data.enumtype.AssetEnum;

@Entity
@Builder(toBuilder = true)
@Table(name = "asset")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Asset extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "asset_type")
	private AssetEnum asset_type;

	@Column(name = "transcode_url", length = 100)
	private String transcode_url;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "file_id")
	private File file;

	@Column(name = "created_by")
	private Long createdBy;
}
