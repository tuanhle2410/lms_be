package vn.edu.topica.edumall.data.enumtype;

public enum CourseVersionStatusEnum {

    CREATING(0L, "CREATING", "Đang tạo"),
    WAITING_APPROVE(1L, "	REVIEWING", "Chờ duyệt"),
    APPROVED(2L, "APPROVED", "Đã duyệt"),
    PUBLISHED(3L, "PUBLISHED", "Xuất bản"),
    DISAPPEAR(4L, "DISAPPEAR", "Tạm ẩn");

    private long id;

    private String name;

    private String description;

    CourseVersionStatusEnum(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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
