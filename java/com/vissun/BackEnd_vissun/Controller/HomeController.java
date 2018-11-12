package com.vissun.BackEnd_vissun.Controller;

import com.vissun.BackEnd_vissun.Utils.RandomValidateCodeUtil;
import com.vissun.BackEnd_vissun.Utils.ShiroUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {



    @Autowired
    private Producer captchaProducer;
    public static Logger logger= LoggerFactory.getLogger(HomeController.class);

    /**
    *@ Description:登录页面
    *@ Return:
    *@ Param:
    */
    @GetMapping("/login")
    public String index(){
        return "Home/index";
    }

    /**
    *@ Description:用户登录
    *@ Return:
    *@ Param:
    */
    @PostMapping("/login")
    public String login(HttpServletRequest request, Map<String,Object> map){
        System.out.println("登录失败"+"HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        /*request.getSession().setAttribute("username",userInfo);*/

        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else if (LockedAccountException.class.getName().equals(exception)){
                msg="该账号已登录！";
                System.out.println("LockedAccountException。。。该账号已登录！");
            }else if ("randomCodeError".equals(exception)){
                msg="验证码错误！";
                System.out.println("验证码错误！");
            }else{
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }

        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理.
        return "Home/index";
    }

    /**
    *@ Description:
    *@ Return: 首页
    *@ Param:
    */
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String view(HttpServletRequest  request){
        HttpSession session=request.getSession();
        System.out.println("home-----"+(String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY));

        return "Home/home";
    }

    @GetMapping("/top")
    public String top(Map<String,Object> map){
        String username= ShiroUtil.getCurrentUserName();
        map.put("username",username);
        return "Home/top";
    }
    @GetMapping("/list")
    public String list(){
        return "Home/list";
    }
    @GetMapping("/main")
    public String main(){
        return "Home/main";
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }



    @RequestMapping(value = "/kaptcha")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);


        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");


        // create the text for the image
        String capText = captchaProducer.createText();
        System.out.println("******************验证码是: " + capText + "******************");
        System.out.println(capText);

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }

        return null;
    }



}
