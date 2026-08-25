package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.binding.LoginForm;
import com.example.binding.SignUpForm;
import com.example.binding.UnlockForm;
import com.example.entity.UserDtlsEntity;
import com.example.repo.UserDtlsRepo;
import com.example.util.EmailUtils;
import com.example.util.PwdUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm form) {
		
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(),form.getPwd());
		
		if(entity == null) {
			return "Invalid Credentials";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "YOur Account Locked";
		}
		
		//create Sesion and store user data in session
		session.setAttribute("userId", entity.getUserId());
		
		
		return "success";
	}

	@Override
	public boolean signUp(SignUpForm form) {
		
		//It will find unique email 
		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		if(user!=null)
		{
			return false;
		}
		
		// Copy data from binding obj to entity obj
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		//generate random pwd and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		
		//Set account status as LOCKED
		entity.setAccStatus("LOCKED");
		
		//insert record
		userDtlsRepo.save(entity);
		
		//send email to unlock the account
		String to = form.getEmail();
		String subject = "Unlock Your Account | Deepak IT";
		StringBuffer body = new StringBuffer();
		body.append("<h1>Use below temporary password to unlock your account</h1>");
		body.append("Temporary pwd: " + tempPwd);
		body.append("<br/>");
		body.append("<a href =\"http://localhost:8080/unlock?email="+to+"\">Click Here To Unlock Your Account</a>");
		
		emailUtils.sendEmail(to, subject, body.toString()); 
		
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		
		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
		
		if(entity.getPwd().equals(form.getTempPwd()))
		{
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("Unlocked");
			userDtlsRepo.save(entity);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean forgotPwd(String email) {
		
		//check record present in db with given email
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);
		
		//If record is not available return false
		if(entity == null) {
			return false;
		}
		
		//if record is available send pwd to email and return true
		String subject = "Recover Password";
		String body = "Your Pwd :: "+ entity.getPwd();
		
		emailUtils.sendEmail(email, subject, body);
		
		
		return true;
	}

}
