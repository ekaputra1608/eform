package com.eform.controller;

import com.eform.service.EformService;
import com.eform.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

public abstract class BaseController {

	@Autowired protected GenericService genericService;
	@Autowired protected EformService eformService;
	
	@Value("${app.url}")
	protected String APP_URL;
	
	@Value("${app.local.storage.path}")
	private String LOCAL_STORAGE_PATH;
    
    protected File getLocalStorage() {
		File localStorage = new File(LOCAL_STORAGE_PATH);
		if (!localStorage.exists()) {
			localStorage.mkdirs();
		}
		
		return localStorage;
	}
	
	@ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e) {
		return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
