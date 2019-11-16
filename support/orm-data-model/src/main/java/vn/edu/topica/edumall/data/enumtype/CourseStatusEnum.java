package vn.edu.topica.edumall.data.enumtype;

public enum CourseStatusEnum {

    INITIALIZE(1L, "INITIALIZE", ""),
    PUBLISHED(2L, "PUBLISHED", ""),
    REMOVE_FROM_OWNER(3L, "REMOVE_FROM_OWNER", ""),
    REMOVE_FROM_STORE(4L, "REMOVE_FROM_STORE", ""),
    DISAPPEAR(5L, "DISAPPEAR", "");

    private long id;

    private String name;

    private String description;

    CourseStatusEnum(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
