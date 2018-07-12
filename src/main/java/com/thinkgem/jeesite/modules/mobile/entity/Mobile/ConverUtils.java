package com.thinkgem.jeesite.modules.mobile.entity.Mobile;

import com.thinkgem.jeesite.modules.mobile.entity.DmUser;
import com.thinkgem.jeesite.modules.mobile.entity.DmYunbiji;
import com.thinkgem.jeesite.modules.mobile.utils.EmojiUtil;

import java.io.UnsupportedEncodingException;
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
        for (DmYunbiji dmYunbiji : dmYunbijis) {
            FileBean fileBean = new FileBean();
            fileBean.setId(dmYunbiji.getId());
            fileBean.setFileurl(dmYunbiji.getBiji());
            fileBean.setImageurl(dmYunbiji.getBijiImage());
            fileBean.setFilename(dmYunbiji.getBijiName());
            fileBean.setFilesize(dmYunbiji.getBijiSize());
            fileBeanList.add(fileBean);
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

    public static List<DmYunbiji> dmYunbijiEmoji(List<DmYunbiji> dmYunbijis) throws UnsupportedEncodingException {
        List<DmYunbiji> dmYunbijiList = new ArrayList<DmYunbiji>();
        for (DmYunbiji dmYunbiji : dmYunbijis) {
            dmYunbiji.getName().setNickname(EmojiUtil.emojiRecovery2(dmYunbiji.getName().getNickname()));
            dmYunbijiList.add(dmYunbiji);
        }
        return dmYunbijiList;
    }

    public static List<DmUser> dmUserEmoji(List<DmUser> dmYunbijis) throws UnsupportedEncodingException {
        List<DmUser> dmUserList = new ArrayList<DmUser>();
        for (DmUser dmUser : dmYunbijis) {
            dmUser.setNickname(EmojiUtil.emojiRecovery2(dmUser.getNickname()));
            dmUserList.add(dmUser);
        }
        return dmUserList;
    }


    public static DmYunbiji dmYunbijiToNull(DmYunbiji dmYunbiji) {
        dmYunbiji.setBijiImage(null);
        dmYunbiji.setBiji(null);
        dmYunbiji.setId(null);
        dmYunbiji.setBijiName(null);
        dmYunbiji.setBijiType(null);
        dmYunbiji.setBijiSize(null);
        return dmYunbiji;
    }


}
