package com.vissun.BackEnd_vissun.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in16:28 2018/4/3
 */
@Service
public class DefaultKaptchaConfig {
	@Bean
	public DefaultKaptcha captchaProducer(){
		DefaultKaptcha captchaProducer =new DefaultKaptcha();
		Properties properties =new Properties();
		properties.setProperty("kaptcha.border","yes");
		properties.setProperty("kaptcha.border.color","105,179,90");
		properties.setProperty("kaptcha.textproducer.font.color","blue");
		properties.setProperty("kaptcha.image.width","125");
		properties.setProperty("kaptcha.image.height","45");
		properties.setProperty("kaptcha.textproducer.font.size","45");
		properties.setProperty("kaptcha.session.key","code");
		properties.setProperty("kaptcha.textproducer.char.length","4");
		properties.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
		Config config=new Config(properties);
		captchaProducer.setConfig(config);
		return  captchaProducer;
	}
}
