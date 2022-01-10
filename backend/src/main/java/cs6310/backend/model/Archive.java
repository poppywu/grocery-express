package cs6310.backend.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="archive")
public class Archive {
    @EmbeddedId
    private ArchiveId archiveId;
    private Date createDate;

    public Archive() {
    }

    public Archive(ArchiveId archiveId, Date createDate) {
        this.archiveId = archiveId;
        this.createDate = createDate;
    }

    public ArchiveId getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(ArchiveId archiveId) {
        this.archiveId = archiveId;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

