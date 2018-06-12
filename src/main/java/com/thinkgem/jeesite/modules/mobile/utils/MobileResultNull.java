/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.thinkgem.jeesite.modules.mobile.utils;

import java.io.Serializable;

/**
 * 返回空对象 解决移动端多次解析问题,暂时解决，随后修改
 */
public class MobileResultNull implements Serializable {


    /**
     * 序列id
     */
    private static final long serialVersionUID = 6693856593396045276L;

    /**
     * 查询结果
     */
    private String            data;

    /**
     * 查询数据
     */
    private Object            resultData;

    /**
     * 获取 data
     *
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * 设置 data
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 获取数据
     *
     * @return
     */
    public Object getResultData() {
        return resultData;
    }

    /**
     * 设置数据
     *
     * @param resultData
     */
    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }






}