package vn.edu.topica.edumall.data.enumtype;

public enum MediaToolFieldEnum {
    FRAME_WIDTH(0L, "frameWidth", ""),
    FRAME_HEIGHT(1L, "frameHeight", ""),
    BITRATE(2L, "bitRate", ""),
    FRAMERATE(3L, "frameRate", ""),
    CODENAME(4L, "codeName", ""),
    FORMAT(5L, "format", ""),
    AUDIO_BITRATE(6L, "audioBitRate", ""),
    AUDIO_CHANNEL(7L, "audioChannel", "");

    Long id;
    String name;
    String description;

    MediaToolFieldEnum(Long id, String name, String description) {
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

