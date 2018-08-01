package com.syntrontech.pmo.service.imp;

import com.syntrontech.pmo.pmo.PmoWebService;
import com.syntrontech.pmo.pmo.model.VitalRecord;
import com.syntrontech.pmo.util.DateTime;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;

import java.util.Arrays;
import java.util.Date;

public class PmoResultServiceImp {

    private PmoWebService pmoWS;

    private void syncPmo(SystemUser user, Date recordDate, UserValueRecord record, VitalRecord.Type type, String zone,
                         Number... numbers) {
        String _recordDate = DateTime.format(recordDate, zone, "yyyy-MM-dd HH:mm:ss");

        VitalRecord _record = new VitalRecord();
        String idNo = user.getUserAccount();
        if (StringUtils.isNotBlank(idNo)) {
            _record.setIdNo(idNo);
        } else {
            String cardId = user.getCards().stream().filter(card -> StringUtils.isNotBlank(card.getCardId()))
                    .map(card -> card.getCardId()).findFirst()
                    .orElseThrow(() -> new ServiceException("Card ID is not found"));
            _record.setIdentifier(cardId);
        }
        _record.setLocationId(record.getLocationId());
        _record.setType(type);
        _record.setmTime(_recordDate);
        _record.setInputType(VitalRecord.InputType.Device);
        _record.setValues(Arrays.asList(numbers));

        // 台東
        if (VitalRecord.Type.BG.equals(type)) {
            _record.setMark(getBGType(numbers[1].intValue()));
        }
        System.out.println("@@@@@ 上傳之量測記錄: " + _record.toString());
        // 台東end

        pmoWS.uploadVitalRecord(_record, response -> updateRecordStatus(record, response));
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
