/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 国际电话号码区号Entity
 * @author 刘智科
 * @version 2018-05-19
 */
public class DmCountry extends DataEntity<DmCountry> {
	
	private static final long serialVersionUID = 1L;
	private String chinese;		// 国家名称
	private String english;		// 国家名称
	private String japanese;		// 国家名称
	private String mobilePrefix;		// 区号
	private String area;		// 所在的洲
	private  String initial;  //首字母
	
	public DmCountry() {
		super();
	}

	public DmCountry(String id){
		super(id);
	}


	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getJapanese() {
		return japanese;
	}

	public void setJapanese(String japanese) {
		this.japanese = japanese;
	}

	@Length(min=0, max=255, message="区号长度必须介于 0 和 255 之间")
	public String getMobilePrefix() {
		return mobilePrefix;
	}

	public void setMobilePrefix(String mobilePrefix) {
		this.mobilePrefix = mobilePrefix;
	}
	
	@Length(min=0, max=255, message="所在的洲长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Length(min=0, max=255, message="首字母长度必须介于 0 和 2 之间")
	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}
}