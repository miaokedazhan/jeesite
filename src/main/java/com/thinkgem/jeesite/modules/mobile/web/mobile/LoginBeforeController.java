package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmCountry;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;
import com.thinkgem.jeesite.modules.mobile.entity.Mobile.ConverUtils;
import com.thinkgem.jeesite.modules.mobile.entity.Mobile.FileBean;
import com.thinkgem.jeesite.modules.mobile.service.DmCountryService;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.DmYunbijiService;
import com.thinkgem.jeesite.modules.mobile.service.ValidateUtils;
import com.thinkgem.jeesite.modules.mobile.utils.ALiYun;
import com.thinkgem.jeesite.modules.mobile.utils.EmojiUtil;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import com.thinkgem.jeesite.modules.mobile.utils.MobileUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
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
                        dmUser.setNickname(EmojiUtil.emojiConvert1(nickname));
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
                map.put("id", dmUser.getId());
                map.put("token", token);
                map.put("nickname", EmojiUtil.emojiRecovery2(dmUser.getNickname()));
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
     *云笔记上传-流
     */
    @ResponseBody
    @RequestMapping(value = "uploadYunBiJiFromByte")
    public MobileResult uploadYunBiJi(HttpServletRequest request, DmUser dmUser) {
        try {
            // 文件保存目录相对路径
            String basePath = "upload";
            // 文件的目录名
            String dirName = "data";
            // 文件保存目录路径
            String savePath;
            // 文件保存目录url
            String saveUrl;
            // 上传临时路径
            String TEMP_PATH = "/temp";
            String tempPath = basePath + TEMP_PATH;
            // 文件最终的url包括文件名
            String fileUrl;

            savePath = request.getSession().getServletContext().getRealPath("/") + basePath + "/";
            // 文件保存目录URL
            saveUrl = request.getContextPath() + "/" + basePath + "/";
            File uploadDir = new File(savePath);
            savePath += dirName + "/";
            saveUrl += dirName + "/";
            File saveDirFile = new File(savePath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            tempPath = request.getSession().getServletContext().getRealPath("/") + tempPath + "/";
            File file = new File(tempPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!ServletFileUpload.isMultipartContent(request)) {
                return MobileResult.error(1031, MobileUtils.STATUS_1031);
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 1024 * 10);
            factory.setRepository(new File(tempPath));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            List<FileItem> list = upload.parseRequest(request);
            List<FileBean> fileBeanList = new ArrayList<FileBean>();
            DmYunbiji dmYunbiji = new DmYunbiji();
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                } else {
                    String fileName = item.getName();
                    String newFileName;
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    newFileName = df.format(new Date()) + "_" + fileName;
                    fileUrl = saveUrl + newFileName;
                    File uploadedFile = new File(savePath, newFileName);
                    item.write(uploadedFile);
                    dmYunbiji.setBiji(fileUrl);
                    if (fileName.indexOf(".note") == -1) {
                        dmYunbiji.setBijiImage(fileUrl);
                    } else {
                        dmYunbiji.setBijiName(fileName);
                        dmYunbiji.setCreateDate(new Date());
                        dmYunbiji.setName(dmUser);
                        dmYunbiji.setBiji(fileUrl);
                        String id = IdGen.getID12();
                        dmYunbiji.setId(id);
                        dmYunbiji.setBijiSize(String.valueOf(item.getSize()));
                        dmYunbiji.setBijiType(".note");
                        dmYunbijiService.saveYunBiJi(dmYunbiji);
                        fileBeanList.add(ConverUtils.yunbijiToBean(dmYunbiji));
                    }
                }
            }
            return MobileResult.ok(MobileUtils.STATUS_1041, fileBeanList);
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }


}
