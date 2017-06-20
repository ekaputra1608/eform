package com.eform.controller;

import com.eform.entity.Eform;
import com.eform.util.DataTables;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController extends BaseController {
	
	private void putIntoRequest(Model model) {
		model.addAttribute("rontgenItems", eformService.rontgenItems());
		model.addAttribute("generalItems", eformService.generalItems());
		model.addAttribute("listCompany", eformService.listCompany());
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public DataTables search(DataTables dataTables) {
		return genericService.search(Eform.class, dataTables);
	}
	
	@RequestMapping("/create")
	public String create(Model model) {
		putIntoRequest(model);
		
		return "edit";
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		model.addAttribute("eform", genericService.get(Eform.class, id));
		
		return "view";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		Eform eform = (Eform) genericService.get(Eform.class, id);
		
		model.addAttribute("eform", eform);
		
		if (Eform.STATUS_SUBMITTED.equals(eform.getStatus())) {
			return "view";
		}
		
		putIntoRequest(model);
		
		return "edit";
	}
	
	@RequestMapping("/save")
	public String save(Eform eform, 
			@RequestParam String[] rontgenItems, @RequestParam String[] generalItems) {
		
		if (Eform.STATUS_CREATED.equals(eform.getStatus())) {
			String[] items = (String[]) ArrayUtils.addAll(rontgenItems, generalItems);
			
			eform.setItems(StringUtils.join(items, ","));
			
			genericService.saveOrUpdate(eform);
		}
		
		return "redirect:view/" + eform.getId();
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable String id) {
		Eform eform = (Eform) genericService.get(Eform.class, id);
		
		if (!Eform.STATUS_SUBMITTED.equals(eform.getStatus())) {
			genericService.delete(eform);
		}
		
		return "redirect:../";
	}
	
	@RequestMapping("/submit/{id}")
	public String submit(@PathVariable String id) {
		Eform eform = (Eform) genericService.get(Eform.class, id);
		
		if (Eform.STATUS_CREATED.equals(eform.getStatus())) {
			eformService.submit(eform);
		}
		
		return "redirect:../view/" + eform.getId();
	}
}
