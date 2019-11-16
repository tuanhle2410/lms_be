package vn.edu.topica.edumall.data.enumtype;

public enum CourseRunMarketingEnum {

    NO(0L, "NO", "no run marketing"),
    YES(1L, "YES", "run marketing");

    long id;
    String name;
    String description;

    CourseRunMarketingEnum(long id, String name, String description) {
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
