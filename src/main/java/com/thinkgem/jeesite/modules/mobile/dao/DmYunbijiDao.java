/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;

import java.util.List;

/**
 * 云笔记数据DAO接口
 *
 * @author 刘智科
 * @version 2018-06-19
 */
@MyBatisDao
public interface DmYunbijiDao extends CrudDao<DmYunbiji> {

    void saveYunBiJi(DmYunbiji dmYunbiji);

    void updataYunBiJi(DmYunbiji dmYunbiji);

    void updataYunBiJiName(DmYunbiji dmYunbiji);

    List<DmYunbiji> getYunBiJiList(DmYunbiji dmYunbiji);
}