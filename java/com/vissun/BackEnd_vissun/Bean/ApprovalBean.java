package com.vissun.BackEnd_vissun.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in9:25 2018/4/28
 */
@Entity
public class ApprovalBean {


	@Id
	@GeneratedValue
	private Integer id;

	//创建人
	private String creator;
	//审批名
	private String total;

	//审批详情
	private String detail;

	//审批人
	private String approval;

	//抄送人
	private String carbonCopy;

	//审批进展
	private String info;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getCarbonCopy() {
		return carbonCopy;
	}

	public void setCarbonCopy(String carbonCopy) {
		this.carbonCopy = carbonCopy;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ApprovalBean{" +
				"id=" + id +
				", total='" + total + '\'' +
				", detail='" + detail + '\'' +
				", approval='" + approval + '\'' +
				", carbonCopy='" + carbonCopy + '\'' +
				'}';
	}
}
