/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mobile.dao.DmYunbijiDao;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 云笔记数据Service
 *
 * @author 刘智科
 * @version 2018-06-19
 */
@Service
@Transactional(readOnly = true)
public class DmYunbijiService extends CrudService<DmYunbijiDao, DmYunbiji> {

    @Autowired
    private DmYunbijiDao dmYunbijiDao;

    public DmYunbiji get(String id) {
        return super.get(id);
    }

    public List<DmYunbiji> findList(DmYunbiji dmYunbiji) {
        return super.findList(dmYunbiji);
    }

    public Page<DmYunbiji> findPage(Page<DmYunbiji> page, DmYunbiji dmYunbiji) {
        return super.findPage(page, dmYunbiji);
    }

    @Transactional(readOnly = false)
    public void save(DmYunbiji dmYunbiji) {
        super.save(dmYunbiji);
    }

    @Transactional(readOnly = false)
    public void delete(DmYunbiji dmYunbiji) {
        super.delete(dmYunbiji);
    }

    @Transactional(readOnly = false)
    public void saveYunBiJi(DmYunbiji dmYunbiji) {
        dmYunbijiDao.saveYunBiJi(dmYunbiji);
    }

    @Transactional(readOnly = false)
    public void updataYunBiJi(DmYunbiji dmYunbiji) {
        dmYunbijiDao.updataYunBiJi(dmYunbiji);
    }

    @Transactional(readOnly = false)
    public void updataYunBiJiName(DmYunbiji dmYunbiji) {
        dmYunbijiDao.updataYunBiJiName(dmYunbiji);
    }

    public List<DmYunbiji> getYunBiJiList(String dmUserId, int pageNo, int pageSize) {
        return dmYunbijiDao.getYunBiJiList(dmUserId, pageNo, pageSize);
    }

    @Transactional(readOnly = false)
    public void deleteYunBiJiByUser(String dmUserId) {
        dmYunbijiDao.deleteYunBiJiByUser(dmUserId);
    }

    public List<DmYunbiji> getAllYunBiJiList(String dmUserId) {
        return dmYunbijiDao.getAllYunBiJiList(dmUserId);
    }
}