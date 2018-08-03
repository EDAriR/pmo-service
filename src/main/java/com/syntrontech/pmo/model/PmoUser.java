package com.syntrontech.pmo.model;

import com.syntrontech.pmo.model.common.PmoStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pmo_user")
public class PmoUser {

    // TABLE pmo_user
    // sequence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence", nullable = false)
    private Long sequence;
    // user_id
    @Column(name = "user_id", nullable = false)
    private String userId;
    // subject_id
    @Column(name = "subject_id", nullable = false)
    private String subjectId;
    // pmo_password
    @Column(name = "pmo_password", nullable = false)
    private String pmoPassword;
    // status
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PmoStatus pmoStatus;
    // synctime
    @Column(name = "synctime")
    private Date synctime;
    // tenant_id
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPmoPassword() {
        return pmoPassword;
    }

    public void setPmoPassword(String pmoPassword) {
        this.pmoPassword = pmoPassword;
    }

    public PmoStatus getPmoStatus() {
        return pmoStatus;
    }

    public void setPmoStatus(PmoStatus pmoStatus) {
        this.pmoStatus = pmoStatus;
    }

    public Date getSynctime() {
        return synctime;
    }

    public void setSynctime(Date synctime) {
        this.synctime = synctime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "PmoUser:[" +
                "sequence=" + sequence + ", " +
                "userId=" + userId + ", " +
                "pmoPassword=" + pmoPassword+ ", " +
                "pmoStatus=" + pmoStatus + ", " +
                "synctime=" + synctime + ", " +
                "tenantId=" + tenantId + ", " +
                "]";
    }

}
