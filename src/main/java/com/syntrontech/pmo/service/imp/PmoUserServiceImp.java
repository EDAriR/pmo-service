package com.syntrontech.pmo.service.imp;

import com.syntrontech.pmo.model.auth.model.solr.SolrUser;
import com.syntrontech.pmo.pmo.PmoWebService;
import com.syntrontech.pmo.pmo.model.ResultMessage;
import com.syntrontech.pmo.pmo.model.UserData;
import com.syntrontech.pmo.redis.model.RedisUser;
import com.syntrontech.pmo.solr.model.SolrSubject;
import com.syntrontech.pmo.util.DateTime;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PmoUserServiceImp {

    private PmoWebService pmoWS;

    private UserData buildPmoUser(SystemUser user, String areaCode) {

        // TODO
        SolrSubject solrSubject = new SolrSubject();
        RedisUser ru = new RedisUser();
        SolrUser su = new SolrUser();

        UserData pmoUser = new UserData();
        pmoUser.setIdno(solrSubject.getId());
        pmoUser.setPassword(ru.getPmoPassword());

        SystemUserCard card = su.getCards_ss();
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

        String[] emails = ru.getEmails();
        if(emails != null && emails.length != 0)
            pmoUser.setEmail(ru.getEmails()[0]);
        char _sex = solrSubject.getSubjectGender_s() != null? solrSubject.getSubjectGender_s().charAt(0):null;
        pmoUser.setSex("" + _sex);
        String[] mobilePhones = su.getMobilePhones_ss();
        String mobilPhone = null;
        String telPhone = null;
        if(mobilePhones != null && mobilePhones.length != 0){

            for (int i = 0; i < mobilePhones.length; i++) {
                if(i == 0)
                    telPhone = fixPhoneNum(mobilePhones[i]);
                if(i == 1)
                    mobilPhone = fixMobilePhoneNum(mobilePhones[i]);
            }
        }

        pmoUser.setTel(telPhone);
        //pmoUser.setTel(fixPhoneNum(user.getUserPhone())); // 區碼用()包起來，例：(02)23456677
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        pmoUser.setBirth(f.format(solrSubject.getSubjectBirthday_dt()));
        //以下台東
        pmoUser.setName(solrSubject.getSubjectName_s());
        pmoUser.setAddress(solrSubject.getSubjectAddress_s());
        pmoUser.setType("01");	// 2016-06-24  將Type預設為01(個人資料未同設備綁定為社區(01)或居家(02))
        pmoUser.setGroup("01");	// 2016-06-24  將Group預設為01(個人資料未區分使用者族群為無(01)或弱勢族群(02))
        pmoUser.setMobilePhone(fixMobilePhoneNum(user.getUserMobile()));
        pmoUser.setIsHTM(solrSubject.getSubjectPersonalHistory_ss());;
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



    public String fixPhoneNum(String phone) {
       if (phone.length() >= 9 || phone.length() < 11) {
            StringBuffer buff = new StringBuffer();
            buff.append(phone);
            buff.insert(0, "(");
            buff.insert(3, ")");
            phone = buff.toString();
        }else {
           phone = fixMobilePhoneNum(phone);
       }
        return phone;
    }

    public String fixMobilePhoneNum(String mobliePhone) {
        Pattern pattern = Pattern.compile("\\D");
        Matcher matcher = pattern.matcher(mobliePhone);
        return matcher.replaceAll("").trim();
    }

}

