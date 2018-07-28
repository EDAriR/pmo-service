package com.syntrontech.pmo.model.model.vo;

import com.syntrontech.autoTool.annotation.ParamNotNull;
import com.syntrontech.autoTool.annotation.ParamStringRestrict;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeleteUnitMetaVO {
	
	@ParamNotNull
	@QueryParam("unitIds")
	@ParamStringRestrict
	Set<String> unitIds;
	
	public List<String> getUnitIds() {
		return unitIds.stream()
					  .collect(Collectors.toList());
	}

	public void setUnitIds(Set<String> unitIds) {
		this.unitIds = unitIds;
	}
}
