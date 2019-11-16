package vn.edu.topica.edumall.data.enumtype;

public enum FileTypeEnum {

    VIDEO(0L, "VIDEO", ""), IMAGE(1L, "IMAGE", ""), AUDIO(2L, "AUDIO", ""), OTHER(3L, "OTHER", ""), ATTACHMENT(4L, "ATTACHMENT", "");

    private long id;
    private String name;
    private String description;

    FileTypeEnum(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
