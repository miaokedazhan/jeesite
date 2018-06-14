package com.thinkgem.jeesite.test;


import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;

/**
 * 云笔记test测试类
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-*.xml", "classpath:mybatis-*.xml"})
public class YunBiJiControllerTest extends BaseController {

    @Autowired
    private DmUserService dmUserService;

    @Test
    @Transactional
    @Rollback(false)
    public void imgageUploadTest() throws Exception  {

        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();

        request.setMethod("POST");

        request.setContentType("multipart/form-data");

        request.addHeader("Content-type", "multipart/form-data");

        FileInputStream fis = new FileInputStream("C:/Users/Administrator/Desktop/数据导入_非正常户信息_20170414.xls");

        MockMultipartFile mfile = new MockMultipartFile("C:/Users/Administrator/Desktop", "数据导入_非正常户信息_20170414.xls", "application/vnd_ms-excel", fis);



    }
}
