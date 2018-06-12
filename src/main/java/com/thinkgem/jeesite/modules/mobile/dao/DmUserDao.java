package com.thinkgem.jeesite.modules.mobile.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;

/**
 * 用户信息DAO接口
 * @author 刘智科
 * @version 2018-05-09
 */
@MyBatisDao
public interface DmUserDao extends CrudDao<DmUser> {

    public DmUser getUserByPhoneNumber(String phoneNumber);

}