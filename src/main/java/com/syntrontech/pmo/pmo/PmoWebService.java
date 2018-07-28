package com.syntrontech.pmo.pmo;

import java.util.concurrent.Future;

import javax.xml.ws.AsyncHandler;

import com.syntrontech.syncare.external.pmo.model.ResultMessage;
import com.syntrontech.syncare.external.pmo.model.UserData;
import com.syntrontech.syncare.external.pmo.model.VitalRecord;
import com.syntrontech.syncare.external.pmo.vitalsign.UploadVitalRecordResponse;


public interface PmoWebService {
	public ResultMessage uploadVitalRecord(VitalRecord record);
	
	public Future<?> uploadVitalRecord(VitalRecord record, AsyncHandler<UploadVitalRecordResponse> asyncHandler);

	public ResultMessage registerUserByVendor(UserData user);
	
	public ResultMessage uploadUserData(UserData user);
	
	public String getDefaultAreaCode();
}
