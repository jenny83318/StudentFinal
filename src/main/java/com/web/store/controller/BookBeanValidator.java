package com.web.store.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.web.store.model.BookBean;

public class BookBeanValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BookBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//....

	}

}
