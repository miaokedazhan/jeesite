package com.thinkgem.jeesite.modules.mobile.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mobile.entity.SysToken;

import java.util.List;

/**
 * token验证DAO接口
 * @author 刘智科
 * @version 2018-05-09
 */
@MyBatisDao
public interface SysTokenDao extends CrudDao<SysToken> {
         public SysToken getSysTokenByToken(String token) ;

         public List<SysToken> getSysTokenByUserId(String dmuserId) ;

         public int updateSysTokenByToken(SysToken sysToken) ;

         public int deleteSysTokenByToken(String token) ;

          public int deleteSysTokenByUserId(String dmuserId) ;

}