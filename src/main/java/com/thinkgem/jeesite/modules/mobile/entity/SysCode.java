/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 验证码有效期Entity
 * @author 刘智科
 * @version 2018-05-11
 */
public class SysCode extends DataEntity<SysCode> {
	
	private static final long serialVersionUID = 1L;
	private String phone;		// 手机号
	private String code;		// 验证码
	private Date exportTime;		// 过期时间
	
	public SysCode() {
		super();
	}

	public SysCode(String id){
		super(id);
	}

	@Length(min=1, max=64, message="手机号长度必须介于 1 和 64 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=1, max=64, message="验证码长度必须介于 1 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="过期时间不能为空")
	public Date getExportTime() {
		return exportTime;
	}

	public void setExportTime(Date exportTime) {
		this.exportTime = exportTime;
	}


	
}