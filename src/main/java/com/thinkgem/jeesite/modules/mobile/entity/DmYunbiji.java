/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 云笔记数据Entity
 *
 * @author 刘智科
 * @version 2018-06-19
 */
public class DmYunbiji extends DataEntity<DmYunbiji> {

    private static final long serialVersionUID = 1L;
    private DmUser dmUser;        // 用户id
    private String bijiName;        // 笔记名称
    private String bijiSize;        // 笔记大小
    private String bijiType;        // 笔记类型
    private String biji; // 笔记内容
    private String bijiImage;// 笔记图片

    public DmYunbiji() {
        super();
    }

    public DmYunbiji(String id) {
        super(id);
    }

    @JsonIgnore
    public DmUser getName() {
        return dmUser;
    }

    public void setName(DmUser dmUser) {
        this.dmUser = dmUser;
    }

    @Length(min = 1, max = 32, message = "笔记名称长度必须介于 1 和 32 之间")
    public String getBijiName() {
        return bijiName;
    }

    public void setBijiName(String bijiName) {
        this.bijiName = bijiName;
    }

    @Length(min = 1, max = 32, message = "笔记大小长度必须介于 1 和 32 之间")
    public String getBijiSize() {
        return bijiSize;
    }

    public void setBijiSize(String bijiSize) {
        this.bijiSize = bijiSize;
    }

    @Length(min = 1, max = 100, message = "笔记类型长度必须介于 1 和 100 之间")
    public String getBijiType() {
        return bijiType;
    }

    public void setBijiType(String bijiType) {
        this.bijiType = bijiType;
    }

    public String getBiji() {
        return biji;
    }

    public void setBiji(String biji) {
        this.biji = biji;
    }

    public String getBijiImage() {
        return bijiImage;
    }

    public void setBijiImage(String bijiImage) {
        this.bijiImage = bijiImage;
    }
}