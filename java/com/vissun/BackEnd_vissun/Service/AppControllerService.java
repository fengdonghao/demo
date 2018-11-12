package com.vissun.BackEnd_vissun.Service;

import com.vissun.BackEnd_vissun.Bean.Result;
import com.vissun.BackEnd_vissun.Bean.Token;
import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Repository.UserRepo;
import com.vissun.BackEnd_vissun.Utils.ResultUtil;
import com.vissun.BackEnd_vissun.Utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @ Author:fdh
 * @ Description:filter拦截之后，下一步执行的代码块。获取filter的处理结果并解析
 * @ Date：Create in10:55 2018/3/31
 */
@Service
public class AppControllerService {
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private UserRepo userRepo;
	private static Logger logger= LoggerFactory.getLogger(AppControllerService.class);
	public Result codeFilter(HttpServletResponse response){
		String code = response.getHeader("code");
		logger.info("code:" + code);
		if (code.equals("0")) {            //如果code为空，则说明对token认证成功
			return ResultUtil.success1();
		} else if (code.equals("1")) {    //code是1 表示：token解析成功，但是token即将过期，需要更换新的token
			String newToken = response.getHeader("newToken");
			//把更新的token保存到数据库的user表中。
			Integer id=tokenUtil.getCurrentUserId(newToken);
			User user1=new User();
			user1=userRepo.findOne(id);
			user1.setId(id);
			user1.setToken(newToken);
			userRepo.save(user1);
			logger.info("token已经被替换！");
			Token token = new Token();
			token.setToken(newToken);
			return ResultUtil.error(1, "success", token);
		} else if (code.equals("100")) {   //code是100 表示： token解析失败
			logger.info("token解析失败！");
			return ResultUtil.error(100, "error", null);
		} else if (code.equals("102")) {
			logger.info("您已被踢下线！");
			return ResultUtil.error(102,"error","您已被踢下线");
		} else {					//未知的错误
			return ResultUtil.error(103, "error", null);
		}
	}
}
