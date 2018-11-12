package com.vissun.BackEnd_vissun.Bean;



import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String USER_STATE_OK = "正常";
    public static final String USER_STATE_DISABLE = "禁用";
    public static final String SESSION_KEY = "curr_user";

    @Id
    @GeneratedValue
    private Integer id;

    private String username;
    private String password;
    @NotNull
    @Length(min = 11,max = 11)
    @Column(name = "phoneNumber")
    private String phoneNumber;
    private String token;
    private String state;
    private String email;
    private String lastLoginInfo;
    private String currentLoginInfo;
    @Column(name = "headPath")
    private String headPath;
    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<Role> roleList;


    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "UserDevice", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns ={@JoinColumn(name = "deviceId") })
    private Set<Device> deviceSet;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<Device> getDeviceSet() {
        return deviceSet;
    }

    public void setDeviceSet(Set<Device> deviceSet) {
        this.deviceSet = deviceSet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentLoginInfo() {
        return currentLoginInfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCurrentLoginInfo(String currentLoginInfo) {
        this.currentLoginInfo = currentLoginInfo;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastLoginInfo() {
        return lastLoginInfo;
    }

    public void setLastLoginInfo(String lastLoginInfo) {
        this.lastLoginInfo = lastLoginInfo;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", token='" + token + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginInfo='" + lastLoginInfo + '\'' +
                ", currentLoginInfo='" + currentLoginInfo + '\'' +
                ", headPath='" + headPath + '\'' +
                ", roleList=" + roleList +
                ", deviceSet=" + deviceSet +
                '}';
    }
}
