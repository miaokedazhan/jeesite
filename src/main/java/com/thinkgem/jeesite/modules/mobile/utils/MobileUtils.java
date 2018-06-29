package com.thinkgem.jeesite.modules.mobile.utils;


/**
 * 移动端常量配置
 */
public class MobileUtils {


    public static  final String signNameSIGN_NAME="好写科技";
    public static  final String TEMPLATE_CODE_CHINESE="SMS_134323861";
    public static  final String TEMPLATE_CODE_INTERNATIONAL="SMS_134328890";

    /*
     * 发布地址
     * */
    public static  final String URL="http://192.168.0.58:8080";

    /*
    * token过期时间
    * 单位：天
    * */
    public static  final int Export_TIME=7;
   public static final int Redis_Export_TIME = 1 * 60 * 60 * 24 * 7;
   //public static  final int Redis_Export_TIME=60;

    /*
     * token过期时间
     * 单位：分钟
     * */
    public static  final int CODE_Export_TIME=5;
   public static final int REDIS_CODE_Export_TIME = 1 * 60 * 5;
    /**
     * 请求状态码
     */
    public static  final String STATUS_200="请求成功！";
    public static  final String STATUS_1001="用户名或密码为空！";
    public static  final String STATUS_1002="用户已存在！";
    public static  final String STATUS_1003="保存用户成功！";
    public static  final String STATUS_1004="用户不存在！";
    public static  final String STATUS_1005="用户名或密码不正确！";
    public static  final String STATUS_1006="登录成功！";
    public static  final String STATUS_1007="验证码为空！";
    public static  final String STATUS_1008="验证码不正确！";
    public static  final String STATUS_1009="获取验证码成功！";
    public static  final String STATUS_1010="请重新登录！";
    public static  final String STATUS_1011="用户token过期了！";
    public static  final String STATUS_1012="服务器出错！";
    public static  final String STATUS_1013="手机号为空！";
    public static final String STATUS_1015 = "修改个人信息成功！";
    public static  final String STATUS_1016="验证码已过期！";
    public static  final String STATUS_1017="密码为空！";
    public static  final String STATUS_1018="原始密码不正确！";
    public static  final String STATUS_1019="退出登陆成功！";
    public static  final String STATUS_1020="账号已从别处登录！";
    public static  final String STATUS_1021="请先获取验证码！";
    public static  final String STATUS_1022="密码不一致！";
    public static  final String STATUS_1023="参数错误！";
    public static  final String STATUS_1024="修改用户信息成功！";
    public static  final String STATUS_1025="获取区域列表成功！";
    public static  final String STATUS_1026="获取验证码失败！";
    public static  final String STATUS_1027="获取验证码异常，请重新获取！";
    public static  final String STATUS_1028="验证码已发送，请稍后再试！";
    public static  final String STATUS_1029="非法手机号！";
    public static  final String STATUS_1030="保存头像成功！";
    public static  final String STATUS_1031="文件上传形式错误method=\"post\"enctype=\"multipart/form-data\"！";
    public static  final String STATUS_1032="删除头像成功！";
    public static  final String STATUS_1033="上传头像系统错误！";
    public static  final String STATUS_1034="上传头像文件错误！";
    public static final String STATUS_1035 = "获取个人信息成功！";
    public static  final String STATUS_1036="还没有头像，请上传！";
    public static final String STATUS_1040 = "请获取验证码！";
    public static final String STATUS_1041 = "笔记上传成功！";
    public static final String STATUS_1042 = "笔记修改成功！";
    public static final String STATUS_1043 = "笔记删除成功！";
 public static final String STATUS_1044 = "获取笔记成功！";
}

