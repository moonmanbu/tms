package cc.topush.tms.model;

import act.db.CreatedAt;
import act.db.LastModifiedAt;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class ModelBase {

    @CreatedAt
    private Date created;

    @LastModifiedAt
    private Date lastModified;

    public Date getCreated() {
        return created;
    }

    public Date getLastModified() {
        return lastModified;
    }

}
