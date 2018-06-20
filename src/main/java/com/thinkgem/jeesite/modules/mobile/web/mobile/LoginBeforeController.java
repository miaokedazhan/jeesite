package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmCountry;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;
import com.thinkgem.jeesite.modules.mobile.service.DmCountryService;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.DmYunbijiService;
import com.thinkgem.jeesite.modules.mobile.service.ValidateUtils;
import com.thinkgem.jeesite.modules.mobile.utils.ALiYun;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import com.thinkgem.jeesite.modules.mobile.utils.MobileUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private ValidateUtils validateUtils;
    @Autowired
    private DmCountryService dmCountryService;
    @Autowired
    private DmYunbijiService dmYunbijiService;

    /*
     * 获取验证码
     *
     * @param mobile 手机号
     * @param areaCode 区号
     * type 1.注册验证码 2.找回验证码
     */
    @RequestMapping("getCode")
    @ResponseBody
    public MobileResult getLoginCode(String areaCode, String mobile, String type) {
        try {
            if (!StringUtils.isNotBlank(areaCode) || !StringUtils.isNotBlank(mobile) || !StringUtils.isNotBlank(type)) {
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            if (!type.equals("1") && !type.equals("2")) {
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            switch (type) {
                case "1":
                    if (!validateUtils.validateMobile(areaCode + mobile)) {
                        return MobileResult.error(1002, MobileUtils.STATUS_1002);
                    }
                    break;
                case "2":
                    if (validateUtils.validateMobile(areaCode + mobile)) {
                        return MobileResult.error(1004, MobileUtils.STATUS_1004);
                    }
                    break;
            }
            String status = null;
            MobileResult mobileResult = new MobileResult();
            String code = getRandomCode(6);
            if (mobile.length() == 11 && areaCode.equals("86")) {
                status = ALiYun.sendSSM(mobile, MobileUtils.signNameSIGN_NAME, MobileUtils.TEMPLATE_CODE_CHINESE, code);
            } else {
                status = ALiYun.sendSSM("00" + areaCode + mobile, MobileUtils.signNameSIGN_NAME, MobileUtils.TEMPLATE_CODE_INTERNATIONAL, code);
            }
            if (status.equals("OK")) {
                JedisUtils.set(areaCode + mobile, code, MobileUtils.REDIS_CODE_Export_TIME);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", code);
                return MobileResult.ok(MobileUtils.STATUS_1009, map);
            } else if (status.equals("isv.BUSINESS_LIMIT_CONTROL")) {
                return MobileResult.error(1028, MobileUtils.STATUS_1028);
            } else if (status.equals("isv.MOBILE_NUMBER_ILLEGAL")) {
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
    public MobileResult registerORForgetPassword(String nickname, String areaCode, String mobile, String password, String code, String type) {
        try {
            if (!StringUtils.isNotBlank(areaCode) || !StringUtils.isNotBlank(mobile) || !StringUtils.isNotBlank(type)
                    || !StringUtils.isNotBlank(password) || !StringUtils.isNotBlank(code)) {
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            if (!type.equals("1") && !type.equals("2")) {
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            switch (type) {
                case "1":
                    if (!validateUtils.validateMobile(areaCode + mobile)) {
                        return MobileResult.error(1002, MobileUtils.STATUS_1002);
                    }
                    String redisCode = JedisUtils.get(areaCode + mobile);
                    if (StringUtils.isEmpty(redisCode)) {
                        return MobileResult.error(1040, MobileUtils.STATUS_1040);
                    }
                    if (!redisCode.equals(code)) {
                        return MobileResult.error(1008, MobileUtils.STATUS_1008);
                    }
                    DmUser dmUser = new DmUser();
                    if (StringUtils.isNotBlank(nickname)) {
                        dmUser.setNickname(nickname);
                    }
                    dmUser.setPassword(SystemService.entryptPassword(password));
                    String id = IdGen.getID12();
                    dmUser.setCountryCode(areaCode);
                    dmUser.setPhone(mobile);
                    dmUser.setId(id);
                    dmUser.setPhoneNumber(areaCode + mobile);
                    dmUser.setIsNewRecord(true);
                    dmUserService.save(dmUser);
                    JedisUtils.del(areaCode + mobile);
                    return MobileResult.ok(MobileUtils.STATUS_1003, "");
                case "2":
                    if (validateUtils.validateMobile(areaCode + mobile)) {
                        return MobileResult.error(1004, MobileUtils.STATUS_1004);
                    }
                    String redisCode2 = JedisUtils.get(areaCode + mobile);
                    if (StringUtils.isEmpty(redisCode2)) {
                        return MobileResult.error(1040, MobileUtils.STATUS_1040);
                    }
                    if (!redisCode2.equals(code)) {
                        return MobileResult.error(1008, MobileUtils.STATUS_1008);
                    }
                    DmUser dmUser3 = dmUserService.getUserByPhoneNumber(areaCode + mobile);
                    dmUser3.setPassword(SystemService.entryptPassword(password));
                    dmUserService.save(dmUser3);
                    JedisUtils.delObject(dmUser3.getToken());
                    JedisUtils.del(areaCode + mobile);
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
    public MobileResult login(String areaCode, String mobile, String password) {
        try {
            if (!StringUtils.isNotBlank(areaCode) || !StringUtils.isNotBlank(mobile)
                    || !StringUtils.isNotBlank(password)) {
                return MobileResult.error(1023, MobileUtils.STATUS_1023);
            }
            if (validateUtils.validateMobile(areaCode + mobile)) {
                return MobileResult.error(1004, MobileUtils.STATUS_1004);
            }
            DmUser dmUser = dmUserService.getUserByPhoneNumber(areaCode + mobile);
            if (validateUtils.validatePassword(password, dmUser.getPassword())) {
                if (StringUtils.isEmpty(dmUser.getToken())) {
                    dmUser.setToken("#############");
                }
                DmUser tokenDm = (DmUser) JedisUtils.getObject(dmUser.getToken());
                if (tokenDm != null) {
                    tokenDm.setIsLogin(true);
                    JedisUtils.refushOlderObject(dmUser.getToken(), tokenDm);
                }
                String token = IdGen.uuid();
                dmUser.setToken(token);
                dmUserService.save(dmUser);
                JedisUtils.setObject(token, dmUser, MobileUtils.Redis_Export_TIME);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("token", token);
                map.put("nickname", dmUser.getNickname());
                map.put("headPortrait", dmUser.getHeadPortrait());
                map.put("countryCode", dmUser.getCountryCode());
                map.put("phone", dmUser.getPhone());
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
                switch (type) {
                    case "1":
                        return MobileResult.ok(MobileUtils.STATUS_1025, dmCountryService.findListChinese(dmCountry));
                    case "2":
                        return MobileResult.ok(MobileUtils.STATUS_1025, dmCountryService.findListEnglish(dmCountry));
                    case "3":
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

    /*
     * 获取笔记
     */
    @ResponseBody
    @RequestMapping(value = "getYunBiJi")
    public void getHeadPortrait(HttpServletResponse response, String id) {
        response.setContentType("image/jpeg");
        DmYunbiji dmYunbiji = new DmYunbiji();
        dmYunbiji.setId(id);
        dmYunbiji = dmYunbijiService.get(dmYunbiji);
        try {
            byte[] picture = (byte[]) dmYunbiji.getBiji();
            ServletOutputStream os = response.getOutputStream();
            os.write(picture);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取笔记
     */
    @ResponseBody
    @RequestMapping(value = "deletTest")
    public MobileResult deletTest(HttpServletRequest request) {

        String str = "http://192.168.0.58:8080/upload/images/20180614170010_674.jpg";
        str = str.substring(MobileUtils.URL.length() + 1);
        System.out.println(str);
        new File(request.getSession().getServletContext().getRealPath("/") + str).delete();
        return MobileResult.ok("1", "");
    }


}
