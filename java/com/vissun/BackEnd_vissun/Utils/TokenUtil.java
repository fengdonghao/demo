package com.vissun.BackEnd_vissun.Utils;

import com.vissun.BackEnd_vissun.Bean.Role;
import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author:fdh
 * @ Description: 用户权限管理  还有一种较为简单的方法是：在生成token的时候，把用户的role也保存到token里面，直接解析token即可
 * @ Date：Create in13:53 2018/3/28
 */
@Service
public class TokenUtil {

	private static final String _Manager="项目经理";
	private static final String _Admin="管理员";
	private static final String _Staff="普通员工";
	private static Logger logger= LoggerFactory.getLogger(TokenUtil.class);
	@Autowired
	private UserRepo userRepo;

	/**
	 * 获取当前用户的手机号 ！
	 * @param token
	 * @return
	 */
	public  String getCurrentPhoneNumber(String token){
		String phoneNumber=(String)JavaWebTokenUtil.parsePhoneNumber(token);
		return phoneNumber;
	}

	/**
	 * 获取当前用户的ID
	 * @param token
	 * @return
	 */
	public  Integer getCurrentUserId(String token){
		String phoneNumber=getCurrentPhoneNumber(token);
		Integer id=userRepo.findByPhoneNumber(phoneNumber).getId();
		return id;
	}

	/**
	 * 获取当前用户
	 * @param token
	 * @return
	 */
	public User getCurrentUser(String token){
		String phoneNumber=getCurrentPhoneNumber(token);
		return userRepo.findByPhoneNumber(phoneNumber);
	}

	/**
	 * 判断当前用户是否为管理员
	 * @param token
	 * @return
	 */
	public Boolean isAdmin(String token){
		String roleName="";
		User user=getCurrentUser(token);
		if (user!=null){
			for(Role role:user.getRoleList()){
				roleName=role.getRoleName();
				logger.info(roleName);
			}
			return roleName.equals(_Admin);
		}
		return false;
	}

	/**
	 * 判断当前用户是否是项目经理
	 * @param token
	 * @return
	 */
	public Boolean isManager(String token){
		String roleName="";
		User user=getCurrentUser(token);
		for(Role role:user.getRoleList()){
			roleName=role.getRoleName();
			logger.info(roleName);
		}
		return roleName.equals(_Manager);
	}

	/**
	 * 判断当前用户是否为 普通员工
	 * @param token
	 * @return
	 */
	public Boolean isEmployee(String token){
		String roleName="";
		User user=getCurrentUser(token);
		for(Role role:user.getRoleList()){
			roleName=role.getRoleName();
			logger.info(roleName);
		}
		return roleName.equals(_Staff);
	}
}
