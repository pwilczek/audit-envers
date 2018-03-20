package name.wilu.auditenvers;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionListener;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Table(name = "audit_master")
@RevisionEntity(AuditMaster.AuditMasterListener.class)
public class AuditMaster extends DefaultRevisionEntity {
    //
    public String operation;
    //
    public static class AuditMasterListener implements RevisionListener {

        @Override public void newRevision(Object revisionEntity) {
            if (revisionEntity instanceof AuditMaster) {
                AuditMaster am = (AuditMaster) revisionEntity;
                am.operation = "todo";
            }
        }
    }
}
