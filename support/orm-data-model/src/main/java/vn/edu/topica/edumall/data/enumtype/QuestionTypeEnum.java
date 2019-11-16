package vn.edu.topica.edumall.data.enumtype;

public enum QuestionTypeEnum {

	SINGLE_CHOICE(1L, "", ""), MULTIPLE_CHOICE(2L, "", "");

	private long questionTypeId;
	private String questionTypeName;
	private String questionTypeDescription;

	QuestionTypeEnum(long questionTypeId, String questionTypeName, String questionTypeDescription) {
		this.questionTypeId = questionTypeId;
		this.questionTypeName = questionTypeName;
		this.questionTypeDescription = questionTypeDescription;
	}

	public long getQuestionTypeId() {
		return this.questionTypeId;
	}

	public void setQuestionTypeId(long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionTypeName() {
		return this.questionTypeName;
	}

	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	public String getQuestionTypeDescription() {
		return this.questionTypeDescription;
	}

	public void setQuestionTypeDescription(String questionTyeDescription) {
		this.questionTypeDescription = questionTyeDescription;
	}

}
