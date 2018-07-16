package com.thinkgem.jeesite.modules.mobile.web.mobile;

import com.thinkgem.jeesite.modules.mobile.entity.DmApk;
import com.thinkgem.jeesite.modules.mobile.service.DmApkService;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        DmApk dmApk = dmApkService.getNewApkForAndroid("com.wlzn.nazapad");
        model.addAttribute("dmApk", dmApk);
        return "modules/mobile/downLoad";
    }

    @RequestMapping(value = {"getApkList"})
    public String getApkList(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<DmApk> dmApks = dmApkService.getApkList("com.wlzn.nazapad");
        model.addAttribute("dmApks", dmApks);
        return "modules/mobile/downLoadList";
    }

    @ResponseBody
    @RequestMapping(value = {"validateVersion"})
    public MobileResult validateVersion(String os, String version, String packagename) {
        DmApk dmApk = null;
        Map<String, Object> map = new HashMap<String, Object>();
        if (("Android").equals(os)) {
            dmApk = dmApkService.getNewApkForAndroid(packagename);
            if (dmApk == null) {
                return MobileResult.ok("版本不存在", "");
            }
            if (dmApk.getVersion().equals(version)) {
                map.put("isNew", true);
                map.put("url", "is new");
                map.put("version", dmApk.getVersion());
            } else {
                map.put("isNew", false);
                map.put("url", dmApk.getUrl());
                map.put("version", dmApk.getVersion());
            }
        } else if (("Ios").equals(os)) {
            dmApk = dmApkService.getNewApkForIos(packagename);
            if (dmApk == null) {
                return MobileResult.ok("版本不存在", "");
            }
            if (dmApk.getVersion().equals(version)) {
                map.put("isNew", true);
                map.put("url", "is new");
                map.put("version", dmApk.getVersion());
            } else {
                map.put("isNew", false);
                map.put("url", dmApk.getUrl());
                map.put("version", dmApk.getVersion());
            }
        } else {
            map.put("isNew", true);
            map.put("url", "");
            map.put("version", version);
        }
        return MobileResult.ok("验证版本成功", map);
    }

}
