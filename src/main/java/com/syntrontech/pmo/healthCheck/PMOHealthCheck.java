package com.syntrontech.pmo.healthCheck;

import javax.ws.rs.client.ClientBuilder;

import com.codahale.metrics.health.HealthCheck.*;
import com.syntrontech.pmo.pmo.PmoWebServiceSettings;

public class PMOHealthCheck {
	private PmoWebServiceSettings pmoWsSettings;
	
	public PmoWebServiceSettings getPmoWsSettings() {
		return pmoWsSettings;
	}

	public void setPmoWsSettings(PmoWebServiceSettings pmoWsSettings) {
		this.pmoWsSettings = pmoWsSettings;
	}

	protected Result check() throws Exception {
		try{
			send("Member");
			send("VitalSign");
		}catch(Exception e){
			return Result.unhealthy(e);
		}
		return Result.healthy();
	}

	private void send(String serviceName){
		ClientBuilder.newClient()
			.target(pmoWsSettings.getUrl())
			.path(serviceName+".asmx")
			.request()
			.get();
	}
}
