package com.thinkgem.jeesite.modules.mobile.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.entity.Mobile.ConverUtils;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.DmYunbijiService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
	@Autowired
	private DmYunbijiService dmYunbijiService;
	
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
	public String list(DmUser dmUser, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		Page<DmUser> page = dmUserService.findPage(new Page<DmUser>(request, response), dmUser);
		List<DmUser> dmUserList = page.getList();
		dmUserList = ConverUtils.dmUserEmoji(dmUserList);
		page.setList(dmUserList);
		model.addAttribute("page", page);
		return "modules/mobile/dmUserList";
	}


	@RequiresPermissions("mobile:dmUser:edit")
	@RequestMapping(value = "delete")
	public String delete(DmUser dmUser, RedirectAttributes redirectAttributes) {
		dmUserService.delete(dmUser);
		dmYunbijiService.deleteYunBiJiByUser(dmUser.getId());
		addMessage(redirectAttributes, "删除用户成功");
		return "redirect:"+Global.getAdminPath()+"/mobile/dmUser/?repage";
	}

}