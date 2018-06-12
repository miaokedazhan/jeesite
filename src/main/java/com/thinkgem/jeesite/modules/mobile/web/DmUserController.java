package com.thinkgem.jeesite.modules.mobile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;

/**
 * 用户信息Controller
 * @author 刘智科
 * @version 2018-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/mobile/dmUser")
public class DmUserController extends BaseController {

	@Autowired
	private DmUserService dmUserService;
	
	@ModelAttribute
	public DmUser get(@RequestParam(required=false) String id) {
		DmUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmUserService.get(id);
		}
		if (entity == null){
			entity = new DmUser();
		}
		return entity;
	}
	
	@RequiresPermissions("mobile:dmUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(DmUser dmUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmUser> page = dmUserService.findPage(new Page<DmUser>(request, response), dmUser); 
		model.addAttribute("page", page);
		return "modules/mobile/dmUserList";
	}

	@RequiresPermissions("mobile:dmUser:view")
	@RequestMapping(value = "form")
	public String form(DmUser dmUser, Model model) {
		model.addAttribute("dmUser", dmUser);
		return "modules/mobile/dmUserForm";
	}

	@RequiresPermissions("mobile:dmUser:edit")
	@RequestMapping(value = "save")
	public String save(DmUser dmUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dmUser)){
			return form(dmUser, model);
		}
		dmUserService.save(dmUser);
		addMessage(redirectAttributes, "保存用户成功");
		return "redirect:"+Global.getAdminPath()+"/mobile/dmUser/?repage";
	}
	
	@RequiresPermissions("mobile:dmUser:edit")
	@RequestMapping(value = "delete")
	public String delete(DmUser dmUser, RedirectAttributes redirectAttributes) {
		dmUserService.delete(dmUser);
		addMessage(redirectAttributes, "删除用户成功");
		return "redirect:"+Global.getAdminPath()+"/mobile/dmUser/?repage";
	}

}