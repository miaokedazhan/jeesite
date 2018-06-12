/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mobile.entity.DmApk;

/**
 * 移动版本管理DAO接口
 * @author 刘智科
 * @version 2018-05-10
 */
@MyBatisDao
public interface DmApkDao extends CrudDao<DmApk> {
	
}