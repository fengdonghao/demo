package com.vissun.BackEnd_vissun.Controller;

import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Repository.UserRepo;
import com.vissun.BackEnd_vissun.Socket.ServerSocketS;
import com.vissun.BackEnd_vissun.Socket.ServerSocketThread;
import com.vissun.BackEnd_vissun.Utils.JavaWebTokenUtil;
import com.vissun.BackEnd_vissun.Utils.ResultUtil;

import com.vissun.BackEnd_vissun.Utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in15:54 2018/3/29
 */
@Controller
public class AppHomeController {

	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private ServerSocketS serverSocketS;
	@Autowired
	private UserRepo userRepo;
	private static Logger logger= LoggerFactory.getLogger(AppController.class);

	@ResponseBody
	@PostMapping("appLogin")
	public Object login(User user){
		String temPhoneNumber=user.getPhoneNumber();
		String temPassword=user.getPassword();
		logger.info("登录名：tmpUsername: "+temPhoneNumber);
		logger.info("登录密码：temPassword: "+temPassword);
		User user1=userRepo.findByPhoneNumber(temPhoneNumber);
		if (user1==null){
			logger.info("数据库中没有该用户的信息！");
			return ResultUtil.error(105,"error",null); //表示用户不存在
		}
		String lastLoginInfo=user1.getCurrentLoginInfo();
		Integer id=user1.getId();
		if (temPassword.equals(user1.getPassword())){//密码校验
			logger.info("密码 验证 success");
			//密码校验成功后，需判断该用户是不是 同一账户二次登录
			if (user1.getToken()!=null&&!user1.getToken().isEmpty()){
					String token=user1.getToken();
					String phoneNumber=tokenUtil.getCurrentPhoneNumber(token);
//					ServerSocketThread.sendToClient(phoneNumber,"you out!!!");
			}
			String token = JavaWebTokenUtil.createJWT(id.toString(),"vissun",temPhoneNumber,1000*60*60*24*2+60000);//1000=1s
			logger.info(token);
			/*
			如果一个账号被两个人先后登录，后登陆的挤掉前面登录的
			当用户登录发现已经有token了，便说明该账号已经有人登录了
			此时用后面用户的token替换前面用户的token。
			 */
			//update用户表：增加token到user表中
			user1.setId(id);
			user1.setToken(token);
			Date nowTime= new Date(System.currentTimeMillis());
			//创建两个参数：当前登录信息和上次登录信息，每次登录时，将上次登录信息的内容替换为当前登录信息，再刷新当前登录信息的内容
			user1.setCurrentLoginInfo(nowTime.toString());
			user1.setLastLoginInfo(lastLoginInfo);
			userRepo.save(user1);
			logger.info("token已保存到user表！");
			//返回APP一个user对象，返回的结果以对象的结构返回，返回token 登录账号 及最近一次的登录信息
			User user2=new User();
			user2.setToken(token);
			user2.setPhoneNumber(temPhoneNumber);
			user2.setLastLoginInfo(user1.getLastLoginInfo());
			logger.info("登录成功！");
			return ResultUtil.success(user2);
		}else{
			logger.info("密码 验证 failed!");
			return ResultUtil.error(101,"error",null);//密码错误
		}
	}

	@ResponseBody
	@PostMapping("appLogout")
	public String logout(String phoneNumber){
		logger.info("用户退出");
		User user=userRepo.findByPhoneNumber(phoneNumber);
		System.out.println(user);
		//删除用户信息中token，下次请求时发现没有token便会提示用户重新登录
		user.setToken(null);
		userRepo.save(user);
		return "success";
	}

	@GetMapping("socketServerStart")
	public void  socketServerStart(){
		serverSocketS.serverSocket();
	}

	@ResponseBody
	@GetMapping("thread")
	public void currentThread(){
		System.out.println(Thread.currentThread());
		System.out.println(Thread.activeCount());
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while (group.getParent() != null) {
			group = group.getParent();
		}
		Thread[] threads = new Thread[group.activeCount()];
		group.enumerate(threads);
		for (Thread thread : threads) {
			if (thread == null) {
				continue;
			}
			try {
				StringBuffer buf = new StringBuffer();
				ThreadGroup tgroup = thread.getThreadGroup();
				String groupName = tgroup == null ? "null" : tgroup.getName();
				buf.append("ThreadGroup:").append(groupName).append(", ");
				buf.append("Id:").append(thread.getId()).append(", ");
				buf.append("Name:").append(thread.getName()).append(", ");
				buf.append("isDaemon:").append(thread.isDaemon()).append(", ");
				buf.append("isAlive:").append(thread.isAlive()).append(", ");
				buf.append("Priority:").append(thread.getPriority());
				System.out.println(buf.toString());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
