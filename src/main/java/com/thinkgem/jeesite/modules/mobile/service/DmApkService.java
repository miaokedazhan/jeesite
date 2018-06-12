/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mobile.entity.DmApk;
import com.thinkgem.jeesite.modules.mobile.dao.DmApkDao;

/**
 * 移动版本管理Service
 * @author 刘智科
 * @version 2018-05-10
 */
@Service
@Transactional(readOnly = true)
public class DmApkService extends CrudService<DmApkDao, DmApk> {

	public DmApk get(String id) {
		return super.get(id);
	}
	
	public List<DmApk> findList(DmApk dmApk) {
		return super.findList(dmApk);
	}
	
	public Page<DmApk> findPage(Page<DmApk> page, DmApk dmApk) {
		return super.findPage(page, dmApk);
	}
	
	@Transactional(readOnly = false)
	public void save(DmApk dmApk) {
		super.save(dmApk);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmApk dmApk) {
		super.delete(dmApk);
	}
	
}