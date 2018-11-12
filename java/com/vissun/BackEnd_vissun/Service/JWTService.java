package com.vissun.BackEnd_vissun.Service;

import com.vissun.BackEnd_vissun.Utils.JavaWebTokenUtil;
import org.springframework.stereotype.Service;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in9:39 2018/3/28
 */
@Service
public class JWTService {

	public void expTime(Long seconds){

		long days=seconds/(1000*60*60*24);
		long hour=(seconds-days*1000*60*60*24)/3600000;
		long minutes = (seconds-days*1000*60*60*24-hour*3600000) / 60000;
		long remainingSeconds = seconds % 60;


		System.out.println(seconds + " seconds is "+days+" days "+hour+" hours " + minutes +
				" minutes and "+ remainingSeconds + " seconds");

	}
	public String updateToken(String token){


		String newToken1=JavaWebTokenUtil.createJWT("1","vissun",token,1000*60*60*24*2+60000);//2天+60秒
		return newToken1;
	}
}
