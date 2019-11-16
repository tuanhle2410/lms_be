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
import vn.edu.topica.edumall.data.enumtype.QuestionTypeEnum;

@Entity
@Builder(toBuilder = true)
@Table(name = "question")
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Question extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "question_Type")
	private QuestionTypeEnum questionType;

	@Column(name = "question_title", columnDefinition = "Text")
	private String questionTite;

	@Column(name = "answers", columnDefinition = "Text")
	private String answers;

	@Column(name = "correct_answer", columnDefinition = "Text")
	private String correctAnswer;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	@Column(name = "created_by")
	private Long createdBy;

}
