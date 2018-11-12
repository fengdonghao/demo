package com.vissun.BackEnd_vissun.MyBlog;

import com.vissun.BackEnd_vissun.Bean.BlogUser;
import com.vissun.BackEnd_vissun.Repository.BlogUserRepo;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in10:12 2018/5/23
 */
@Controller
public class LoginController {

    @Autowired
    private BlogUserRepo blogUserRepo;

	@GetMapping("blog/login")
	public String loginG(){
		return "MyBlog/login";
	}

	@PostMapping("blog/login")
	public String loginP(BlogUser blogUSer, HttpServletRequest request, Map<String,String> map){
		String phoneNumber=blogUSer.getPhoneNumber();
		String passdword=blogUSer.getPassword();
		BlogUser user=blogUserRepo.findByPhoneNumber(phoneNumber);
        if (user==null){
            map.put("error","用户名或密码错误！");
            return "MyBlog/login";
        }
        if (!user.getPassword().equals(passdword)){
            map.put("error","用户名或密码错误！");
            return "MyBlog/login";
        }
		HttpSession session=request.getSession(true);//true表示如果此时有session，则获取session，如果没有session，则新创建一个session
		session.setAttribute("phoneNumber",blogUSer.getPhoneNumber()); //自定义session
        System.out.println(request.getSession(false).getId());
        return "redirect:/blog/index";
	}

	@GetMapping("/blog/logout")
	public String logout(HttpServletRequest request) throws Exception{
	    HttpSession session=request.getSession(false);
        if (session == null){
            return "MyBlog/Redirect";
        }
        session.setMaxInactiveInterval(1);
        return "MyBlog/Redirect";
    }

    @GetMapping("/blog/logoutRe")
    public String logoutRe(){
	    return "redirect:/blog/redirect";
    }

}
