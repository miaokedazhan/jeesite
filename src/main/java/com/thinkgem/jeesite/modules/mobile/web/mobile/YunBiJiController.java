package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UploadUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;
import com.thinkgem.jeesite.modules.mobile.entity.Mobile.ConverUtils;
import com.thinkgem.jeesite.modules.mobile.entity.Mobile.FileBean;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.DmYunbijiService;
import com.thinkgem.jeesite.modules.mobile.service.ValidateUtils;
import com.thinkgem.jeesite.modules.mobile.utils.EmojiUtil;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import com.thinkgem.jeesite.modules.mobile.utils.MobileUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 移动端登录之后（有token）Controller
 *
 * @author 刘智科
 * @version 2018-6-16
 */
@Controller
@RequestMapping(value = "/yunbiji")
public class YunBiJiController extends BaseController {

    @Autowired
    private DmUserService dmUserService;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private DmYunbijiService dmYunbijiService;

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
                            if (!StringUtils.isEmpty(dmUser.getHeadPortrait())) {
                                new File(request.getSession().getServletContext().getRealPath("/") + dmUser.getHeadPortrait()).delete();
                            }
                            //删除指定图片
//                            String str="http://192.168.0.58:8080/upload/images/20180605161512_551.jpg";
//                            str=str.substring(MobileUtils.URL.length()+1);
//                            new File( request.getSession().getServletContext().getRealPath("/")+"upload/images/20180605161512_551.jpg").delete();
                            dmUser.setHeadPortrait(str[4]);
                            dmUserService.save(dmUser);
                            JedisUtils.refushObject(dmUser.getToken(), dmUser);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("url", str[4]);
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
                    JedisUtils.refushObject(dmUser.getToken(), dmUser);
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
     *获取用户信息
     */
    @ResponseBody
    @RequestMapping(value = "getUserInfo")
    public MobileResult getHeadPortrait(DmUser dmUser) {
        try {
            dmUser.setPassword(null);
            dmUser.setUpdateDate(null);
            dmUser.setUpdateBy(null);
            dmUser.setCreateBy(null);
            dmUser.setCreateDate(null);
            dmUser.setNickname(EmojiUtil.emojiRecovery2(dmUser.getNickname()));
            if (StringUtils.isEmpty(dmUser.getHeadPortrait())) {
                dmUser.setHeadPortrait("");
            }
            return MobileResult.ok(MobileUtils.STATUS_1035, dmUser);
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }

    /*
     *817c9339d1e7598899e0876789930ee1822f860c6865218b00e2390e 对应密码1234
     * 修改个人信息
     * @param pwd  新密码
     * @param oldPwd 原始密码
     * nickname 昵称
     */
    @ResponseBody
    @RequestMapping(value = "updataUserInfo")
    public MobileResult updataPassword(DmUser dmUser, String pwd, String oldPwd, String nickname) {
        try {
            if (!StringUtils.isEmpty(pwd) && !StringUtils.isEmpty(oldPwd)) {
                if (!validateUtils.validatePassword(oldPwd, dmUser.getPassword())) {
                    return MobileResult.error(1018, MobileUtils.STATUS_1018);
                }
                dmUser.setPassword(SystemService.entryptPassword(pwd));
                dmUserService.save(dmUser);
                JedisUtils.delObject(dmUser.getToken());
            }
            if (!StringUtils.isEmpty(nickname)) {
                dmUser.setNickname(EmojiUtil.emojiConvert1(nickname));
                dmUserService.save(dmUser);
                JedisUtils.refushObject(dmUser.getToken(), dmUser);
            }
            return MobileResult.ok(MobileUtils.STATUS_1015, "");
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }

    /*
     * 登录退出
     */
    @ResponseBody
    @RequestMapping(value = "loginout")
    public MobileResult loginout(HttpServletRequest request) {
        try {
            JedisUtils.delObject(request.getHeader("token"));
            return MobileResult.ok(MobileUtils.STATUS_1019, "");
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }

    /*
     *云笔记上传-流
     */
    @ResponseBody
    @RequestMapping(value = "uploadYunBiJiFromByte")
    public MobileResult uploadYunBiJi(HttpServletRequest request, DmUser dmUser) {
        try {
            String token = request.getHeader("token");
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
                    System.out.println(name + "=" + value);
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
                        System.out.println("id==" + id);
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

    /*
     * 获取笔记账号所有笔记
     */
    @ResponseBody
    @RequestMapping(value = "getAllYunBiJi")
    public MobileResult getAllYunBiJi(DmUser dmUser) {
        DmYunbiji dmYunbiji = new DmYunbiji();
        dmYunbiji.setName(dmUser);
        List<DmYunbiji> dmYunbijis = dmYunbijiService.getYunBiJiList(dmYunbiji);
        try {
            return MobileResult.ok(MobileUtils.STATUS_1044, ConverUtils.yunbijiListToBeanList(dmYunbijis));
        } catch (Exception e) {
            return MobileResult.exception("error " + e.toString());
        }
    }

    /*
     * 删除笔记
     */
    @ResponseBody
    @RequestMapping(value = "deleteYunBiJi")
    public MobileResult deleteYunBiJi(String id) {
        DmYunbiji dmYunbiji = new DmYunbiji();
        dmYunbiji.setId(id);
        dmYunbijiService.delete(dmYunbiji);
        return MobileResult.ok(MobileUtils.STATUS_1043, "");

    }


}
