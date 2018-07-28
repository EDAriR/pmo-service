package com.syntrontech.pmo.model.auth.model.solr;

import com.syntrontech.pmo.solr.InternalServiceException;
import com.syntrontech.pmo.solr.SolrDoc;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class SolrGroup implements SolrDoc {
	
	private String id;
	
	private String groupId_s;
	
	private String groupName_s;
	
	private String tenantId_s;
	
	private String[] userIds_ss;
	
	private String groupMeta_s;
	
	private String groupCreateTime_dt;
	
	private String groupUpdateTime_dt;
	
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

	public String getGroupId_s() {
		return groupId_s;
	}

	public void setGroupId_s(String groupId_s) {
		this.groupId_s = groupId_s;
	}

	public String getGroupName_s() {
		return groupName_s;
	}

	public void setGroupName_s(String groupName_s) {
		this.groupName_s = groupName_s;
	}

	public String getTenantId_s() {
		return tenantId_s;
	}

	public void setTenantId_s(String tenantId_s) {
		this.tenantId_s = tenantId_s;
	}

	public String[] getUserIds_ss() {
		return userIds_ss;
	}

	public void setUserIds_ss(String[] userIds_ss) {
		this.userIds_ss = userIds_ss;
	}

	public String getGroupMeta_s() {
		return groupMeta_s;
	}

	public void setGroupMeta_s(String groupMeta_s) {
		this.groupMeta_s = groupMeta_s;
	}

	public String getGroupCreateTime_dt() {
		return groupCreateTime_dt;
	}

	public void setGroupCreateTime_dt(String groupCreateTime_dt) {
		this.groupCreateTime_dt = groupCreateTime_dt;
	}

	public String getGroupUpdateTime_dt() {
		return groupUpdateTime_dt;
	}

	public void setGroupUpdateTime_dt(String groupUpdateTime_dt) {
		this.groupUpdateTime_dt = groupUpdateTime_dt;
	}
	
	protected void createFieldNameMapIfNotExist(){
		if(Objects.isNull(fieldNameMap)){
			fieldNameMap = new ConcurrentHashMap<>();
			fieldNameMap.put("groupId", "groupId_s");
			fieldNameMap.put("groupName", "groupName_s");
			fieldNameMap.put("tenantId", "tenantId_s");
			fieldNameMap.put("userIds", "userIds_ss");
			fieldNameMap.put("createTime", "groupCreateTime_dt");
			fieldNameMap.put("updateTime", "groupUpdateTime_dt");
		}
	}
	

	public String getFieldNameTransformTOFieldName(String fieldName) throws InternalServiceException {
		createFieldNameMapIfNotExist();
		if(!fieldNameMap.containsKey(fieldName)){
			throw new InternalServiceException("can't find mapping solr doc field name from ["+fieldName+"]");
		}
		return fieldNameMap.get(fieldName);
	}

	public Set<String> findTOFieldNames() {
		createFieldNameMapIfNotExist();
		return fieldNameMap.keySet();
	}
	
}
