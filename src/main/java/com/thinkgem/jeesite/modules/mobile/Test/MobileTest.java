package com.thinkgem.jeesite.modules.mobile.Test;


import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mobile.entity.DmCountry;
import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.service.DmCountryService;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *增加一个添加区号的方法
 * @author 刘智科
 * @version 2018-1-16
 */
@Controller
@RequestMapping(value = "/mobileTest")
public class MobileTest extends BaseController {

    @Autowired
    private DmCountryService dmCountryService;

    /**
     * 测试redis
     */
    @RequestMapping("testRedis")
    @ResponseBody
    public MobileResult testRedis() {
        DmUser dmUser = new DmUser();
        dmUser.setPassword("122121212");
        dmUser.setHeadPortrait("12133");
        dmUser.setHeadPortrait("1123");
        JedisUtils.delObject("key");
        JedisUtils.setObject("key", dmUser, 10000);
        DmUser dmUser2 = (DmUser) JedisUtils.getObject("key1");
        return MobileResult.ok(",", dmUser2);
    }
    
    /**
     * 开发环境添加区号
     *
     */
    @RequestMapping("addCode")
    @ResponseBody
    public MobileResult addHeadPortrait(HttpServletRequest request) throws Exception {
        HSSFWorkbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream("C:\\Users\\007\\Desktop\\区号.xls");
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            HSSFSheet hssfSheet = workbook.getSheetAt(0);
            if (hssfSheet == null) {
                return MobileResult.error(11,"");
            }
            // 循环行
            for (int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                DmCountry dmCountry = new DmCountry();
                HSSFCell cell = hssfRow.getCell(0);
                if (cell == null) {
                    continue;
                }
                hssfRow.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                dmCountry.setMobilePrefix(cell.getStringCellValue());
                cell = hssfRow.getCell(1);
                if (cell == null) {
                    continue;
                }
                hssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                dmCountry.setChinese(cell.getStringCellValue());

                cell = hssfRow.getCell(2);
                if (cell == null) {
                    continue;
                }
                hssfRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                dmCountry.setEnglish(cell.getStringCellValue());

                cell = hssfRow.getCell(3);
                if (cell == null) {
                    continue;
                }
                hssfRow.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                dmCountry.setJapanese(cell.getStringCellValue());

                cell = hssfRow.getCell(4);
                if (cell == null) {
                    continue;
                }
                hssfRow.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                dmCountry.setInitial(cell.getStringCellValue());
                dmCountryService.save(dmCountry);
            }
        return  MobileResult.ok(1033+"","");
    }

}
