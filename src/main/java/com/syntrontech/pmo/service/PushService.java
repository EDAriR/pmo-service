package com.syntrontech.pmo.service;

import java.util.List;

import com.syntrontech.autoTool.exception.server.ServerException;
import com.syntrontech.pmo.model.common.DeviceType;

public interface PushService {
	
	void pushMessage(List<String> targetUserIds, List<DeviceType> targetDevices, Object message, String image) throws ServerException;
}
