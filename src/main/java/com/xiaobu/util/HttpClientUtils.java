package com.xiaobu.util;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobu
 * @version JDK1.8.0_171
 * @date on  2018/12/10 10:40
 * @description V1.0 HttpClient工具类
 */
public class HttpClientUtils {
    /**
     * @author xiaobu
     * @date 2018/12/10 10:46
     * @param url 请求地址, map 数据类型, encoding 编码]
     * @return java.lang.String
     * @descprition  pots请求传输 形式数据形式访问
     * @version 1.0
     */
    public static String sendPostDataByMap(String url, Map<String, String> map, String encoding)  {
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 装填参数
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        CloseableHttpResponse response = getPostResponse(httpClient, httpPost);
        return getResult(response,encoding);
    }

   /**
    * @author xiaobu
    * @date 2018/12/10 10:45
    * @param url 请求笛子, json 请求数据类型, encoding编码
    * @return java.lang.String
    * @descprition  post请求传输json
    * @version 1.0
    * JSON.toJSONString(map) 将map对象转化为json字符串
    */
    public static String sendPostDataByJson(String url, String json, String encoding)  {
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding(encoding);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = getPostResponse(httpClient, httpPost);
        return getResult(response,encoding);
    }


    /**
     * @author xiaobu
     * @date 2018/12/10 11:17
     * @param url 访问地址, encoding 编码]
     * @return java.lang.String
     * @descprition  get方式请求
     * @version 1.0
     */
    public static  String sendGetData(String url, String encoding)  {
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/json");
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = getGetResponse(httpClient, httpGet);
        return getResult(response,encoding);
    }

    /**
     * @author xiaobu
     * @date 2018/12/10 11:18
     * @param httpClient , httpPost
     * @return org.apache.http.client.methods.CloseableHttpResponse
     * @descprition  获取response对象
     * @version 1.0
     */
    public static  CloseableHttpResponse getPostResponse(CloseableHttpClient httpClient,HttpPost httpPost){
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * @author xiaobu
     * @date 2018/12/10 11:18
     * @param httpClient , httpGET
     * @return org.apache.http.client.methods.CloseableHttpResponse
     * @descprition  获取response对象
     * @version 1.0
     */
    public static  CloseableHttpResponse getGetResponse(CloseableHttpClient httpClient,HttpGet httpGet){
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * @author xiaobu
     * @date 2018/12/10 11:18
     * @param response, encoding]
     * @return java.lang.String
     * @descprition  获取结果
     * @version 1.0
     */
    public static String getResult(CloseableHttpResponse response,String encoding){
        String result="";
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                result = EntityUtils.toString(response.getEntity(), encoding);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 释放链接
        try {
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}
