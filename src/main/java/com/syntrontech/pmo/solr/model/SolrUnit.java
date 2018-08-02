package com.syntrontech.pmo.solr.model;


import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SolrUnit implements SolrDoc {
	
	private String id;
	
	private String unitId_s;
	
	private String unitName_s;
	
	private String unitParentId_s;
	
	private String unitParentName_s;
	
	private String tenantId_s;
	
	private String unitMeta_s;
	
	private String unitStatus_s;
	
	private String unitCreateTime_dt;
	
	private String unitUpdateTime_dt;
	
	private static Map<String, String> fieldNameMap;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Map<String, String> createFieldNameMap() {
		return null;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitId_s() {
		return unitId_s;
	}

	public void setUnitId_s(String unitId_s) {
		this.unitId_s = unitId_s;
	}

	public String getUnitName_s() {
		return unitName_s;
	}

	public void setUnitName_s(String unitName_s) {
		this.unitName_s = unitName_s;
	}

	public String getUnitParentId_s() {
		return unitParentId_s;
	}

	public void setUnitParentId_s(String unitParentId_s) {
		this.unitParentId_s = unitParentId_s;
	}

	public String getUnitParentName_s() {
		return unitParentName_s;
	}

	public void setUnitParentName_s(String unitParentName_s) {
		this.unitParentName_s = unitParentName_s;
	}

	public String getTenantId_s() {
		return tenantId_s;
	}

	public void setTenantId_s(String tenantId_s) {
		this.tenantId_s = tenantId_s;
	}

	public String getUnitMeta_s() {
		return unitMeta_s;
	}

	public void setUnitMeta_s(String unitMeta_s) {
		this.unitMeta_s = unitMeta_s;
	}

	public String getUnitStatus_s() {
		return unitStatus_s;
	}

	public void setUnitStatus_s(String unitStatus_s) {
		this.unitStatus_s = unitStatus_s;
	}

	public String getUnitCreateTime_dt() {
		return unitCreateTime_dt;
	}

	public void setUnitCreateTime_dt(String unitCreateTime_dt) {
		this.unitCreateTime_dt = unitCreateTime_dt;
	}

	public String getUnitUpdateTime_dt() {
		return unitUpdateTime_dt;
	}

	public void setUnitUpdateTime_dt(String unitUpdateTime_dt) {
		this.unitUpdateTime_dt = unitUpdateTime_dt;
	}
	

	protected void createFieldNameMapIfNotExist(){
		if(Objects.isNull(fieldNameMap)){
			fieldNameMap = new ConcurrentHashMap<>();
			fieldNameMap.put("unitId", "unitId_s");
			fieldNameMap.put("unitName", "unitName_s");
			fieldNameMap.put("parentUnitId", "unitParentId_s");
			fieldNameMap.put("parentUnitName", "unitParentName_s");
			fieldNameMap.put("tenantId", "tenantId_s");
			fieldNameMap.put("status", "unitStatus_s");
			fieldNameMap.put("createTime", "unitCreateTime_dt");
			fieldNameMap.put("updateTime", "unitUpdateTime_dt");
		}
	}

}
