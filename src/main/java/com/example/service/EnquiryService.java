package com.example.service;

import java.util.List;

import com.example.binding.DashboardResponse;
import com.example.binding.EnquiryForm;
import com.example.binding.EnquirySearchCriteria;

public interface EnquiryService {
	
	public List<String> getCourses();
	
	public List<String> getEnqStatuses();
	
	public DashboardResponse getDashboardData(Integer userId);
	
	public boolean saveEnquiry(EnquiryForm from);
	
//	public String upsrtEnquiry(EnquiryForm form);
//	
//	public List<EnquiryForm> getEnquries(Integer userId, EnquirySearchCriteria criteria);
//	
//	public EnquiryForm getEnquiry(Integer enqId);

}
