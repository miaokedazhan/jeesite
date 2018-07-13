package com.thinkgem.jeesite.modules.mobile.web.mobile;

import com.thinkgem.jeesite.modules.mobile.entity.DmApk;
import com.thinkgem.jeesite.modules.mobile.service.DmApkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 刘智科
 * @Title: $file_name
 * @Package $package_name
 * @Description: $todo
 */
@Controller
@RequestMapping(value = "/version")
public class VersionManager {

    @Autowired
    private DmApkService dmApkService;

    @RequestMapping(value = {"getApk"})
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        DmApk dmApk = dmApkService.getNewApk();
        model.addAttribute("dmApk", dmApk);
        return "modules/mobile/downLoad";
    }

    @RequestMapping(value = {"getApkList"})
    public String getApkList(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<DmApk> dmApks = dmApkService.getApkList();
        model.addAttribute("dmApks", dmApks);
        return "modules/mobile/downLoadList";
    }
}
