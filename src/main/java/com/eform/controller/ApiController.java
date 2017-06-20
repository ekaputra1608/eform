package com.eform.controller;

import com.eform.entity.Eform;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class ApiController extends BaseController {

	@RequestMapping(value = "lab-results/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity labResults(@PathVariable String id, @RequestParam MultipartFile file) throws Exception {
		
		Eform eform = (Eform) genericService.get(Eform.class, "verificationId", id);
		eform.setFilename(file.getOriginalFilename());
		
		genericService.saveOrUpdate(eform);
		
		File upload = new File(getLocalStorage(), eform.getId());
		file.transferTo(upload);
		
		Map data = new HashMap();
		data.put("eform", eform);
		data.put("file", APP_URL + "/api/v1/lab-results/" + id);
		
		Map responseBody = new HashMap();
		responseBody.put("status", "success");
		responseBody.put("data", data);
		
		return new ResponseEntity(responseBody, HttpStatus.OK);
	}
	
	@RequestMapping(value = "lab-results/{id}", method = RequestMethod.GET)
	public ResponseEntity labResults(@PathVariable String id, HttpServletResponse response) throws Exception {
		
		Eform eform = (Eform) genericService.get(Eform.class, "verificationId", id);
		
		File file = new File(getLocalStorage(), eform.getId());
		InputStream inputStream = new FileInputStream(file);
		
		byte[] bytes = IOUtils.toByteArray(inputStream);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\"" + eform.getFilename() +"\"");
		
		return new ResponseEntity(bytes, headers, HttpStatus.OK);
	}
}
