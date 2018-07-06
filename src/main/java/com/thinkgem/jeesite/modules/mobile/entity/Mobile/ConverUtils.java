package com.thinkgem.jeesite.modules.mobile.entity.Mobile;

import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘智科
 * @Title: $file_name
 * @Package $package_name
 * @Description: $todo
 */
public class ConverUtils {

    public static List<FileBean> yunbijiListToBeanList(List<DmYunbiji> dmYunbijis) {
        List<FileBean> fileBeanList = new ArrayList<FileBean>();
        FileBean fileBean = new FileBean();
        for (DmYunbiji dmYunbiji : dmYunbijis) {
            fileBean.setId(dmYunbiji.getId());
            fileBean.setFileurl(dmYunbiji.getBiji());
            fileBean.setImageurl(dmYunbiji.getBijiImage());
            fileBean.setFilename(dmYunbiji.getBijiName());
            fileBean.setFilesize(dmYunbiji.getBijiSize());
        }
        return fileBeanList;
    }

    public static FileBean yunbijiToBean(DmYunbiji dmYunbiji) {
        FileBean fileBean = new FileBean();
        fileBean.setId(dmYunbiji.getId());
        fileBean.setFileurl(dmYunbiji.getBiji());
        fileBean.setImageurl(dmYunbiji.getBijiImage());
        fileBean.setFilename(dmYunbiji.getBijiName());
        fileBean.setFilesize(dmYunbiji.getBijiSize());
        return fileBean;
    }
}
