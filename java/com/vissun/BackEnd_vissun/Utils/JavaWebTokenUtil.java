package com.vissun.BackEnd_vissun.Utils;


import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Repository.UserRepo;
import com.vissun.BackEnd_vissun.Socket.ServerSocketThread;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;



/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in10:53 2018/3/21
 */
@Component
public class JavaWebTokenUtil {

    private static Logger logger = LoggerFactory.getLogger(JavaWebTokenUtil.class);
	@Autowired
    private UserRepo userRepo;

	private static JavaWebTokenUtil  serverHandler ;

	@PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
	public void init() {
		serverHandler = this;
		serverHandler.userRepo = this.userRepo;
		// 初使化时将已静态化的testService实例化
	}


	public  static String createJWT(String id, String issuer, String subject, long ttlMillis) {

		//签名的算法
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//当前的时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//签名算法的秘钥，解析token时的秘钥需要和此时的一样
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("miyao");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id)
				.setIssuedAt(now)
				.setSubject(subject)
				.setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);

		logger.info("---token生成---");
		//给token设置过期时间
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			logger.info("过期时间："+exp);
			builder.setExpiration(exp);
		}

//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}
	public  static  Object parseJWT(String jwt) {
//This line will throw an exception if it is not a signed JWS (as expected)
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("miyao")).parseClaimsJws(jwt).getBody();
			//用户id
			String id=claims.getId();
			//用户账号
			String phoneNumber=claims.getSubject();
			/*
			判断是否有账号同时多次登录
			 */
			User user=new User();
			user=serverHandler.userRepo.findByPhoneNumber(phoneNumber);
			if (user.getToken().equals(jwt)&&user.getToken()!=null){
				logger.info("------解析token----");
				logger.info("ID: " + id);
				logger.info("Subject: " + phoneNumber);
				logger.info("Issuer: " + claims.getIssuer());
				logger.info("IssuerAt:   " + claims.getIssuedAt());
				logger.info("Expiration: " + claims.getExpiration());
				/*
				检验token是或否过期
			 	*/
				Long exp=claims.getExpiration().getTime(); //过期的时间
				long nowMillis = System.currentTimeMillis();//现在的时间
				Date now=new Date(nowMillis);
				logger.info("currenTime:"+now);
				long seconds=exp-nowMillis;//剩余的时间 ，若剩余的时间小与48小时，就返回一个新的token给APP
				long days=seconds/(1000*60*60*24);
				long hour=(seconds-days*1000*60*60*24)/3600000;
				long minutes = (seconds-days*1000*60*60*24-hour*3600000) / 60000;
				long remainingSeconds = seconds % 60;
				logger.info(seconds + " seconds is "+days+" days "+hour+" hours " + minutes + " minutes and "+ remainingSeconds + " seconds");
				if (seconds<=1000*60*60*48){
					logger.info("token的有效期小与48小时，请更新token！");
					return phoneNumber;
				}
				return "success";
			}else {
				return "error";
			}

		}catch (ExpiredJwtException e){
			e.printStackTrace();
			return ExpiredJwtException.class.getName();
		}catch (SignatureException e1){
			e1.printStackTrace();
			return  SignatureException.class.getName();
		}catch (MalformedJwtException e2){
			e2.printStackTrace();
			return MalformedJwtException.class.getName();
		}
	}

	/**
	 *
	 * @param jwt
	 * @return
	 */
	public  static  Object parsePhoneNumber(String jwt) {
//This line will throw an exception if it is not a signed JWS (as expected)
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("miyao")).parseClaimsJws(jwt).getBody();
			String PhoneNumber=claims.getSubject();
			return PhoneNumber;

		}catch (ExpiredJwtException e){
			e.printStackTrace();
			return ExpiredJwtException.class.getName();
		}catch (SignatureException e1){
			e1.printStackTrace();
			return  SignatureException.class.getName();
		}catch (MalformedJwtException e2){
			e2.printStackTrace();
			return MalformedJwtException.class.getName();
		}


	}
}
