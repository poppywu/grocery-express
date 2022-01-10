package cs6310.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ArchiveId implements Serializable {
    @Column(name="archive_store_name")
    private String archiveStoreName;
    @Column(name="archive_order_id")
    private String archiveOrderId;

    public ArchiveId() {
    }

    public ArchiveId(String archiveStoreName, String archiveOrderId) {
        this.archiveStoreName = archiveStoreName;
        this.archiveOrderId = archiveOrderId;
    }

    public String getArchiveStoreName() {
        return archiveStoreName;
    }

    public void setArchiveStoreName(String archiveStoreName) {
        this.archiveStoreName = archiveStoreName;
    }

    public String getArchiveOrderId() {
        return archiveOrderId;
    }

    public void setArchiveOrderId(String archiveOrderId) {
        this.archiveOrderId = archiveOrderId;
    }
}
