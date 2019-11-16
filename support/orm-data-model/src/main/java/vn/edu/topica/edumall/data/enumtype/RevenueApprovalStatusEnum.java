package vn.edu.topica.edumall.data.enumtype;

public enum RevenueApprovalStatusEnum {

    PENDING(0L, "PENDING", "PENDING"), APPROVED(1L, "APPROVED", "APPROVED"), REJECTED(2L, "REJECTED", "REJECTED");

    private long approvedId;

    private String name;

    private String description;

    RevenueApprovalStatusEnum(long approvedId, String name, String description) {
        this.approvedId = approvedId;
        this.name = name;
        this.description = description;
    }

    public long getAssetId() {
        return approvedId;
    }

    public void setAssetId(long assetId) {
        this.approvedId = assetId;
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