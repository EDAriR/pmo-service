package com.syntrontech.pmo.healthCheck;

import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

public class HealthCheckBuilder {
	private HealthCheckRegistry checks = new HealthCheckRegistry();
	private Map<String, HealthCheck> checkList = new HashMap<>();
	
	public void setCheckList(Map<String, HealthCheck> checkList){
		this.checkList = checkList;
	}
	
	public Map<String, HealthCheck> getCheckList(){
		return this.checkList;
	}
	
	public HealthCheckRegistry build(){
		checkList.entrySet().stream()
			.forEach(entity -> checks.register(entity.getKey(), entity.getValue()));
		return checks;
	}
}
