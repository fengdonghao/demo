package com.vissun.BackEnd_vissun.Configuration;

import com.vissun.BackEnd_vissun.Bean.Role;
import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Repository.UserRepo;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;

public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    private UserRepo userRepo;
    /**
    *@ Description:权限认证
    *@ Return:
    *@ Param:
    */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("分配权限");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();

            //获取用户对应的角色列表

            List<Role> list=user.getRoleList();

            for(Role role : list) {
//                role.setUserList(null);
                info.addRole(role.getRoleName());
                System.out.println(role);
            }
            return info;

    }

    /**
    *@ Description:登录方法
    *@ Return:
    *@ Param:
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("开始身份认证");
        String username = (String)token.getPrincipal();
        System.out.println("------shiro---"+username);
        User user = userRepo.findByUsername(username);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getPassword(),
                getName()  //realm name
        );

        return authenticationInfo;
    }

}
