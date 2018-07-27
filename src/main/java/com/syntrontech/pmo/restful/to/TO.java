package com.syntrontech.pmo.restful.to;

public interface TO<T> {
	TO<T> convertFrom(T model);
}
