package com.thinkgem.jeesite.modules.mobile.web.mobile;


import com.thinkgem.jeesite.common.utils.UploadUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.service.*;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import com.thinkgem.jeesite.modules.mobile.utils.MobileUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import oracle.sql.BLOB;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 移动端登录之后（有token）Controller
 *
 * @author 刘智科
 * @version 2018-1-16
 */
@Controller
@RequestMapping(value = "/loginBefore")
public class UpLoadController extends BaseController {



    /*
     * 上传头像
     *
     */
    @RequestMapping("uploadHeadPortrait")
    @ResponseBody
    public MobileResult addHeadPortrait(HttpServletRequest request, HttpServletResponse response) {

        return  MobileResult.ok("上传成功","");
    }



    /*
     * 上传头像
     *
     */
    @RequestMapping("uploadHeadPortrait2")
    @ResponseBody
    public void addHeadPortrait2 (HttpServletRequest request, HttpServletResponse response) {


    }

    private byte [] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bAOutputStream = new ByteArrayOutputStream();
        int ch;
        while((ch = is.read() ) != -1){
            bAOutputStream.write(ch);
        }
        byte data [] =bAOutputStream.toByteArray();
        bAOutputStream.close();
        return data;
    }




}
