package vn.edu.topica.edumall.data.enumtype;

public enum AssetEnum {

	VIDEO(0L, "VIDEO", ""), AUDIO(1L, "	AUDIO", ""), SLIDE(2L, "SLIDE", ""), URL(3L, "URL", ""), WORD(4L, "WORD", ""),
	EXCEL(5L, "EXCEL", ""), PDF(6L, "PDF", ""), ATTACHMENT(7L, "ATTACHMENT", "");

	private long assetId;

	private String name;

	private String description;

	AssetEnum(long asset_id, String name, String description) {
		this.assetId = asset_id;
		this.name = name;
		this.description = description;
	}

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
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
