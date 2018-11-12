package com.vissun.BackEnd_vissun.Configuration;



import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.*;


@Configuration
public class shiroConfiguration {
    private static final Logger logger= LoggerFactory.getLogger(shiroConfiguration.class);
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        logger.info("开始执行：ShiroConfiguration.shiroFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);



        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        /*filterChainDefinitionMap.put("/logout", "logout");*/

        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/app/userInfoAdd", "anon");


        filterChainDefinitionMap.put("/login","authc");
        filterChainDefinitionMap.put("/home", "authc");
        filterChainDefinitionMap.put("/top", "authc");
        filterChainDefinitionMap.put("/list", "authc");

//       filterChainDefinitionMap.put("/**", "authc");
//       filterChainDefinitionMap.put("/userList", "roles[admin]");
        //记住我的过滤器
//       filterChainDefinitionMap.put("/login","user");




        // 如果不设置默认会自动寻找Web工程根目录下的"/login.js"页面
        //未登录时跳转的界面
        shiroFilterFactoryBean.setLoginUrl("/login" );
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/home");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    //**
    // * 身份认证realm;
    // * (这个需要自己写，账号密码校验；权限等)
    // * @return
    // */
    @Bean
    public ShiroRealm myShiroRealm(){
        ShiroRealm myShiroRealm = new ShiroRealm();
        //密码加密
        /*myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());*/
        return myShiroRealm;
    }
    /**
     * @ Description: 核心！
     * @ Data:11:17 2017/12/11
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //注入realm.
        securityManager.setRealm(myShiroRealm());

        return securityManager;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
