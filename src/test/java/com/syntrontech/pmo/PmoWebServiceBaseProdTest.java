package com.syntrontech.pmo;

import java.util.Arrays;

import com.syntrontech.pmo.pmo.PmoWebServiceBaseProd;
import com.syntrontech.pmo.pmo.PmoWebServiceSettings;
import com.syntrontech.pmo.pmo.model.ResultMessage;
import com.syntrontech.pmo.pmo.model.UserData;
import com.syntrontech.pmo.pmo.model.VitalRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PmoWebServiceBaseProdTest { 
	@Mock
	private PmoWebServiceSettings settings;
	@InjectMocks
	private PmoWebServiceBaseProd pmoWs;

	@Before
	public void setUp() throws Exception {
		doReturn("http://service.telecare.com.tw/MessageHubWS_test01/").when(settings).getUrl();
		doReturn("Cyshb").when(settings).getPmoAccount();
		doReturn("Test01gb").when(settings).getPmoCredentials();
	}

	@Test
	public void testRegisterUser() throws Exception {
		UserData user = new UserData();
		user.setIdno("B123456789");
		user.setPassword("B19860103");
		user.setIdentifier("123456789");
		user.setEmail("jason_chen@syntrontech.com");
		user.setSex("M");
		user.setTel("123456789");
		user.setAreaCode("10010");
		user.setBirth("1986/01/03");
		ResultMessage message = pmoWs.registerUserByVendor(user);
		System.out.println("testRegisterUser MESSAGE:" + message);
	}
	
	@Test
	public void testUploadUserData() throws Exception {
		UserData user = new UserData();
		user.setIdno("B123456789");
		user.setPassword("B19860103");
		user.setIdentifier("123456789");
		user.setEmail("jason_chen@syntrontech.com");
		user.setSex("M");
		user.setTel("123456789");
		user.setAreaCode("10010");
		user.setBirth("1986/01/03");
		ResultMessage message = pmoWs.uploadUserData(user);
		System.out.println("testUploadUserData MESSAGE:" + message);
	}

	@Test
	public void testGetUsers() throws Exception {
	}

	@Test
	public void testValidateUser() throws Exception {
	}

//	@Test
	public void testUploadVitalRecordVitalRecord() throws Exception {
		VitalRecord record = new VitalRecord();
		record.setIdNo("B123456789");
//		record.setIdentifier("4143357370");
		record.setLocationId("660010000101");
		record.setType(VitalRecord.Type.BP);
		record.setmTime("2015-04-15 13:40:00");
		record.setInputType(VitalRecord.InputType.Device);
		record.setValues(Arrays.asList(110, 75, 65));
		ResultMessage message = pmoWs.uploadVitalRecord(record);
		System.out.println("testUploadVitalRecordVitalRecord:" + message);
	}

	@Test
	public void testGetVitalRecord() throws Exception {
	}

	@Test
	public void testChangeUserPassword() throws Exception {
	}

	@Test
	public void testResetUserPassword() throws Exception {
	}

}
