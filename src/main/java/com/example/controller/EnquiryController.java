package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.binding.DashboardResponse;
import com.example.binding.EnquiryForm;
import com.example.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService enqService;
	
	@GetMapping("/logout")
	public String logout()
	{
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dasdboardPage(Model model) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		
		model.addAttribute("dashboardData",dashboardData);
		
		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String enquiryPage(Model model) { //Model - to send the data
		
		//get courses for drop down
		List<String> courses = enqService.getCourses();
		
		//get enq status for drop down
		List<String> enqStatuses = enqService.getEnqStatuses();
		
		//create  binding class obj
		EnquiryForm formObj = new EnquiryForm();
		
		//set data in model obj
		model.addAttribute("courseNames",courses);
		model.addAttribute("statusNames",enqStatuses);
		model.addAttribute("formObj",formObj);
		
		return "add-enquiry";
	}
	
	@GetMapping("/enquires")
	public String viewenquiryPage() {
		return "view-enquiries";
	}
	
	@PostMapping("/addEnq")
	 public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj,Model model)
	{	
		boolean status = enqService.saveEnquiry(formObj);
		
		if(status) {
			model.addAttribute("succMsg","Enquiry Added");
		}else {
			model.addAttribute("errMsg","Problem Occured");
		}
		
		return "add-enquiry";
	}

}
