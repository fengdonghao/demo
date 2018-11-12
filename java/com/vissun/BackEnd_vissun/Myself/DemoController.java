package com.vissun.BackEnd_vissun.Myself;

import com.vissun.BackEnd_vissun.Bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in17:54 2018/4/25
 */
@Controller
public class DemoController {

	@GetMapping("demo")
	public String  demo(){
		return "/index";
	}

	@PostMapping("demoLogin")
	public String demoLogin(User user){
		String username=user.getUsername();
		String password=user.getPassword();
		if (username.equals(password)){
			return "Myself/index";
		}
		return "redirect:Myself/Demo";
	}
}
