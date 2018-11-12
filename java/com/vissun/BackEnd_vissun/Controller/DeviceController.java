package com.vissun.BackEnd_vissun.Controller;

import com.vissun.BackEnd_vissun.Bean.Device;
import com.vissun.BackEnd_vissun.Bean.Result;
import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Repository.DeviceRepo;
import com.vissun.BackEnd_vissun.Repository.UserRepo;
import com.vissun.BackEnd_vissun.Service.AppControllerService;
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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 *代码模板
 *
 Result result=appControllerService.codeFilter(response);
 Integer code=result.getCode();
 if (code==1||code==0){
		//方法块
 return ResultUtil.success1();
 }else {
 return result;
 }
 * @ Author:fdh
 * @ Description:设备管理相关的接口
 * @ Date：Create in15:58 2018/4/20
 */
@Controller
@ResponseBody
public class DeviceController {

	private static final Logger logger= LoggerFactory.getLogger(Device.class);
	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private AppControllerService appControllerService;
	@Autowired
	private DeviceRepo deviceRepo;
	@Autowired
	private UserRepo userRepo;

	/**
	 * 获取我的设备
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("app/getMyDevice")
	public Result MyDevice(HttpServletRequest request,HttpServletResponse response){
		//我的设备
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			//方法块
			String token = request.getHeader("token");
			String phoneNumber=tokenUtil.getCurrentPhoneNumber(token);
			User user=userRepo.findByPhoneNumber(phoneNumber);
			int userId=user.getId(); //可 优化猜测 生成token时，直接把userId存放到token里面，这样后续就不需要通过查询数据库来获取userId了
			//获取用户的设备列表
			Set<Device> deviceSet=user.getDeviceSet(); //若直接输出该集合，会报错
			//新建一个集合存放设备列表并返回
			List<Device> list=new ArrayList<>();
			//遍历设备
			for (Device device:deviceSet){
				device.setUserList(null);//如果没有这一句，会产生无限递归 会导致溢出！！！
				System.out.println(device);
				list.add(device);
			}
			return ResultUtil.success(list);
		}else {
			return result;
		}

	}

	/**
	 * 添加设备
	 * @param device
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("app/addDevice")
	public Result addDevice(Device device, HttpServletRequest request, HttpServletResponse response){
		//添加设备
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			String token=request.getHeader("token");
			String  phoneNumber=tokenUtil.getCurrentPhoneNumber(token); //从token中获取当前用户信息
			User currentUser=userRepo.findByPhoneNumber(phoneNumber);
			int userId=currentUser.getId(); //添加设备的参数之一
			//先获取要添加的设备号，在通过设备号查找到该设备，通过id来添加userID和deviceID的关联
			String deviceNumber = device.getDeviceNumber();
			Device device1=deviceRepo.findByDeviceNumber(deviceNumber);
			if (device1==null){
				return ResultUtil.error(113,"当前设备不存在",null);
			}
			int deviceId =device1.getId(); //添加设备的参数之二
			//通过try保证对数据库操作的正确性
			try{
				userRepo.addDevice(userId,deviceId);//只有这一句有可能存在异常
				logger.info("设备添加成功！");
				return ResultUtil.success(result.getData());
			}catch (Exception e){
				e.printStackTrace();
			}
			return ResultUtil.error(114,"对数据库的操作失败",result.getData());
		}else {
			return result;
		}
	}

	public void deleteDevice(){
		//删除设备

	}

	public void AllDevice(){
		//所有设备
	}

	public void ShareDevice(){
		//共享设备
	}
}
