package com.syntrontech.pmo.exception.client;

import com.syntrontech.autoTool.exception.client.ClientException;

public class OtherClintException  extends ClientException{
	
	private static final long serialVersionUID = 1L;
	
	private String errorCode = "UNKNOWN_ERROR";
	
	private String errorMessage = "unknown error";
	
	public OtherClintException(String error){
		errorCode = error.toUpperCase();
		errorMessage = error.toLowerCase();
	}
	
	public OtherClintException(){

	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}


}
