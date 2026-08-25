package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.binding.LoginForm;
import com.example.binding.SignUpForm;
import com.example.binding.UnlockForm;
import com.example.entity.UserDtlsEntity;
import com.example.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status = userService.signUp(form);

		if (status) {
			model.addAttribute("succMsg", " Account Created, Check Your Email");
		} else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		return "signup";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
 
		String status = userService.login(loginForm);

		if (status.contains("success")) {
			
			return "redirect:/dashboard";
		}

		model.addAttribute("errMsg", status);

		return "login";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		
		
		
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email, Model model) {
		
         boolean status = userService.forgotPwd(email);
         
         if(status)
         {
        	model.addAttribute("succMsg","Pwd sent to your email"); 
         }else {
        	 model.addAttribute("errMsg","Invalid email");
         }
		
		return "forgotPwd";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);

		model.addAttribute("unlock", unlockFormObj);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userService.unlockAccount(unlock);

			if (status) {
				model.addAttribute("succMsg", "your account unlocked successfully");
			} else {
				model.addAttribute("errMsg", "Given Temporary Pwd is incorrect, check your email");
			}

		} else {
			model.addAttribute("errMsg", "New Pwd and Confirm Pwd should be same");
		}

		return "unlock";
	}
}
