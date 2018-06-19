package com.thinkgem.jeesite.modules.mobile.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 用户信息Entity
 * @author 刘智科
 * @version 2018-05-09
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
public class DmUser extends DataEntity<DmUser> {
	
	private static final long serialVersionUID = 1L;
	private String nickname;		// 昵称
    private String countryCode;        // 区号
    private String phone;        // 手机号
	private String phoneNumber;  //手机号（登录名）
	private String password;		// 密码
	private String headPortrait;	 // 用户头像
	private String code; //手机验证码
	private String token; //token信息
	private Boolean isLogin = false; //是否异地登录

	public DmUser() {
		super();
	}

	public DmUser(String id){
		super(id);
	}

	@Length(min=1, max=100, message="昵称长度必须介于 1 和 100 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Length(min=1, max=100, message="手机号长度必须介于 1 和 100 之间")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean login) {
		isLogin = login;
	}

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}