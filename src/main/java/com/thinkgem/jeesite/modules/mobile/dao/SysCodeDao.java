/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mobile.entity.SysCode;

/**
 * 验证码有效期DAO接口
 * @author 刘智科
 * @version 2018-05-11
 */
@MyBatisDao
public interface SysCodeDao extends CrudDao<SysCode> {

    public SysCode getSysCodeByPhone(String phone);
	
}