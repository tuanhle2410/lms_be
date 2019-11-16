package vn.edu.topica.edumall.data.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@Table(name = "quiz")
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Quiz extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "title", length = 30)
	private String title;

	@Column(name = "long_des", columnDefinition = "Text")
	private String longDescription;

	@Column(name = "quiz_order")
	private Integer quizOrder;

	@Column(name = "quiz_duration")
	private Integer quizDuration;

	@Column(name = "pass_score")
	private Integer passScore;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "chapter_id")
	private Chapter chapter;

	@OneToMany(mappedBy = "quiz")
	private Set<Question> questions;

	@Column(name = "created_by")
	private Long createdBy;
}
