/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.thinkgem.jeesite.modules.mobile.utils;

import java.io.Serializable;

/**
 * 移动端同一返回值
 */
public class MobileResult implements Serializable {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 6693856593396045276L;

    /**
     * 查询结果
     */
    private String            data;
    /**
     * 状态码
     */
    private int            status;
    /**
     * 成功与否
     */
    private boolean           isSuccess;

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
     * 获取 isSuccess
     *
     * @return noSuccess
     */
    public boolean getIsSuccess() {
        return isSuccess;
    }

    /**
     * 设置 noSuccess
     *
     * @param isSuccess
     */
    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static MobileResult ok(String data,Object resultData){
        MobileResult mobileResult = new MobileResult();
        mobileResult.setIsSuccess(true);
        mobileResult.setStatus(200);
        mobileResult.setData(data);
        mobileResult.setResultData(resultData);
        return mobileResult;
    }

    public static MobileResult error(int status,String data){
        MobileResult mobileResult = new MobileResult();
        mobileResult.setIsSuccess(false);
        mobileResult.setStatus(status);
        mobileResult.setResultData(new MobileResultNull());
        mobileResult.setData(data);
        return mobileResult;
    }

    public static MobileResult exception(String data){
        MobileResult mobileResult = new MobileResult();
        mobileResult.setIsSuccess(false);
        mobileResult.setStatus(500);
        mobileResult.setData("系统异常："+data);
        mobileResult.setResultData(new MobileResultNull());
        return mobileResult;
    }

}