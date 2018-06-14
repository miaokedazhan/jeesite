package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.ValidateUtils;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 移动端登录之后（有token）Controller
 *
 * @author 刘智科
 * @version 2018-1-16
 */
@Controller
@RequestMapping(value = "/login")
public class UpLoadControllerCopy extends BaseController {

    @Autowired
    private DmUserService dmUserService;


    @Autowired
    private ValidateUtils validateUtils;


    /*
     * 上传头像
     *
     */
    @RequestMapping("uploadHeadPortrait")
    @ResponseBody
    public MobileResult addHeadPortrait(HttpServletRequest request) {
       DmUser dmUser = dmUserService.get("527844360540");
       if(null==dmUser.getHeadPortrait()){
           System.out.println("12345678");
       }
       /* UploadUtils uploadUtils = new UploadUtils();
        String[] str = uploadUtils.uploadFile(req);
        if(str!=null&&str.length>0){
        if(str[0].equals("true")&&str[1].equals("true")){
            return  MobileResult.ok(MobileUtils.STATUS_1030,MobileUtils.URL+str[4]);
        }
        if(str[0].equals("true")){
            return  MobileResult.error(1033,MobileUtils.STATUS_1033);
        }
        if(str[1].equals("true")){
            return  MobileResult.error(1034,MobileUtils.STATUS_1034);
        }
        }*/
        return  MobileResult.ok(1033+"",dmUser);
    }

}
