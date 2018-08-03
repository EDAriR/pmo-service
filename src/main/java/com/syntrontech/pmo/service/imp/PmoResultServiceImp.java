package com.syntrontech.pmo.service.imp;

import com.syntrontech.pmo.pmo.PmoWebService;
import com.syntrontech.pmo.pmo.model.VitalRecord;
import com.syntrontech.pmo.pmo.vitalsign.UploadVitalRecordResponse;
import com.syntrontech.pmo.solr.model.syncareservice.SolrBloodGlucose;
import com.syntrontech.pmo.solr.model.syncareservice.SolrBloodPressureHeartBeat;
import com.syntrontech.pmo.solr.model.syncareservice.SolrMeasurement;
import com.syntrontech.pmo.util.DateTime;
import com.syntrontech.pmo.util.ISODateTime;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PmoResultServiceImp {

    private PmoWebService pmoWS;

    public void syncBloodPressureHeartBeat() {

    }

    public void syncBloodGlucose() {

    }

    private void syncPmo(SolrMeasurement record, VitalRecord.Type type, String zone) {

        if(type == VitalRecord.Type.BP){
            record = record;
        }else {
            record = record;
        }

        ;
        String _recordDate = DateTime
                .format(ISODateTime.parseISOString(record.getRecordTime_dt()), zone, "yyyy-MM-dd HH:mm:ss");

        VitalRecord _record = new VitalRecord();
        String idNo = record.getSubjectId_s();

        _record.setIdNo(idNo);
        _record.setLocationId(record.getUnitId_s());
        _record.setType(type);
        _record.setmTime(_recordDate);
        _record.setInputType(VitalRecord.InputType.Device);

        List<Number> values = new ArrayList<>();

        if(type == VitalRecord.Type.BP){
            // 血壓三個數值依序為收縮壓 舒張壓 脈搏
            SolrBloodPressureHeartBeat recordBP = (SolrBloodPressureHeartBeat)record;
            values.add(recordBP.getSystolicPressure_i());
            values.add(recordBP.getDiastolicPressure_i());
            values.add(recordBP.getHeartRate_i());
        }else {
            SolrBloodGlucose recordBG = (SolrBloodGlucose)record;
            values.add(recordBG.getGlucose_l());
            // TODO FASTING_BLOOD_GLUCOSE, RANDOM_BLOOD_GLUCOSE, POSTPRANDIAL_BLOOD_GLUCOSE;
            _record.setMark("NM");

        }
        _record.setValues(values);

        System.out.println("@@@@@ 上傳之量測記錄: " + _record.toString());

        SolrMeasurement finalRecord = record;
        pmoWS.uploadVitalRecord(_record, response -> updateRecordStatus(finalRecord, response));
    }

    private void updateRecordStatus(SolrMeasurement record, Response<UploadVitalRecordResponse> response) {
        try {
            String result = response.get().getUploadVitalRecordResult();
            System.out.println("衛福部平台回傳之訊息: " + result);
            Form form = new Form();
            form.param("result", result);
            javax.ws.rs.core.Response _response = ClientBuilder.newClient().target("http://localhost:8080").path("api")
                    .path("resource").path("vitalSign").path("status").path("" + record.getId())
                    .request().put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
            int status = _response.getStatus();
            System.out.println("衛福部平台回傳之訊息代碼:" + status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBGType(int bg_type) {
        switch (bg_type) {
            case 0:
                return "NM";
            case 1:
                return "AC";
            case 2:
                return "PC";
            default:
                return "NM";
        }
    }
}
