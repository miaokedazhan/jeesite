/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mobile.dao.DmApkDao;
import com.thinkgem.jeesite.modules.mobile.dao.DmCountryDao;
import com.thinkgem.jeesite.modules.mobile.entity.DmApk;
import com.thinkgem.jeesite.modules.mobile.entity.DmCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国际电话号码区号Service
 * @author 刘智科
 * @version 2018-05-10
 */
@Service
@Transactional(readOnly = true)
public class DmCountryService extends CrudService<DmCountryDao, DmCountry> {
    @Autowired
	private DmCountryDao DmCountryDao;

	
	public Map<String,Object> findListChinese(DmCountry dmCountry) {
		Map<String,Object> map = new HashMap<String,Object>();
		char begin_letter='A';
		for (int i=(int)begin_letter;i<begin_letter+26;i++){
			map.put(String.valueOf((char)i),DmCountryDao.findChineseByZIMU(String.valueOf((char)i)));
		}
		return map;
	}
	public Map<String,Object> findListEnglish(DmCountry dmCountry) {
		Map<String,Object> map = new HashMap<String,Object>();
		char begin_letter='A';
		for (int i=(int)begin_letter;i<begin_letter+26;i++){
			map.put(String.valueOf((char)i),DmCountryDao.finEnglishByZIMU(String.valueOf((char)i)));
		}
		return map;
	}
	public List<DmCountry> findListJapanese(DmCountry dmCountry) {
		return DmCountryDao.findListJapanese(dmCountry);
	}
	

	
}