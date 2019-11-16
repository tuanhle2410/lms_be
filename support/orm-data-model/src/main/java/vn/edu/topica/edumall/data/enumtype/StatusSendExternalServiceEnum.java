package vn.edu.topica.edumall.data.enumtype;

public enum StatusSendExternalServiceEnum {

    SEND_MEDIA_TOOL_SUCCESS(0L, "SEND_MEDIA_TOOL_SUCCESS", ""),
    SEND_MEDIA_TOOL_FAILED(1L, "SEND_MEDIA_TOOL_FAILED", ""),
    SEND_KELLEY_SUCCESS(2L, "SEND_KELLEY_SUCCESS", ""),
    SEND_KELLEY_FAILED(3L, "SEND_KELLEY_FAILED", "");

    Long id;
    String name;
    String description;

    StatusSendExternalServiceEnum(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

