package com.vissun.BackEnd_vissun.Controller;

import com.vissun.BackEnd_vissun.TengXunMsg.GetValidateNumMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in16:35 2018/5/16
 */
@Controller
public class TengXunSendMsgController {
	@Autowired
	private GetValidateNumMsg getValidateNumMsg;

	@ResponseBody
	@GetMapping("tx/send")
	public String  send(){
		String phoneNumber="15927519596";
		String code= getValidateNumMsg.sendTest(phoneNumber);
		System.out.println(code);
		System.out.println();
		return "success";
	}
}
