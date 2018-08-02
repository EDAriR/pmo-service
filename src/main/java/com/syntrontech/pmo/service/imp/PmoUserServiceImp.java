package com.syntrontech.pmo.service.imp;

import com.syntrontech.autoTool.exception.client.ParamFormatErrorException;
import com.syntrontech.pmo.exception.client.ObjectNotExistedException;
import com.syntrontech.pmo.exception.server.InternalServiceException;
import com.syntrontech.pmo.model.common.PersonalHistoryType;
import com.syntrontech.pmo.model.common.YN;
import com.syntrontech.pmo.redis.model.RedisSubject;
import com.syntrontech.pmo.restful.to.SearchTO;
import com.syntrontech.pmo.solr.Solr;
import com.syntrontech.pmo.solr.SolrException;
import com.syntrontech.pmo.solr.SolrFilterNameConverter;
import com.syntrontech.pmo.solr.SolrSearchModel;
import com.syntrontech.pmo.solr.model.SolrUser;
import com.syntrontech.pmo.pmo.PmoWebService;
import com.syntrontech.pmo.pmo.model.ResultMessage;
import com.syntrontech.pmo.pmo.model.UserData;
import com.syntrontech.pmo.solr.model.SolrSubject;
import com.syntrontech.pmo.solr.model.syncareservice.SolrEmergencyContacts;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PmoUserServiceImp {

    private PmoWebService pmoWS;

    @Autowired
    private Solr solrService;

    @Override
    public SystemUserTO updateUser(RedisSubject redisSubject, String areaCode) {
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
    }

    private UserData buildPmoUser(RedisSubject redisSubject, String areaCode) {

        // TODO
        SolrSubject solrSubject = null;
        SolrUser solrUser = null;
        try {
            solrSubject = searchSubject(redisSubject);
            solrUser = searchUser(redisSubject);
        } catch (InternalServiceException e) {
            e.printStackTrace();
        } catch (SolrException e) {
            e.printStackTrace();
        } catch (ParamFormatErrorException e) {
            e.printStackTrace();
        } catch (ObjectNotExistedException e) {
            e.printStackTrace();
        }

        UserData pmoUser = new UserData();
        pmoUser.setIdno(redisSubject.getId());
        // TODO
        pmoUser.setPassword(redisUser.getPmoPassword());

        /**台東曾經有三張卡**/
        String[] cards = solrUser.getCards_ss();
        if(cards != null){
            int i = 0;
            for(String c:Arrays.asList(cards)){
                if(i == 0)
                    pmoUser.setIdentifier(c);
                if(i == 1)
                    pmoUser.setIdentifier2(c);
                if(i == 2)
                    pmoUser.setIdentifier3(c);
            }
        }

        String[] emails = solrUser.getEmails_ss();
        if(emails != null && emails.length != 0)
            pmoUser.setEmail(solrUser.getEmails_ss()[0]);

        char _sex = solrSubject.getSubjectGender_s() != null? solrSubject.getSubjectGender_s().charAt(0):'M';
        pmoUser.setSex("" + _sex);
        String[] mobilePhones = solrUser.getMobilePhones_ss();
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

        // TODO
        pmoUser.setType("01");	// 2016-06-24  將Type預設為01(個人資料未同設備綁定為社區(01)或居家(02))
        pmoUser.setGroup("01");	// 2016-06-24  將Group預設為01(個人資料未區分使用者族群為無(01)或弱勢族群(02))

        String[] personalHistorys = solrSubject.getSubjectPersonalHistory_ss();

        YN isHTN = YN.N;
        YN isDM = YN.N;
        if(personalHistorys != null){
            for(String p:Arrays.asList(personalHistorys)){
                if(p.equals(PersonalHistoryType.HYPERTENSION)){
                    isHTN = YN.Y;
                }
                if(p.equals(PersonalHistoryType.DIABETES_MELLITUS)){
                    isDM = YN.Y;
                }
            }
        }

        pmoUser.setIsHTM(isHTN);
        pmoUser.setIsDM(isDM);

        List<SolrEmergencyContacts> es = null;
        try {
            es = findBySubjectIdAndTenantId(redisSubject.getId(), redisSubject.getTenantId());
        } catch (SolrException e) {
            e.printStackTrace();
        } catch (ParamFormatErrorException e) {
            e.printStackTrace();
        }

        if(es != null && es.size() != 0){
            SolrEmergencyContacts solrEmergencyContacts = es.get(0);
            pmoUser.setIsAlert(YN.Y);
            pmoUser.setAlertNotifierName(solrEmergencyContacts.getName_s());
            pmoUser.setAlertNotifierEmail(solrEmergencyContacts.getEmail_s());
            pmoUser.setAlertNotifierMobilePhone(solrEmergencyContacts.getPhone_s());
        }

        // TODO 台東end
        if (StringUtils.isNotBlank(areaCode)) {
            pmoUser.setAreaCode(areaCode);
        }

        return pmoUser;
    }

    private SolrUser searchUser(RedisSubject redisSubject) throws SolrException, InternalServiceException, ParamFormatErrorException, ObjectNotExistedException {

        String keyword = "*";
        String tenantIdFilter = "tenantId"+":"+ redisSubject.getUserId();
        String userIdFilter = "userId"+":"+ redisSubject.getTenantId();
        List<String> filters = new ArrayList<>();
        filters.add(tenantIdFilter);
        filters.add(userIdFilter);

        filters = SolrFilterNameConverter.convertFilterNames(filters, new SolrUser());

        SolrSearchModel<SolrUser> searchModel = solrService
                .searchJsonDoc(keyword, 1, 10
                , null, null, filters, SolrUser.class);

        if( searchModel != null && searchModel.getContent().size() != 0)
            return searchModel.getContent().get(0);
        else
            throw new ObjectNotExistedException("SolrUser");

    }

    private SolrSubject searchSubject(RedisSubject redisSubject)
            throws InternalServiceException, SolrException, ParamFormatErrorException, ObjectNotExistedException {
        String keyword = "*";

        String tenantIdFilter = "tenantId"+":"+ redisSubject.getTenantId();
        String userIdFilter = "userId"+":"+ redisSubject.getUserId();
        String subjectIdFilter = "subjectId"+":"+ redisSubject.getId();
        List<String> filters = new ArrayList<>();
        filters.add(tenantIdFilter);
        filters.add(userIdFilter);
        filters.add(subjectIdFilter);

        filters = SolrFilterNameConverter.convertFilterNames(filters, new SolrSubject());

        SolrSearchModel<SolrSubject> searchModel = solrService.searchJsonDoc(keyword, 1, 10
                , null, null, filters, SolrSubject.class);

        List<SolrSubject> ss = searchModel.getContent();

        if(ss != null && ss.size() != 0)
            return ss.get(0);
        else
            throw new ObjectNotExistedException("SolrSubject");
    }


    private List<SolrEmergencyContacts> findBySubjectIdAndTenantId(String subjectId, String tenantId) throws SolrException, ParamFormatErrorException {

        String keyword = "*";

        String tenantIdFilter = "tenantId"+":"+tenantId;
        String userIdFilter = "subjectId"+":"+subjectId;

        List<String> filters = new ArrayList<>();
        filters.add(tenantIdFilter);
        filters.add(userIdFilter);

        filters = SolrFilterNameConverter.convertFilterNames(filters, new SolrEmergencyContacts());

        SolrSearchModel<SolrEmergencyContacts> searchModel = solrService.searchJsonDoc(keyword, 1, 2147483647, null, null, filters, SolrEmergencyContacts.class);

        return searchModel.getContent();
    }


//    private UserData uploadPmoUser(SystemUser user, String areaCode) {
//        final String zone = "+8";
//
//        UserData pmoUser = new UserData();
//        pmoUser.setIdno(user.getUserAccount());
//        pmoUser.setPassword(user.getPmoPassword());
//
//        for (int i = 0; i < user.getCards().size(); i++) {
//            SystemUserCard card = user.getCards().get(i);
//            if (i == 0) {
//                pmoUser.setIdentifier(card.getCardId());
//            } else if (i == 1) {
//                pmoUser.setIdentifier2(card.getCardId() == null ? "" : card.getCardId());
//            } else if (i == 2) {
//                pmoUser.setIdentifier3(card.getCardId() == null ? "" : card.getCardId());
//            }
//        }
//
//        pmoUser.setEmail(user.getUserEmail());
//        char _sex = user.getSex().name().toUpperCase().charAt(0);
//        pmoUser.setSex("" + _sex);
//        pmoUser.setTel(fixPhoneNum(user.getUserPhone())); // 區碼用()包起來，例：(02)23456677
//        pmoUser.setBirth(DateTime.format((Date) user.getUserBirthday(), zone, "yyyy/MM/dd"));
//        pmoUser.setName(user.getUserDisplayName());
//        pmoUser.setAddress(user.getUserAddress());
//        pmoUser.setType("01");	// 2016-06-24  將Type預設為01(個人資料未同設備綁定為社區(01)或居家(02))
//        pmoUser.setGroup("01");	// 2016-06-24  將Group預設為01(個人資料未區分使用者族群為無(01)或弱勢族群(02))
//        pmoUser.setMobilePhone(fixMobilePhoneNum(user.getUserMobile()));
//        pmoUser.setIsHTM(user.getWithHighBloodPressure());;
//        pmoUser.setIsDM(user.getWithDiabetesMellitus());
//        pmoUser.setIsAlert(user.getAlert());
//        pmoUser.setAlertNotifierName(StringUtils.isBlank(user.getAlertNotifierName()) ? "" : user.getAlertNotifierName());
//        pmoUser.setAlertNotifierMobilePhone(StringUtils.isBlank(fixMobilePhoneNum(user.getAlertNotifierMobilePhone())) ? "" : fixMobilePhoneNum(user.getAlertNotifierMobilePhone()));
//        pmoUser.setAlertNotifierEmail(StringUtils.isBlank(user.getAlertNotifierEmail()) ? "" : user.getAlertNotifierEmail());
//
//        if (StringUtils.isNotBlank(areaCode)) {
//            pmoUser.setAreaCode(areaCode);
//        }
//        return pmoUser;
//    }


    public String fixPhoneNum(String phone) {

        // TODO
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

