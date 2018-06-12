package com.thinkgem.jeesite.modules.mobile.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * token验证Entity
 * @author 刘智科
 * @version 2018-05-09
 */
public class SysToken extends DataEntity<SysToken> {
	
	private static final long serialVersionUID = 1L;
	private String token;		// token
	private String dmuserId;		// 用户id
	private Date exportTime;		// 过期时间
	
	public SysToken() {
		super();
	}

	public SysToken(String id){
		super(id);
	}

	@Length(min=1, max=64, message="token长度必须介于 1 和 64 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Length(min=1, max=64, message="用户id长度必须介于 1 和 64 之间")
	public String getDmuserId() {
		return dmuserId;
	}

	public void setDmuserId(String dmuserId) {
		this.dmuserId = dmuserId;
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