package com.vissun.BackEnd_vissun.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in17:36 2018/4/25
 */
@Configuration
public class MyWebAppConfiguration extends WebMvcConfigurerAdapter {

	@Value("${resource.path}")
	private String path;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/**
		 * @Description: 对文件的路径进行配置,创建一个虚拟路径/Path/** ，即只要在<img src="/Path/picName.jpg" />便可以直接引用图片
		 *这是图片的物理路径  "file:/+本地图片的地址"
		 * @Date： Create in 14:08 2017/12/20
		 */
		registry.addResourceHandler("/blog/**").addResourceLocations("file:"+path);
		super.addResourceHandlers(registry);
	}
}
