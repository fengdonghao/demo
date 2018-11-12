package com.vissun.BackEnd_vissun.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in14:29 2018/5/22
 */
@Entity
public class BlogUser {
	@Id
	@GeneratedValue
	private Integer id;

	private String phoneNumber;

	private String password;

	private String username;

	private String other;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return "BlogUser{" +
				"id=" + id +
				", phoneNumber='" + phoneNumber + '\'' +
				", password='" + password + '\'' +
				", username='" + username + '\'' +
				", other='" + other + '\'' +
				'}';
	}
}
