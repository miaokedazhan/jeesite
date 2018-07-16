/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmApk;
import com.thinkgem.jeesite.modules.mobile.service.DmApkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 移动版本管理Controller
 * @author 刘智科
 * @version 2018-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/mobile/dmApk")
public class DmApkController extends BaseController {

	@Autowired
	private DmApkService dmApkService;
	
	@ModelAttribute
	public DmApk get(@RequestParam(required=false) String id) {
		DmApk entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmApkService.get(id);
		}
		if (entity == null){
			entity = new DmApk();
		}
		return entity;
	}
	
	@RequiresPermissions("mobile:dmApk:view")
	@RequestMapping(value = {"list", ""})
	public String list(DmApk dmApk, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmApk> page = dmApkService.findPage(new Page<DmApk>(request, response), dmApk); 
		model.addAttribute("page", page);
		return "modules/mobile/dmApkList";
	}

	@RequiresPermissions("mobile:dmApk:view")
	@RequestMapping(value = "form")
	public String form(DmApk dmApk, Model model) {
		model.addAttribute("dmApk", dmApk);
		return "modules/mobile/dmApkForm";
	}

	@RequiresPermissions("mobile:dmApk:edit")
	@RequestMapping(value = "save")
	public String save(DmApk dmApk, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dmApk)){
			return form(dmApk, model);
		}
		dmApkService.save(dmApk);
		addMessage(redirectAttributes, "保存版本成功");
		return "redirect:"+Global.getAdminPath()+"/mobile/dmApk/?repage";
	}

    @RequiresPermissions("mobile:dmApk:delete")
	@RequestMapping(value = "delete")
	public String delete(DmApk dmApk, RedirectAttributes redirectAttributes) {
		dmApkService.delete(dmApk);
		addMessage(redirectAttributes, "删除版本成功");
		return "redirect:"+Global.getAdminPath()+"/mobile/dmApk/?repage";
	}

	/**
	 * 验证登录名是否有效
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkVersion")
	public String checkVersion(String oldVersion, String version, String os) {
		if (version != null && version.equals(oldVersion)) {
			return "true";
		} else if (version != null && dmApkService.checkVersion(version) == null) {
			return "true";
		}
		return "false";
	}

}