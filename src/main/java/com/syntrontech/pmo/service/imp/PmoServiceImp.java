package com.syntrontech.pmo.service.imp;

import com.syntrontech.pmo.pmo.model.ResultMessage;
import com.syntrontech.pmo.pmo.model.UserData;
import com.syntrontech.pmo.pmo.model.VitalRecord;
import com.syntrontech.pmo.redis.model.RedisSubject;
import com.syntrontech.pmo.redis.model.RedisUser;
import com.syntrontech.pmo.solr.model.SolrSubject;
import com.syntrontech.pmo.util.DateTime;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class PmoService {

    private PmoService pmoWS;

    private UserData buildPmoUser(SystemUser user, String areaCode) {

        // TODO
        SolrSubject ss = new SolrSubject();
        RedisUser ru = new RedisUser();

        UserData pmoUser = new UserData();
        pmoUser.setIdno(rs.getUserId());
        pmoUser.setPassword(ru.getPmoPassword());

        SystemUserCard card = ru.().get(0);
        pmoUser.setIdentifier(card.getCardId());

        /**台東有三張卡
         *		for (int i = 0; i < user.getCards().size(); i++) {
         *	SystemUserCard card = user.getCards().get(i);
         *	if (i == 0) {
         *		pmoUser.setIdentifier(card.getCardId());
         *	} else if (i == 1) {
         *		pmoUser.setIdentifier2(card.getCardId() == null ? "" : card.getCardId());
         *	} else if (i == 2) {
         *		pmoUser.setIdentifier3(card.getCardId() == null ? "" : card.getCardId());
         *	}
         *}
         **/

        pmoUser.setEmail(rs.());
        char _sex = user.getSex().name().toUpperCase().charAt(0);
        pmoUser.setSex("" + _sex);
        pmoUser.setTel(user.getUserPhone());
        //pmoUser.setTel(fixPhoneNum(user.getUserPhone())); // 區碼用()包起來，例：(02)23456677
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        pmoUser.setBirth(f.format(user.getUserBirthday()));
        //以下台東
        pmoUser.setName(user.getUserDisplayName());
        pmoUser.setAddress(user.getUserAddress());
        pmoUser.setType("01");	// 2016-06-24  將Type預設為01(個人資料未同設備綁定為社區(01)或居家(02))
        pmoUser.setGroup("01");	// 2016-06-24  將Group預設為01(個人資料未區分使用者族群為無(01)或弱勢族群(02))
        pmoUser.setMobilePhone(fixMobilePhoneNum(user.getUserMobile()));
        pmoUser.setIsHTM(user.getWithHighBloodPressure());;
        pmoUser.setIsDM(user.getWithDiabetesMellitus());
        pmoUser.setIsAlert(user.getAlert());
        pmoUser.setAlertNotifierName(StringUtils.isBlank(user.getAlertNotifierName()) ? "" : user.getAlertNotifierName());
        pmoUser.setAlertNotifierMobilePhone(StringUtils.isBlank(fixMobilePhoneNum(user.getAlertNotifierMobilePhone())) ? "" : fixMobilePhoneNum(user.getAlertNotifierMobilePhone()));
        pmoUser.setAlertNotifierEmail(StringUtils.isBlank(user.getAlertNotifierEmail()) ? "" : user.getAlertNotifierEmail());
        //台東end
        if (StringUtils.isNotBlank(areaCode)) {
            pmoUser.setAreaCode(areaCode);
        }
        return pmoUser;
    }

    @Override
    public SystemUserTO updateUser(Integer id, SystemUserVO vo) {
        SystemUser user = userDao.get(id).orElseThrow(() -> new ServiceException("User id " + id + " is not found"));
        copy(user, vo);
        //台東
        if (vo.isWithHighBloodPressure()) {
            user.setCaseStatus(1);
            user.setCaseStatusDisplay(2);
        }
        UserData pmoUser = uploadPmoUser(user, user.getArea().getCode());
        ResultMessage pmoResult = pmoWS.uploadUserData(pmoUser);

        if ("A01".equalsIgnoreCase(pmoResult.getCode())) {
            return userDao.modify(user).orElse(user).convert(true);
        } else {
            throw new ServiceException("衛福部平台錯誤: " + pmoResult.getMessage());
        }
        //台東end

        //SystemUserTO userTO = userDao.modify(user).orElse(user).convert(true);

        //return convertTO(userTO, userTO.getAccount(), getUserDob(userTO.getAccount()));
    }

    private UserData uploadPmoUser(SystemUser user, String areaCode) {
        final String zone = "+8";

        UserData pmoUser = new UserData();
        pmoUser.setIdno(user.getUserAccount());
        pmoUser.setPassword(user.getPmoPassword());

        for (int i = 0; i < user.getCards().size(); i++) {
            SystemUserCard card = user.getCards().get(i);
            if (i == 0) {
                pmoUser.setIdentifier(card.getCardId());
            } else if (i == 1) {
                pmoUser.setIdentifier2(card.getCardId() == null ? "" : card.getCardId());
            } else if (i == 2) {
                pmoUser.setIdentifier3(card.getCardId() == null ? "" : card.getCardId());
            }
        }

        pmoUser.setEmail(user.getUserEmail());
        char _sex = user.getSex().name().toUpperCase().charAt(0);
        pmoUser.setSex("" + _sex);
        pmoUser.setTel(fixPhoneNum(user.getUserPhone())); // 區碼用()包起來，例：(02)23456677
        pmoUser.setBirth(DateTime.format((Date) user.getUserBirthday(), zone, "yyyy/MM/dd"));
        pmoUser.setName(user.getUserDisplayName());
        pmoUser.setAddress(user.getUserAddress());
        pmoUser.setType("01");	// 2016-06-24  將Type預設為01(個人資料未同設備綁定為社區(01)或居家(02))
        pmoUser.setGroup("01");	// 2016-06-24  將Group預設為01(個人資料未區分使用者族群為無(01)或弱勢族群(02))
        pmoUser.setMobilePhone(fixMobilePhoneNum(user.getUserMobile()));
        pmoUser.setIsHTM(user.getWithHighBloodPressure());;
        pmoUser.setIsDM(user.getWithDiabetesMellitus());
        pmoUser.setIsAlert(user.getAlert());
        pmoUser.setAlertNotifierName(StringUtils.isBlank(user.getAlertNotifierName()) ? "" : user.getAlertNotifierName());
        pmoUser.setAlertNotifierMobilePhone(StringUtils.isBlank(fixMobilePhoneNum(user.getAlertNotifierMobilePhone())) ? "" : fixMobilePhoneNum(user.getAlertNotifierMobilePhone()));
        pmoUser.setAlertNotifierEmail(StringUtils.isBlank(user.getAlertNotifierEmail()) ? "" : user.getAlertNotifierEmail());

        if (StringUtils.isNotBlank(areaCode)) {
            pmoUser.setAreaCode(areaCode);
        }
        return pmoUser;
    }

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

