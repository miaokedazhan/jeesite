/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;
import com.thinkgem.jeesite.modules.mobile.entity.Mobile.ConverUtils;
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
 * 云笔记数据Controller
 *
 * @author 刘智科
 * @version 2018-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/mobile/dmYunbiji")
public class DmYunbijiController extends BaseController {

    @Autowired
    private DmYunbijiService dmYunbijiService;

    @ModelAttribute
    public DmYunbiji get(@RequestParam(required = false) String id) {
        DmYunbiji entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dmYunbijiService.get(id);
        }
        if (entity == null) {
            entity = new DmYunbiji();
        }
        return entity;
    }

    @RequiresPermissions("mobile:dmYunbiji:view")
    @RequestMapping(value = {"list", ""})
    public String list(DmYunbiji dmYunbiji, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        Page<DmYunbiji> page = dmYunbijiService.findPage(new Page<DmYunbiji>(request, response), dmYunbiji);
        List<DmYunbiji> dmYunbijiList = page.getList();
        dmYunbijiList = ConverUtils.dmYunbijiEmoji(dmYunbijiList);
        page.setList(dmYunbijiList);
        model.addAttribute("page", page);
        return "modules/mobile/dmYunbijiList";
    }


    @RequiresPermissions("mobile:dmYunbiji:edit")
    @RequestMapping(value = "delete")
    public String delete(DmYunbiji dmYunbiji, RedirectAttributes redirectAttributes) {
        dmYunbijiService.delete(dmYunbiji);
        addMessage(redirectAttributes, "删除云笔记数据成功");
        return "redirect:" + Global.getAdminPath() + "/mobile/dmYunbiji/?repage";
    }

}