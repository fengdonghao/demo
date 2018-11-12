package com.vissun.BackEnd_vissun.Controller;

import com.vissun.BackEnd_vissun.Bean.Result;
import com.vissun.BackEnd_vissun.Bean.Role;
import com.vissun.BackEnd_vissun.Bean.Token;
import com.vissun.BackEnd_vissun.Bean.User;

import com.vissun.BackEnd_vissun.Repository.UserRepo;
import com.vissun.BackEnd_vissun.Service.AppControllerService;
import com.vissun.BackEnd_vissun.Service.SaveHeadPic;
import com.vissun.BackEnd_vissun.Utils.ResultUtil;
import com.vissun.BackEnd_vissun.Utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**代码模板
 *
 *
 	Result result=appControllerService.codeFilter(response);
 	Integer code=result.getCode();
 	if (code==1||code==0){

 	return ResultUtil.success1();
 	}else {
 	return result;
 	}
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in11:17 2018/3/21
 */
@ResponseBody
@Controller
public class AppController {
	private static Logger logger= LoggerFactory.getLogger(AppController.class);
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AppControllerService appControllerService;

	Semaphore semaphore=new Semaphore(1); //并发总资源数

	@Autowired
	private SaveHeadPic saveHeadPic;

	/**
	 * 过滤该mapping  刷新token的有效期
	 * @param response
	 * @return
	 */
    @ResponseBody
    @GetMapping("app/refresh")
    public Object refresh( HttpServletResponse response) {
		Result result=appControllerService.codeFilter(response);
		logger.info(" "+result.getCode());
		logger.info(" "+result.getMsg());
		logger.info(" "+result.getData());
		return result;
	}

	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@ResponseBody
	@PostMapping("/userAdd")
	public Object AppUserAdd(User user) throws InterruptedException{

			String phoneNumber=user.getPhoneNumber();
			User userInfoTmp=userRepo.findByPhoneNumber(phoneNumber);  //在数据库中查找当前要添加的用户，如果存在。。。
			if (userInfoTmp!=null){
				logger.info("phoneNumber already exist！");
				return ResultUtil.error(104,"error","phoneNunber already exist！");
			}
			//密码校验的正则表达式：不能全部为数字 不能全部为字母 的8-16位密码
			String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
			String password=user.getPassword();
			if (password.matches(regex)){			//校验密码的格式
				user.setPhoneNumber(user.getPhoneNumber());
				user.setPassword(user.getPassword());
			}else{
				return ResultUtil.error(109,"error","密码不符合安全规范");
			}
			//线程安全 信号量
			int availablePermits=semaphore.availablePermits();
			if(availablePermits>0){
				System.out.println("：抢到资源");
			}else{
				System.out.println("资源已被占用，稍后再试");
				return ResultUtil.error(108,"resource is busy now!",null);
			}

			try{
				semaphore.acquire(1);
				userRepo.save(user);
			}finally{
				semaphore.release(1);
			}
			User userInfo1=new User();
			userInfo1=userRepo.findByPhoneNumber(phoneNumber);
			//为用户赋予 普通员工 的角色
			userRepo.addPermission(userInfo1.getId(),2);
			logger.info("被添加的 userNub is:"+userInfo1.getId()+"; phoneNumber is:"+phoneNumber);
			return ResultUtil.success(phoneNumber);

	}

	/**
	 * 修改用户头像
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("app/editHead")
	public Object editHead(HttpServletRequest request,HttpServletResponse response,@RequestParam("file") MultipartFile file) throws IOException {
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			String token=request.getHeader("token");
			User user=tokenUtil.getCurrentUser(token);
			String username=user.getUsername();
			String phoneNumber=user.getPhoneNumber();
			saveHeadPic.saveHeadPic(file,username);
			String headPicPath="C:\\Users\\Administrator\\IdeaProjects\\FDH_CRM\\src\\main\\resources\\static\\"+username+".jpg";
			userRepo.updateHeadByPhoneNumber(headPicPath,phoneNumber);
			logger.info("保存用户头像success");
			return result;
		}else{
			return result;
		}
	}

	/**
	 * 修改用户资料
	 * @param user
	 * @param response
	 * @param request
	 * @return
	 */
	@PostMapping("app/editUser")
	public Result editUser(User user,HttpServletResponse response,HttpServletRequest request){
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			String token=request.getHeader("token");
			Integer id=tokenUtil.getCurrentUserId(token);
			User user1=userRepo.getOne(id);
			user1.setId(id);
			if (user.getUsername()!=null&&!user.getUsername().isEmpty()){
				user1.setUsername(user.getUsername());
			}
			if (user.getEmail()!=null&&!user.getEmail().isEmpty()){
				String regex="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
				if (user.getEmail().matches(regex)){
					user1.setEmail(user.getEmail());
				}else {
					//邮箱格式验证失败，返回错误信息,112表示此时需要更新token，111表示不需要更新
					if (result.getData()!=null){
						return ResultUtil.error(112,"error",result.getData());
					}else {
						return ResultUtil.error(111,"error",result.getData());
					}

				}

			}
			userRepo.save(user1);
			logger.info("修改用户资料success");
			return result;
		}else{
			return result;
		}
	}

	/**
	 * 修改密码
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("app/editPassword")
	public Result editPassword(User user,HttpServletRequest request,HttpServletResponse response){
		//从response中获取用户状态
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			//正则表达式 匹配8到16位的不全是数字，不全是字母的数字字母混合的字符串，^开头  $结尾
			String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
			String password=user.getPassword();
			if (password.matches(regex)){
				String token=request.getHeader("token");
				Integer id=tokenUtil.getCurrentUserId(token);
				User user1=userRepo.getOne(id);
				user1.setId(id);
				user1.setPassword(password);
				userRepo.save(user1);
				logger.info("用户 "+id+" 密码修改success");
				return result;
			}else{//密码不符合规范
				System.out.println(result.getCode());
				System.out.println(result.getMsg());
				System.out.println(result.getData());
				return ResultUtil.error(110,"密码修改失败！",result.getData());
			}

		}else{
			return result;
		}

	}

	/**
	 * 修改用户状态
	 *
	 * @param state
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("app/editState")
	public Result editState(String state,HttpServletRequest request,HttpServletResponse response){
		//从response中获取用户状态
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			String token=request.getHeader("token");
			String phoneNumber=tokenUtil.getCurrentPhoneNumber(token);
			User user=new User();
			user=userRepo.findByPhoneNumber(phoneNumber);
			user.setState(state);
			userRepo.save(user);
			logger.info("用户状态修改成功");
			return ResultUtil.success(state);
		}else {
			return result;
		}
	}

	/**
	 * 通讯录返回用户的电话号码,用户名，邮箱（遗留问题，当token快要过期时，会生成并返回新的token，此时不仅要返回用户信息，还有返回新的token，未解决）
	 * @param response
	 * @return
	 */
	@GetMapping("app/userList")
	public Result userList(HttpServletResponse response){
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==0||code==1){
			List<User> list=new ArrayList<>();
			List<User> list1=new ArrayList<>();
			list=userRepo.findAll();
			for (User user:list){
				User user1=new User();
				user1.setPhoneNumber(user.getPhoneNumber());
				user1.setEmail(user.getEmail());
				user1.setUsername(user.getUsername());
				list1.add(user1);
			}
			return ResultUtil.success(list1);
		}else {
			return result;
		}
	}



	@GetMapping("app/getRoleList")
	public Result getRoleList(HttpServletRequest request,HttpServletResponse response){
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){

			String token=request.getHeader("token");
			String phoneNumber=tokenUtil.getCurrentPhoneNumber(token);
			User user=userRepo.findByPhoneNumber(phoneNumber);
			for (Role list:user.getRoleList()){
				System.out.println(list);
			}
			return ResultUtil.success1();
		}else {
			return result;
		}
	}
}
