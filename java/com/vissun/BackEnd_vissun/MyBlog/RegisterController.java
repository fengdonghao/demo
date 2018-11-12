package com.vissun.BackEnd_vissun.MyBlog;

import com.vissun.BackEnd_vissun.Bean.BlogUser;
import com.vissun.BackEnd_vissun.Repository.BlogUserRepo;
import com.vissun.BackEnd_vissun.TengXunMsg.GetValidateNumMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;


/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in15:46 2018/5/18
 */
@Controller
public class RegisterController {

	@Autowired
	private BlogUserRepo blogUserRepo;
	@Autowired
	private GetValidateNumMsg getValidateNumMsg;

	/**
	 * 返回注册页面
	 * @return
	 */
	@GetMapping("blog/register")
	public String registerG(){
		return "MyBlog/register";
	}

	/**
	 * 用户注册
	 * @param phoneNumber
	 * @param password
	 * @return
	 */
	@PostMapping("blog/register1")
	public String registerP(@RequestParam("phoneNumber")String phoneNumber,@RequestParam("password")String password){
		BlogUser user=new BlogUser();
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		blogUserRepo.save(user);
		return "MyBlog/registerSuccess";
	}

	/**
	 * 给注册的用户发送短信验证码(先校验数据库用户资料是否已经存在该手机号码)
     * ajax异步传递参数
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/blog/sendValidateMsg")
	public String  sendValidateMsg(HttpServletRequest req){
		//ajax传递过来的参数 的读取
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = req.getReader();
			char[]buff = new char[1024];
			int len;
			while((len = reader.read(buff)) != -1) {
				sb.append(buff,0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str=sb.toString();
/*		JSONObject jsonObject=new JSONObject(str);
		String  phoneNumber=jsonObject.getString("phoneNumber");
		System.out.println(phoneNumber);*/
		BlogUser user=blogUserRepo.findByPhoneNumber(str);
		if (user!=null){//如果输入的手机号已经存在 则返回错误信息 注册失败
			return "error";
		}
		String validateCode=getValidateNumMsg.sendTest(str);
		System.out.println(validateCode);
		return validateCode;

	}
}
