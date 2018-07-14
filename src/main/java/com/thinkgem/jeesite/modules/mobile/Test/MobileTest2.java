package com.thinkgem.jeesite.modules.mobile.Test;


import net.sf.json.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *接口测试
 * @author 刘智科
 * @version 2018-5-16
 */

public class MobileTest2  {
    /**
     * 测试上传头像
     */
    @Test
      public void uploadHeadPortrait() {
        String url = "http://127.0.0.1:8080/loginBefore/uploadYunBiJiFromByte";
          HttpPost httpPost = new HttpPost(url);
          CloseableHttpClient client = HttpClients.createDefault();
        String fileUrl = "C:/Users/007/Desktop/20180608165800_653.jpg";
        String fileUrl2 = "C:/Users/007/Desktop/20180619115231_496.jpg";
          String boundary = "-------------------------7e020233150564";//编节符
          String prefix = "--";//前缀 上传时需要多出两个-- 一定需要注意！！！
          String end = "\r\n";//这里也需要注意，在html协议中，用 “/r/n” 换行，而不是 “/n”。
          try {
              URL http = new URL(url);
              HttpURLConnection conn = (HttpURLConnection) http.openConnection();
              conn.setRequestMethod("POST");
              conn.setReadTimeout(5000);
              conn.setDoInput(true);//准许向服务器读数据
              conn.setDoOutput(true);//准许向服务器写入数据

              /*设置向服务器上传数据的请求方式  默认的是表单形式
               * 通过Content-Type协议向服务器上传数据
               * boundary
               * */
              conn.setRequestProperty("token", "8118aa950f174f8888a0cbc235453c6e");
              conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

              //创建一个输出流对象，
              DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            /*
            *
              -----------------------------7e020233150564
              Content-Disposition: form-data; name="file"; filename="I:\迅雷下载\18fb1f51c9eb63489cce9e029154782e.jpg"
              Content-Type: image/jpeg
                                        //这里是空一行  需要注意
              <二进制文件数据未显示>
              ---------------------------7e020233150564--
              */
              //向服务器写入数据  这里就需要完全根据以上协议格式来写，需要仔细，避免出错。
              out.writeBytes(prefix + boundary + end);//这是第一行  并回车换行
              //这是第二行，文件名和对应服务器的
              out.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"image1\"" + end);//这是第二行
              out.writeBytes(end);//空一行
              //以下写入图片
              FileInputStream fileInputStream = new FileInputStream(new File(fileUrl));
              byte[] b = new byte[1024 * 4];//缓冲区
              int len;
              //循环读数据
              while ((len = fileInputStream.read(b)) != -1) {
                  out.write(b, 0, len);
              }
              //写完数据后 回车换行
              out.writeBytes(end);
              out.writeBytes(prefix + boundary + end);//这是第一行  并回车换行
              //这是第二行，文件名和对应服务器的
              Base64Utils.decodeFromString("");
              out.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + new String("你好".getBytes("gbk"), "iso-8859-1") + "\"" + end);//这是第二行
              out.writeBytes(end);//空一行
              //以下写入图片
              FileInputStream fileInputStream2 = new FileInputStream(new File(fileUrl2));
              byte[] b2 = new byte[1024 * 4];//缓冲区
              int len2;
              //循环读数据
              while ((len2 = fileInputStream2.read(b2)) != -1) {
                  out.write(b2, 0, len2);
              }
              //写完数据后 回车换行
              out.writeBytes(end);
              out.writeBytes(prefix + boundary + prefix + end);
              out.flush();//清空

              //创建一个输入流对象  获取返回的信息  是否上传成功
              BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              StringBuffer sb = new StringBuffer();
              String str;
              while ((str = reader.readLine()) != null) {
                  sb.append(str);
              }
              //关闭流信息
              if (out != null) out.close();
              if (reader != null) reader.close();

              System.out.print("返回结果：" + sb.toString());
          } catch (MalformedURLException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }

    /**
     * 测试获取头像
     */
    @Test
    public void getHeadPortrait(){
        String url = "http://localhost:8080/yunbiji/getHeadPortrait";
        try {
            URL http = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) http.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setDoInput(true);//准许向服务器读数据
            conn.setDoOutput(true);//准许向服务器写入数据
            conn.setRequestProperty("token", "0434dbe95a8041bdba36a06d406c39d1");
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            //关闭流信息
            if (out != null) out.close();
            if (reader != null) reader.close();

            System.out.print("返回结果：" + sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试获取区域
     */
    @Test
    public void getDmCountry(){
    /*    JSONObject jsonParam = new JSONObject();
        9         jsonParam.put("name", "admin");
        10         jsonParam.put("pass", "123456");
        11         StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
        12         entity.setContentEncoding("UTF-8");
        13         entity.setContentType("application/json");
        14         httpPost.setEntity(entity);*/
        String url = "http://localhost:8080/loginBefore/getDmCountry?type=1";
        try {

            URL http = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) http.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setDoInput(true);//准许向服务器读数据
            conn.setDoOutput(true);//准许向服务器写入数据
            conn.setRequestProperty("token", "0434dbe95a8041bdba36a06d406c39d1");
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            //关闭流信息
            if (out != null) out.close();
            if (reader != null) reader.close();

            System.out.print("返回结果：" + sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试redis
     */
    @Test
    public void testRedis() {

        List<String> list = new ArrayList<String>();
        list.add("1111");
        list.add("2222");
        String ss = JSONArray.fromObject(list).toString();
        System.out.println(JSONArray.fromObject(list));
        JSONArray jsonArray = JSONArray.fromObject(ss);
        for (Object sss : jsonArray) {
            System.out.println(sss);
        }
        System.out.println(jsonArray);

    }

    @Test
    public void testZiFu() throws UnsupportedEncodingException {

        String encodeStr = DigestUtils.md5Hex("12223344" + "abc");
        System.out.println("MD5加密后的字符串为:encodeStr=" + encodeStr);




    }
}
