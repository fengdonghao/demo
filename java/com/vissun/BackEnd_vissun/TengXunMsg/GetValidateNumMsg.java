package com.vissun.BackEnd_vissun.TengXunMsg;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in16:26 2018/5/16
 */
@Service
public class GetValidateNumMsg {
	// 短信应用SDK AppID
	private int appid = 1400092062; // 1400开头

	// 短信应用SDK AppKey
	private String appkey = "1b83dc0c2aac525952ef62fdc93cebb5";

	// 需要发送短信的手机号码
	private String[] phoneNumbers=new String[10];

	// 短信模板ID，需要在短信应用中申请
	private int templateId = 122281; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

	// 签名
	private String smsSign = "eastgo"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

	public String sendTest(String phoneN) {
		try{
			String verifyCode=randomNumber();
			phoneNumbers[0]=phoneN;
			String[] params = {verifyCode};
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
//			SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],
//					"【eastgo】您的验证码是: 5678", "", "");
			SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
					templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.print(result);
			return verifyCode;
		}catch (IOException e){
			e.printStackTrace();
		}catch (HTTPException e){
			e.printStackTrace();
		}catch (JSONException e){
			e.printStackTrace();
		}

		return "error";
	}

	/**
	 * 生成六位随机数
	 * @return
	 */
	private String randomNumber(){
		return String.valueOf(new Random().nextInt(899999) + 100000);
	}

}
