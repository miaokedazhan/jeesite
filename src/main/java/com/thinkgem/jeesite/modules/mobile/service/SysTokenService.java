package com.thinkgem.jeesite.modules.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mobile.entity.SysToken;
import com.thinkgem.jeesite.modules.mobile.dao.SysTokenDao;

/**
 * token验证Service
 * @author 刘智科
 * @version 2018-05-09
 */
@Service
@Transactional(readOnly = true)
public class SysTokenService extends CrudService<SysTokenDao, SysToken> {
	@Autowired
	private  SysTokenDao  sysTokenDao;
	public SysToken get(String id) {
		return super.get(id);
	}

	public SysToken getSysTokenByToken(String token) {
		return sysTokenDao.getSysTokenByToken(token);
	}

	public List<SysToken> getSysTokenByUserId(String dmuserId) {
		return sysTokenDao.getSysTokenByUserId(dmuserId);
	}

	@Transactional(readOnly = false)
	public int updateSysTokenByToken(SysToken  sysToken) {
		return sysTokenDao.updateSysTokenByToken(sysToken);
	}

	@Transactional(readOnly = false)
	public int deleteSysTokenByToken(String token) {
		return sysTokenDao.deleteSysTokenByToken(token);
	}
	@Transactional(readOnly = false)
	public int deleteSysTokenByUserId(String dmuserId) {
		return sysTokenDao.deleteSysTokenByUserId(dmuserId);
	}

	public List<SysToken> findList(SysToken sysToken) {
		return super.findList(sysToken);
	}
	
	public Page<SysToken> findPage(Page<SysToken> page, SysToken sysToken) {
		return super.findPage(page, sysToken);
	}
	
	@Transactional(readOnly = false)
	public void save(SysToken sysToken) {
		super.save(sysToken);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysToken sysToken) {
		super.delete(sysToken);
	}
	
}