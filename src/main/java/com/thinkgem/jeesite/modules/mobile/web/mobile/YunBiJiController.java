package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UploadUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.SysCodeService;
import com.thinkgem.jeesite.modules.mobile.service.SysTokenService;
import com.thinkgem.jeesite.modules.mobile.service.ValidateUtils;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import com.thinkgem.jeesite.modules.mobile.utils.MobileUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 移动端登录之后（有token）Controller
 *
 * @author 刘智科
 * @version 2018-1-16
 */
@Controller
@RequestMapping(value = "/yunbiji")
public class YunBiJiController extends BaseController {

    @Autowired
    private DmUserService dmUserService;
    @Autowired
    private SysTokenService sysTokenService;
    @Autowired
    private SysCodeService sysCodeService;
    @Autowired
    private ValidateUtils validateUtils;

    @ModelAttribute
    public DmUser init(HttpServletRequest request) {
        return (DmUser) request.getAttribute("dmUser");
    }


    /*
     * 头像管理
     * type 1.增加修改2.删除
     */
    @ResponseBody
    @RequestMapping(value = "headPortrait")
    public MobileResult headPortrait(HttpServletRequest request, DmUser dmUser, String type) {
        try {
            if (!StringUtils.isNotBlank(type)) {
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            if (!type.equals("1") && !type.equals("2")) {
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            switch (type) {
                case "1":
                    UploadUtils uploadUtils = new UploadUtils();
                    String[] str = uploadUtils.uploadFile(request);
                    if (str != null && str.length > 0) {
                        if (str[0].equals("true") && str[1].equals("true")) {
                            //删除指定图片
//                            String str="http://192.168.0.58:8080/upload/images/20180605161512_551.jpg";
//                            str=str.substring(MobileUtils.URL.length()+1);
//                            new File( request.getSession().getServletContext().getRealPath("/")+"upload/images/20180605161512_551.jpg").delete();
                            dmUser.setHeadPortrait(MobileUtils.URL + str[4]);
                            dmUserService.save(dmUser);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("url", MobileUtils.URL + str[4]);
                            return MobileResult.ok(MobileUtils.STATUS_1030, map);
                        }
                        if (str[0].equals("true")) {
                            return MobileResult.error(1033, MobileUtils.STATUS_1033);
                        }
                        if (str[1].equals("true")) {
                            return MobileResult.error(1034, MobileUtils.STATUS_1034);
                        }
                    }
                    return MobileResult.error(1033, MobileUtils.STATUS_1033);
                case "2":
                    dmUser.setHeadPortrait(null);
                    dmUserService.save(dmUser);
                    return MobileResult.ok(MobileUtils.STATUS_1032, "");
            }
            MobileResult mobileResult = new MobileResult();
            mobileResult.setResultData(dmUser);
            return mobileResult;
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }


    /*
     *获取头像管理
     */
    @ResponseBody
    @RequestMapping(value = "getHeadPortrait")
    public MobileResult getHeadPortrait(HttpServletResponse response, DmUser dmUser) {
        if(null==dmUser.getHeadPortrait()){
           return MobileResult.ok(MobileUtils.STATUS_1036,"");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", dmUser.getHeadPortrait());
        return  MobileResult.ok(MobileUtils.STATUS_1035,map);

    }

    /*
     *817c9339d1e7598899e0876789930ee1822f860c6865218b00e2390e 对应密码1234
     * 修改密码
     * @param pwd  新密码
     * @param oldPwd 原始密码
     */
    @ResponseBody
    @RequestMapping(value = "updataPassword")
    public MobileResult updataPassword(DmUser dmUser, String pwd, String oldPwd) {
        try {
            MobileResult mobileResult = new MobileResult();
            if (!validateUtils.validatePassword(oldPwd, dmUser.getPassword())) {
                return MobileResult.error(1018, MobileUtils.STATUS_1018);
            }
            dmUser.setPassword(SystemService.entryptPassword(pwd));
            dmUserService.save(dmUser);
            sysTokenService.deleteSysTokenByUserId(dmUser.getId());
            return MobileResult.ok(MobileUtils.STATUS_1015, "");
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }

    /*
     * 登录退出
     * 增加一个空行
     *
     */
    @ResponseBody
    @RequestMapping(value = "loginout")
    public MobileResult loginout(HttpServletRequest request) {
        try {
            sysTokenService.deleteSysTokenByToken(request.getHeader("token"));
            return MobileResult.ok(MobileUtils.STATUS_1019, "");
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }


}
