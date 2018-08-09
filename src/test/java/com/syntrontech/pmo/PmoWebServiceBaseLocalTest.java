package com.syntrontech.pmo;
 
import java.util.Arrays;

import com.syntrontech.pmo.pmo.PmoWebServiceBaseLocal;
import com.syntrontech.pmo.pmo.model.UserData;
import com.syntrontech.pmo.pmo.model.VitalRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;


 
@RunWith(MockitoJUnitRunner.class)
public class PmoWebServiceBaseLocalTest {
	@InjectMocks
	private PmoWebServiceBaseLocal service;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUploadVitalRecord() throws Exception {
		VitalRecord record = new VitalRecord(
				"M123456789", "2CE25FBB", "090200100101",
				VitalRecord.Type.BP, "2014/06/27 12:23:56", null,
				VitalRecord.InputType.Device, Arrays.asList(121, 76, 72),
				"aaa", "VC_HOME", "XARD0001");
		service.uploadVitalRecord(record, null);
	}
	
	@Test
	public void testUploadUserData() throws Exception {
		UserData userData = new UserData(
				"A1234567890",
				"2wsx1qaz",
				"88888888",
				"00000000",
				"77777777",
				"aaa@aaa.com",
				"F",
				"(02)11111111",
				"10003",
				"19840320",
				"aaa",
				"aaaaaa",
				"01",
				"01",
				"0911111111",
				null,
				null,
				null,
				"AAA",
				"0912222222",
				"YYY@YYY.com");
		service.uploadUserData(userData);
	}
}
