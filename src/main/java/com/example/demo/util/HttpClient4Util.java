package com.example.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HttpClient4Util {

    public static Object getResult(String url, String method, String resultTag, String namespace,
                                                List<Map<String,Object>> inputParams) throws Exception {

        //拼接请求体
        StringBuilder requestContent = new StringBuilder();
        requestContent.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\""+namespace+"\">");
        requestContent.append("<soapenv:Header/>");
        requestContent.append("<soapenv:Body>");
        requestContent.append("<web:"+method+">");
        if(inputParams!=null) {
            for(int i=0;i<inputParams.size();i++) {
                String paramName = (String)inputParams.get(i).get("paramName");
                String paramValue = (String)inputParams.get(i).get("paramValue");
                requestContent.append("<web:"+paramName+">"+paramValue+"</web:"+paramName+">");
            }
        }
        requestContent.append("</web:"+method+">");
        requestContent.append("</soapenv:Body>");
        requestContent.append("</soapenv:Envelope>");
        //调用
        //创建请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置超时
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30*1000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(30*1000)// 设置连接请求超时时间
                .setSocketTimeout(30*1000)// 设置读取数据连接超时时间
                .build();
        httpPost.setConfig(requestConfig);
        //设置请求头
        httpPost.addHeader("Content-Type", "text/xml;charset=UTF-8");
        //封装post请求参数
        httpPost.setEntity(new StringEntity(requestContent.toString(), "UTF-8"));
        CloseableHttpResponse httpResponse = null;
        String result = null;
        try {
            //httpClient对象执行post请求，并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
                }
            }
        }
        //获取结果
        Document doc = DocumentHelper.parseText(result);
        Element root = doc.getRootElement();
        return root.element("Body").element(method+"Response").element(resultTag);
    }

}
