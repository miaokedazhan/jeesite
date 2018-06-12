/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 移动版本管理Entity
 * @author 刘智科
 * @version 2018-05-10
 */
public class DmApk extends DataEntity<DmApk> {
	
	private static final long serialVersionUID = 1L;
	private String packagename;		// 包名
	private String name;		// 名称
	private String os;		// 平台
	private String version;		// 版本
	private String url;		// 文件
	private String message;		// 版本信息
	
	public DmApk() {
		super();
	}

	public DmApk(String id){
		super(id);
	}

	@Length(min=1, max=128, message="包名长度必须介于 1 和 128 之间")
	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	
	@Length(min=1, max=128, message="名称长度必须介于 1 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=255, message="平台长度必须介于 1 和 255 之间")
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	@Length(min=1, max=255, message="版本长度必须介于 1 和 255 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=1, max=255, message="文件长度必须介于 1 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}