/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mobile.entity.SysCode;
import com.thinkgem.jeesite.modules.mobile.dao.SysCodeDao;

/**
 * 验证码有效期Service
 * @author 刘智科
 * @version 2018-05-11
 */
@Service
@Transactional(readOnly = true)
public class SysCodeService extends CrudService<SysCodeDao, SysCode> {

	@Autowired
	private SysCodeDao sysCodeDao;

	public SysCode get(String id) {
		return super.get(id);
	}

	public SysCode getSysCodeByPhone(String phone) {
		return sysCodeDao.getSysCodeByPhone(phone);
	}
	
	public List<SysCode> findList(SysCode sysCode) {
		return super.findList(sysCode);
	}
	
	public Page<SysCode> findPage(Page<SysCode> page, SysCode sysCode) {
		return super.findPage(page, sysCode);
	}
	
	@Transactional(readOnly = false)
	public void save(SysCode sysCode) {
		super.save(sysCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysCode sysCode) {
		super.delete(sysCode);
	}
	
}