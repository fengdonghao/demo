package com.vissun.BackEnd_vissun.Controller;

import com.vissun.BackEnd_vissun.Bean.ApprovalBean;
import com.vissun.BackEnd_vissun.Bean.Result;
import com.vissun.BackEnd_vissun.Repository.ApprovalBeanRepo;
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

/**
 Result result=appControllerService.codeFilter(response);
 Integer code=result.getCode();
 if (code==1||code==0){

 return ResultUtil.success1();
 }else {
 return result;
 }
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in9:19 2018/4/28
 */
@ResponseBody
@Controller
public class AppWorkController {

	private final  static Logger logger= LoggerFactory.getLogger(AppWorkController.class);
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private ApprovalBeanRepo approvalBeanRepo;

	@Autowired
	private AppControllerService appControllerService;

	/**
	 * 新建审批
	 * @param approvalBean
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("app/createApproval")
	public Result createApproval(ApprovalBean approvalBean, HttpServletRequest request, HttpServletResponse response){
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			String token=request.getHeader("token");
			String currentUser=tokenUtil.getCurrentPhoneNumber(token);
			ApprovalBean approvalBean1=new ApprovalBean();
			approvalBean1.setApproval(approvalBean.getApproval());
			approvalBean1.setCarbonCopy(approvalBean.getCarbonCopy());
			approvalBean1.setCreator(currentUser);
			approvalBean1.setDetail(approvalBean.getDetail());
			approvalBean1.setInfo("等待 "+approvalBean.getApproval()+" 审批");
			approvalBean1.setTotal(approvalBean.getTotal());
			approvalBeanRepo.save(approvalBean1);
			logger.info(currentUser+": 创建审批success");
			return ResultUtil.success(result.getData());
		}else {
			return result;
		}

	}

	//我发起的审批
	@GetMapping("app/getMyWork")
	public Result myWork(HttpServletResponse response,HttpServletRequest request){
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			String token=request.getHeader("token");
			String currentUser=tokenUtil.getCurrentPhoneNumber(token);

			List<ApprovalBean> list1=new ArrayList<>();
			list1=approvalBeanRepo.findByCreator(currentUser);
			logger.info("获取我由我发起的审批的列表success---");
			return ResultUtil.success(list1);
		}else {
			return result;
		}

	}

	//我审批的
	public String myApproval(){



		return "";
	}

	//抄送我的
	@GetMapping("app/getMyCc")
	public Result myCc(HttpServletRequest request,HttpServletResponse response){
		Result result=appControllerService.codeFilter(response);
		Integer code=result.getCode();
		if (code==1||code==0){
			String token=request.getHeader("token");
			String currentUser=tokenUtil.getCurrentPhoneNumber(token);

			List<ApprovalBean> list= new ArrayList<>();
			list=approvalBeanRepo.findByCarbonCopy(currentUser);
			logger.info("获取抄送我的审批的列表success---");
			return ResultUtil.success(list);
		}else {
			return result;
		}
	}

	//审批



}
