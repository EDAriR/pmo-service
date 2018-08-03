package com.syntrontech.pmo.model;

import com.syntrontech.pmo.model.common.PmoStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pmo_result")
public class PmoResult {
    // TABLE pmo_result

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
    // measurement_type
    @Column(name = "measurement_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurementPMOType measurementType;
    // record_id
    @Column(name = "record_id", nullable = false)
    private Long recordId;
    // result
    @Column(name = "result", nullable = false)
    private String result;
    // status
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PmoStatus pmoStatus;
    // synctime
    @Column(name = "synctime", nullable = false)
    private Date synctime;
    // tenant_id
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Override
    public String toString() {
        return "PmoResult:[" +
                "userId=" + userId + ", " +
                "measurementType=" + measurementType + ", " +
                "recordId=" + recordId + ", " +
                "result=" + result + ", " +
                "tenantId=" + tenantId + ", " +
                "]";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MeasurementPMOType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementPMOType measurementType) {
        this.measurementType = measurementType;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
