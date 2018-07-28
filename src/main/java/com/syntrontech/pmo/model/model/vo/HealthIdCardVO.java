package com.syntrontech.pmo.model.model.vo;

import com.syntrontech.autoTool.annotation.ParamNotNull;
import com.syntrontech.autoTool.annotation.ParamStringRestrict;

import java.util.Objects;
import java.util.Set;

public class HealthIdCardVO {
	
	@ParamNotNull
	@ParamStringRestrict
	private String userId;
	
	@ParamStringRestrict
	private String userName;
	
	@ParamNotNull
	@ParamStringRestrict
	private String password;
	
	@ParamNotNull
	@ParamStringRestrict(pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$")
	private String email;
	
	@ParamStringRestrict(pattern="[0-9]{5,15}")
	private String mobilePhone;
	
	@ParamNotNull
	@ParamStringRestrict
	private Set<String> cardIds;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		if(Objects.isNull(userName)){
			return "user";
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Set<String> getCardIds() {
		return cardIds;
	}

	public void setCardIds(Set<String> cardIds) {
		this.cardIds = cardIds;
	}
	
}
