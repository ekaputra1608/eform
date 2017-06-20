package com.eform.service;

import com.eform.entity.Eform;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EformService {
	
	@Autowired private GenericService genericService;
	
	@Value("${eform.url}")
	private String EFORM_URL;
	
	@Value("${eform.grant.type}")
	private String grantType;
	
	@Value("${eform.client.id}")
	private String clientId;
	
	@Value("${eform.client.secret}")
	private String clientSecret;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public String authToken(String username, String password) {
		MultiValueMap params = new LinkedMultiValueMap();
		params.add("grant_type", grantType);
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("username", username);
		params.add("password", password);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(EFORM_URL + "/oauth2/access_token",
				HttpMethod.POST, new HttpEntity(params, new HttpHeaders()), String.class);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
	        Map responseBody = mapper.readValue(responseEntity.getBody(), HashMap.class);
	        
	        return (String) responseBody.get("access_token");
		} catch (Exception e) {
			throw new RuntimeException("JsonException, message: " + e.getMessage());
		}
	}
	
	private HttpHeaders getHttpHeaders() {
		String token = authToken("yesterday@yahoo.com", "123"); // FIXME: where do we get this?
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + token);
		
		return httpHeaders;
	}
	
	private Object parse(ResponseEntity<String> responseEntity) {
		if (HttpStatus.OK == responseEntity.getStatusCode()) {
	        try {
	        	ObjectMapper mapper = new ObjectMapper();
	        	Map responseBody = mapper.readValue(responseEntity.getBody(), HashMap.class);
	        	
	        	String status = (String) responseBody.get("status");
		        if ("success".equals(status)) {
		        	return responseBody.get("data");
		        }
		        throw new RuntimeException("status: " + status + ", message: " + responseBody.get("message"));
	        } catch (Exception e) {
	        	throw new RuntimeException("JsonException, message: " + e.getMessage());
	        }
		} else {
			throw new RuntimeException("HttpStatus: " + responseEntity.getStatusCode() + ", error: " + responseEntity.getBody());
		}
	}
	
	public void submit(Eform eform) {
		Map body = new HashMap();
		body.put("doctor_name", eform.getDoctorName());
		body.put("doctor_email", eform.getDoctorEmail());
		body.put("patient_name", eform.getPatientName());
		body.put("patient_email", eform.getPatientEmail());
		body.put("patient_dob", new SimpleDateFormat("yyyy-MM-dd").format(eform.getPatientDob()));
		body.put("note", eform.getNote());
		body.put("items", Arrays.asList(eform.getItems().split(",")));
		body.put("company_code", eform.getCompanyCode());
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(EFORM_URL + "api/v1/doctor/order/input/post",
				HttpMethod.POST, new HttpEntity(body, getHttpHeaders()), String.class);
		
		String data = (String) parse(responseEntity);
		
		eform.setVerificationId(data);
		eform.setStatus(Eform.STATUS_SUBMITTED);
		
		genericService.saveOrUpdate(eform);
	}
	
	public List rontgenItems() {
		ResponseEntity<String> responseEntity = restTemplate.exchange(EFORM_URL + "api/v1/order/pemeriksaan/item/rontgen",
				HttpMethod.GET, new HttpEntity(getHttpHeaders()), String.class);
		
        List data = (List) parse(responseEntity);
        List item = (List) ((Map) data.get(0)).get("item");
        
        List result = new ArrayList();
        for (Map obj : (List<Map>) item) {
        	Object[] arr = { obj.get("master_code"), obj.get("name") };
        	result.add(arr);
        }
        return result;
	}
	
	public List generalItems() {
		ResponseEntity<String> responseEntity = restTemplate.exchange(EFORM_URL + "api/v1/order/pemeriksaan/item/general",
				HttpMethod.GET, new HttpEntity(getHttpHeaders()), String.class);
		
		List data = (List) parse(responseEntity);
        List item = (List) ((Map) data.get(0)).get("item");
        
        List result = new ArrayList();
        for (Map obj : (List<Map>) item) {
        	Object[] arr = { obj.get("master_code"), obj.get("name") };
        	result.add(arr);
        }
        return result;
	}
	
	public List labItems() {
		// FIXME: 404 for this url
		ResponseEntity<String> responseEntity = restTemplate.exchange(EFORM_URL + "api/v1/lab",
				HttpMethod.GET, new HttpEntity(getHttpHeaders()), String.class);
		
		List data = (List) parse(responseEntity);
        List item = (List) ((Map) data.get(0)).get("item");
        
        List result = new ArrayList();
        for (Map obj : (List<Map>) item) {
        	Object[] arr = { obj.get("lab_code"), obj.get("name") };
        	result.add(arr);
        }
        return result;
	}
	
	public List listCompany() {
		ResponseEntity<String> responseEntity = restTemplate.exchange(EFORM_URL + "api/v1/company/list",
				HttpMethod.GET, new HttpEntity(getHttpHeaders()), String.class);
		
		List data = (List) parse(responseEntity);
		
		List result = new ArrayList();
        for (Map obj : (List<Map>) data) {
        	Object[] arr = { obj.get("company_code"), obj.get("company_name") };
        	result.add(arr);
        }
        return result;
	}
}
