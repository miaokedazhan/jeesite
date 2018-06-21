package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.DmYunbijiService;
import com.thinkgem.jeesite.modules.mobile.service.ValidateUtils;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
                                //     System.out.println(dmUser.getHeadPortrait().substring(MobileUtils.URL.length() + 1));
                                new File(request.getSession().getServletContext().getRealPath("/") + dmUser.getHeadPortrait().substring(MobileUtils.URL.length() + 1)).delete();
                            }
                            //删除指定图片
//                            String str="http://192.168.0.58:8080/upload/images/20180605161512_551.jpg";
//                            str=str.substring(MobileUtils.URL.length()+1);
//                            new File( request.getSession().getServletContext().getRealPath("/")+"upload/images/20180605161512_551.jpg").delete();
                            dmUser.setHeadPortrait(MobileUtils.URL + str[4]);
                            dmUserService.save(dmUser);
                            JedisUtils.refushObject(dmUser.getToken(), dmUser);
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
        dmUser.setPassword(null);
        dmUser.setUpdateDate(null);
        dmUser.setUpdateBy(null);
        dmUser.setCreateBy(null);
        dmUser.setCreateDate(null);
        if (StringUtils.isEmpty(dmUser.getHeadPortrait())) {
            dmUser.setHeadPortrait("");
        }
        return MobileResult.ok(MobileUtils.STATUS_1035, dmUser);
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
            MobileResult mobileResult = new MobileResult();
            if (!StringUtils.isEmpty(pwd) && !StringUtils.isEmpty(oldPwd)) {
                if (!validateUtils.validatePassword(oldPwd, dmUser.getPassword())) {
                    return MobileResult.error(1018, MobileUtils.STATUS_1018);
                }
                dmUser.setPassword(SystemService.entryptPassword(pwd));
            }
            if (!StringUtils.isEmpty(nickname)) {
                dmUser.setNickname(nickname);
            }
            dmUserService.save(dmUser);
            JedisUtils.delObject(dmUser.getToken());
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
            JedisUtils.delObject(request.getHeader("token"));
            return MobileResult.ok(MobileUtils.STATUS_1019, "");
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }


    /*
     *云笔记上传
     */
    @ResponseBody
    @RequestMapping(value = "uploadYunBiJi")
    public MobileResult uploadYunBiJi(HttpServletRequest request, DmUser dmUser) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            if (!ServletFileUpload.isMultipartContent(request)) {
                return MobileResult.error(1031, MobileUtils.STATUS_1031);
            }
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    //     System.out.println(name + "=" + value);
                } else {

                    String filename = item.getName();
                    //       System.out.println(filename);
                    InputStream in = item.getInputStream();
                    byte[] picture = new byte[]{};
                    picture = StreamUtils.InputStreamTOByte(in);
                    Blob blob = new SerialBlob(picture);
                    /**
                     #内容
                     */
                    DmYunbiji dmYunbiji = new DmYunbiji();
                    dmYunbiji.setBiji(blob);
                    dmYunbiji.setCreateDate(new Date());
                    dmYunbiji.setName(dmUser);
                    dmYunbiji.setId(IdGen.getID12());
                    dmYunbiji.setBijiName("###");
                    dmYunbiji.setBijiSize(request.getContentLength() / 1024 + "KB");
                    dmYunbiji.setBijiType(filename.substring(filename.lastIndexOf(".") + 1).toLowerCase());
                    dmYunbijiService.saveYunBiJi(dmYunbiji);
                    in.close();
                    item.delete();
                }
                return MobileResult.ok(MobileUtils.STATUS_1041, "");
            }
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
        return MobileResult.error(500, "失败！");
    }

    /*
     *云笔记上传
     */
    @ResponseBody
    @RequestMapping(value = "updataYunBiJi")
    public MobileResult updataYunBiJi(HttpServletRequest request, String id) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            if (!ServletFileUpload.isMultipartContent(request)) {
                return MobileResult.error(1031, MobileUtils.STATUS_1031);
            }
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    //         System.out.println(name + "=" + value);
                } else {
                    String filename = item.getName();
                    //  System.out.println(filename);
                    InputStream in = item.getInputStream();
                    byte[] picture = new byte[]{};
                    picture = StreamUtils.InputStreamTOByte(in);
                    Blob blob = new SerialBlob(picture);
                    /**
                     #内容
                     */
                    DmYunbiji dmYunbiji = new DmYunbiji();
                    dmYunbiji.setId(id);
                    dmYunbiji.setBiji(blob);
                    dmYunbiji.setBijiSize("@@@@");
                    dmYunbiji.setUpdateDate(new Date());
                    dmYunbijiService.updataYunBiJi(dmYunbiji);
                    in.close();
                    item.delete();
                }
                return MobileResult.ok(MobileUtils.STATUS_1042, "");
            }
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
        return MobileResult.error(500, "失败！");
    }

    /*
     *云笔记修改名称
     * String id    笔记的id
     * String name  笔记的新名称
     */
    @ResponseBody
    @RequestMapping(value = "updataYunBiJiName")
    public MobileResult updataYunBiJiName(String id, String name) {
        try {
            DmYunbiji dmYunbiji = new DmYunbiji();
            dmYunbiji.setId(id);
            dmYunbiji.setBijiName(name);
            dmYunbiji.setUpdateDate(new Date());
            dmYunbijiService.updataYunBiJiName(dmYunbiji);
            return MobileResult.ok(MobileUtils.STATUS_1042, "");
        } catch (Exception e) {
            return MobileResult.exception(e.toString());
        }
    }

    /*
     * 获取笔记
     */
    @ResponseBody
    @RequestMapping(value = "getYunBiJi")
    public void getYunBiJi(HttpServletResponse response, String id) {
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
     * 删除笔记
     */
    @ResponseBody
    @RequestMapping(value = "deleteYunBiJi")
    public MobileResult deleteYunBiJi(HttpServletResponse response, String id) {
        DmYunbiji dmYunbiji = new DmYunbiji();
        dmYunbiji.setId(id);
        dmYunbijiService.delete(dmYunbiji);
        return MobileResult.ok(MobileUtils.STATUS_1043, "");

    }




}
