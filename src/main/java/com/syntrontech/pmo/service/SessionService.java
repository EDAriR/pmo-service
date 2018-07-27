package com.syntrontech.pmo.service;

import java.util.Optional;

import com.syntrontech.pmo.model.Session;


public interface SessionService {
	
	Optional<Session> findSessionBySessionToken(String token);
	
	void updateExpiredTime(String token);
	
}
