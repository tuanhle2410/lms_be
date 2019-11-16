package vn.edu.topica.edumall.api.lms.upload.queue;

public enum ConsumerEnum {
    UPLOADS3(0, "UPLOADS3", "consumer upload file to s3"),
    PROCESS_SEND_TO_MEDIA_TOOL(1, "PROCESS_SEND_TO_MEDIA_TOOL", "Process "),
    LOG(2, "LOG", "consumer LOG"),
    PROCESS_SEND_EMAIL_WHEN_FAIL_UPLOAD_S3(3, "PROCESS_SEND_EMAIL_WHEN_FAIL_UPLOAD_S3", "Send email to teacher when failing upload file to s3"),
    UPDATE_COURSE_KELLEY(4, "UPDATE_COURSE_KELLEY", "Process publish, disappear course"),
    SEND_EMAIL_TO_TEACHER(5, "SEND_EMAIL_TO_TEACHER", "Send an email to the teacher");

    private long consumerId;
    private String name;
    private String description;

    ConsumerEnum(long consumerId, String name, String description) {
        this.consumerId = consumerId;
        this.name = name;
        this.description = description;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
