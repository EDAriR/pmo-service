package com.syntrontech.syncare.healthCheck;

import javax.ws.rs.client.ClientBuilder;

import com.codahale.metrics.health.HealthCheck;
import com.syntrontech.syncare.external.pmo.PmoWebServiceSettings;

public class PMOHealthCheck extends HealthCheck {
	private PmoWebServiceSettings pmoWsSettings;
	
	public PmoWebServiceSettings getPmoWsSettings() {
		return pmoWsSettings;
	}

	public void setPmoWsSettings(PmoWebServiceSettings pmoWsSettings) {
		this.pmoWsSettings = pmoWsSettings;
	}

	@Override
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
