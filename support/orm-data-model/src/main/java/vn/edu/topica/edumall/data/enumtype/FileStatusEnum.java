package vn.edu.topica.edumall.data.enumtype;

public enum FileStatusEnum {
	
	AVAILABILITY(1L, "AVAILABILITY", ""),
	REMOVED(2L, "REMOVED", "");

	private long id;
	
	private String name;
	
	private String description;
	

	private FileStatusEnum(long id, String name, String description) {
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
