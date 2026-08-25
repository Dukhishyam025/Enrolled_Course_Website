package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.binding.DashboardResponse;
import com.example.binding.EnquiryForm;
import com.example.entity.CourseEntity;
import com.example.entity.EnqStatusEntity;
import com.example.entity.StudentEnqEntity;
import com.example.entity.UserDtlsEntity;
import com.example.repo.CourseRepo;
import com.example.repo.EnqStatusRepo;
import com.example.repo.StudentEnqRepo;
import com.example.repo.UserDtlsRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CourseRepo coursesRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public List<String> getCourses() {
		
		List<CourseEntity> findAll = coursesRepo.findAll();
		
		List<String> names = new ArrayList<>();
		
		for(CourseEntity entity : findAll) {
			
			names.add(entity.getCourseName());
		}
		
		return names;
	}

	@Override
	public List<String> getEnqStatuses() {
		
		List<EnqStatusEntity> findAll = statusRepo.findAll();
		
		List<String> statusList = new ArrayList<>();
		
		for(EnqStatusEntity entity : findAll)
		{
			statusList.add(entity.getStatusName());
		}
		
		return statusList;
	}
	@Override
	public DashboardResponse getDashboardData(Integer userId) {

	    DashboardResponse response = new DashboardResponse();

	    Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

	    if (findById.isPresent()) {

	        UserDtlsEntity userEntity = findById.get();

	        List<StudentEnqEntity> enquiries = userEntity.getEnquiries();

	        Integer totalCnt = enquiries.size();

	        Integer enrolledCnt = (int) enquiries.stream()
	                .filter(e -> e.getEnqStatus().equals("Enrolled"))
	                .collect(Collectors.toList())
	                .size();

	        Integer lostCnt = (int) enquiries.stream()
	                .filter(e -> e.getEnqStatus().equals("Lost"))
	                .collect(Collectors.toList())
	                .size();

	        response.setTotalEnquriesCnt(totalCnt);
	        response.setEnrolledCnt(enrolledCnt);
	        response.setLostCnt(lostCnt);
	    }
	    
	    System.out.println(response);

	    return response;
	}

//	@Override
//	public String upsrtEnquiry(EnquiryForm form) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<EnquiryForm> getEnquries(Integer userId, EnquirySearchCriteria criteria) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public EnquiryForm getEnquiry(Integer enqId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {

	    StudentEnqEntity enqEntity = new StudentEnqEntity();

	    BeanUtils.copyProperties(form, enqEntity);

	    Integer userId = (Integer) session.getAttribute("userId");

	    UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();

	    enqEntity.setUser(userEntity);

	    enqRepo.save(enqEntity);

	    return true;
	}
	

}
