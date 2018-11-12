package com.vissun.BackEnd_vissun.Bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in14:15 2018/4/20
 */
@Entity
public class Device implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	private String deviceName;
	private String deviceNumber;
	private String deviceInfo;
	@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
	@JoinTable(name = "UserDevice", joinColumns = { @JoinColumn(name = "deviceId") }, inverseJoinColumns ={@JoinColumn(name = "userId") })
	private Set<User> userSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Set<User> getUserList() {
		return userSet;
	}

	public void setUserList(Set<User> userList) {
		this.userSet = userList;
	}

	@Override
	public String toString() {
		return "Device{" +
				"id=" + id +
				", deviceName='" + deviceName + '\'' +
				", deviceNumber='" + deviceNumber + '\'' +
				", deviceInfo='" + deviceInfo + '\'' +
				", userSet=" + userSet +
				'}';
	}
}
