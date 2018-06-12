package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmCountry;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.entity.SysCode;
import com.thinkgem.jeesite.modules.mobile.entity.SysToken;
import com.thinkgem.jeesite.modules.mobile.service.DmCountryService;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.SysCodeService;
import com.thinkgem.jeesite.modules.mobile.service.SysTokenService;
import com.thinkgem.jeesite.modules.mobile.utils.ALiYun;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import com.thinkgem.jeesite.modules.mobile.utils.MobileUtils;
import com.thinkgem.jeesite.modules.mobile.service.ValidateUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import oracle.sql.BLOB;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

/**
 * 移动端登录前（无需token）Controller
 *
 * @author 刘智科
 * @version 2018-1-16
 */
@Controller
@RequestMapping(value = "/loginBefore")
public class LoginBeforeController extends BaseController {

    @Autowired
    private DmUserService dmUserService;
    @Autowired
    private SysTokenService sysTokenService;
    @Autowired
    private SysCodeService sysCodeService;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private DmCountryService dmCountryService;

    /*
     * 获取验证码
     *
     * @param mobile 手机号
     * @param areaCode 区号
     * type 1.注册验证码 2.找回验证码
     */
    @RequestMapping("getCode")
    @ResponseBody
    public MobileResult getLoginCode(String areaCode,String mobile,String type) {
        try {
            if(!StringUtils.isNotBlank(areaCode)||!StringUtils.isNotBlank(mobile)||!StringUtils.isNotBlank(type)){
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            if(!type.equals("1") && !type.equals("2")){
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            switch (type) {
                case "1":
                    if (!validateUtils.validateMobile(areaCode+mobile)) {
                        return MobileResult.error(1002, MobileUtils.STATUS_1002);
                    }
                    break;
                case "2":
                    if (validateUtils.validateMobile(areaCode+mobile)) {
                        return MobileResult.error(1004, MobileUtils.STATUS_1004);
                    }
                    break;
            }
            String status=null;
            MobileResult mobileResult = new MobileResult();
            String code = getRandomCode(6);
            if(mobile.length()==11 && areaCode.equals("86")){
                status = ALiYun.sendSSM(mobile, MobileUtils.signNameSIGN_NAME, MobileUtils.TEMPLATE_CODE_CHINESE, code);
            }else{
                status = ALiYun.sendSSM("00"+areaCode+mobile, MobileUtils.signNameSIGN_NAME, MobileUtils.TEMPLATE_CODE_INTERNATIONAL, code);
            }
            if(status.equals("OK")){
                SysCode sysCode = new SysCode();
                sysCode.setPhone(areaCode+mobile);
                sysCode.setCode(code);
                sysCode.setExportTime(getCodeExportTime());
                sysCodeService.delete(sysCode);
                sysCodeService.save(sysCode);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", code);
                return MobileResult.ok(MobileUtils.STATUS_1009, map);
            }else if(status.equals("isv.BUSINESS_LIMIT_CONTROL")){
                return MobileResult.error(1028, MobileUtils.STATUS_1028);
            }else if(status.equals("isv.MOBILE_NUMBER_ILLEGAL")){
                return MobileResult.error(1029, MobileUtils.STATUS_1029);
            }
        } catch (Exception e) {
            return MobileResult.error(1027, MobileUtils.STATUS_1027);
        }
        return MobileResult.error(1026, MobileUtils.STATUS_1026);
    }

    /*
     * 注册接口，忘记密码
     * @param mobile 手机号
     * @param areaCode 区号
     * @param password 密码
     * @param code 验证码
     * @param type:  1.注册  2.忘记密码吗
     */
    @ResponseBody
    @RequestMapping(value = "registerORForgetPassword")
    public MobileResult registerORForgetPassword(String nickname,String areaCode,String mobile,String password,String code,String type) {
        try {
            if(!StringUtils.isNotBlank(areaCode)||!StringUtils.isNotBlank(mobile)||!StringUtils.isNotBlank(type)
                    ||!StringUtils.isNotBlank(password)||!StringUtils.isNotBlank(code)){
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            if(!type.equals("1") && !type.equals("2")){
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            switch (type) {
                case "1":
                    if (!validateUtils.validateMobile(areaCode+mobile)) {
                        return MobileResult.error(1002, MobileUtils.STATUS_1002);
                    }
                    if (!validateUtils.validateSendCode(areaCode+mobile)) {
                        return MobileResult.error(1021, MobileUtils.STATUS_1021);
                    }
                    SysCode sysCode = sysCodeService.getSysCodeByPhone(areaCode+mobile);
                    if (!validateUtils.validateCodeExportTime(sysCode.getExportTime().getTime())) {
                        return MobileResult.error(1016, MobileUtils.STATUS_1016);
                    }
                    if (!sysCode.getCode().equals(code)) {
                        return MobileResult.error(1008, MobileUtils.STATUS_1008);
                    }
                    DmUser dmUser = new DmUser();
                    if (StringUtils.isNotBlank(nickname)) {
                        dmUser.setNickname(nickname);
                    }
                    dmUser.setPassword(SystemService.entryptPassword(password));
                    String id = IdGen.getID12();
                    dmUser.setId(id);
                    dmUser.setPhoneNumber(areaCode+mobile);
                    dmUser.setIsNewRecord(true);
                    dmUserService.save(dmUser);
                    sysCodeService.delete(sysCode);
                    return MobileResult.ok(MobileUtils.STATUS_1003, "");
                case "2":
                    if (validateUtils.validateMobile(areaCode+mobile)) {
                        return MobileResult.error(1004, MobileUtils.STATUS_1004);
                    }
                    if (!validateUtils.validateSendCode(areaCode+mobile)) {
                        return MobileResult.error(1021, MobileUtils.STATUS_1021);
                    }
                    SysCode sysCode2 = sysCodeService.getSysCodeByPhone(areaCode+mobile);
                    if (!validateUtils.validateCodeExportTime(sysCode2.getExportTime().getTime())) {
                        return MobileResult.error(1016, MobileUtils.STATUS_1016);
                    }
                    if (!sysCode2.getCode().equals(code)) {
                        return MobileResult.error(1008, MobileUtils.STATUS_1008);
                    }
                    DmUser dmUser3 = dmUserService.getUserByPhoneNumber(areaCode+mobile);
                    dmUser3.setPassword(SystemService.entryptPassword(password));
                    dmUserService.save(dmUser3);
                    sysTokenService.deleteSysTokenByUserId(dmUser3.getId());
                    sysCodeService.delete(sysCode2);
                    return MobileResult.ok(MobileUtils.STATUS_1015, "");
            }
            return MobileResult.error(1023, MobileUtils.STATUS_1023);
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }
    /*
     * 登录接口
     * @param mobile 手机号
     * @param areaCode 区号
     * @param password 密码
     */
    @ResponseBody
    @RequestMapping(value = "login")
    public MobileResult login(String areaCode,String mobile, String password) {
        try {
            if(!StringUtils.isNotBlank(areaCode)||!StringUtils.isNotBlank(mobile)
                    ||!StringUtils.isNotBlank(password)){
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            if (validateUtils.validateMobile(areaCode+mobile)) {
                return MobileResult.error(1004, MobileUtils.STATUS_1004);
            }
            DmUser dmUser = dmUserService.getUserByPhoneNumber(areaCode+mobile);
            if (validateUtils.validatePassword(password, dmUser.getPassword())) {
                String token = IdGen.uuid();
                SysToken sysToken = new SysToken();
                sysToken.setToken(token);
                sysToken.setDmuserId(dmUser.getId());
                sysToken.setExportTime(DateUtils.getDataNext(MobileUtils.Export_TIME));
                sysTokenService.save(sysToken);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("token", sysToken.getToken());
                return MobileResult.ok(MobileUtils.STATUS_1006, map);
            }
            return MobileResult.error(1005, MobileUtils.STATUS_1005);
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }


    /*
     * 查询国家区号
     *
     * @param mobile 手机号
     * @param type:1.中文 2.英文 3.日文
     */
    @RequestMapping("getDmCountry")
    @ResponseBody
    public MobileResult getDmCountry(String type) {
        try {
            DmCountry dmCountry = new DmCountry();
            if (StringUtils.isNotBlank(type)) {
               switch (type){
                   case "1":
                       return MobileResult.ok(MobileUtils.STATUS_1025, dmCountryService.findListChinese(dmCountry));
                   case "2":
                       return MobileResult.ok(MobileUtils.STATUS_1025, dmCountryService.findListEnglish(dmCountry));
                   case  "3":
                       return MobileResult.ok(MobileUtils.STATUS_1025, dmCountryService.findListJapanese(dmCountry));
               }
            }
            return MobileResult.error(1023, MobileUtils.STATUS_1023);

        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }

    /*
     * 生成随机验证码
     */
    public static String getRandomCode(int num) {
        String chars = "0123456789";
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            code.append(chars.charAt(rand));
        }
        return code.toString();
    }

    /*
     * 验证码有效期时间
     */
    public static Date getCodeExportTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, MobileUtils.CODE_Export_TIME);
        return calendar.getTime();
    }
}
