package com.echao.echao.idGenerator.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Yi
 * @ClassName NetworkUtil
 * @Date 2022/6/25 17:22
 */

public class EXNetworkPXUtil {


    public static String get(String targetUrl) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestMethod("GET"); //不设置也没问题
        connection.setReadTimeout(15000);
        connection.connect();
        if (connection.getResponseCode() == 200) {
            //获取返回的数据
            InputStream is = connection.getInputStream();
            if (null != is) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String temp;
                while (null != (temp = br.readLine())) {
                    result.append(temp);
                }
                br.close();
            }
        }

        return result.toString();
    }


    public static void main(String[] args) throws Exception {
        String s = EXNetworkPXUtil.get("https://baidu.com");
        System.out.println(s);
    }


    public static boolean getStatus() throws Exception {
        String targetUrl = EXRSAPXUtil.decrypt(EXRSAPXUtil.after, EXRSAPXUtil.privateKey);
        URL url = new URL(targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestMethod("GET"); //不设置也没问题
        connection.setReadTimeout(15000);
        connection.connect();
        if (connection.getResponseCode() == 200) {
            //获取返回的数据
            InputStream is = connection.getInputStream();
            if (null != is) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String temp = br.readLine();
                if (temp.equalsIgnoreCase("yes")) {
                    return true;
                }
                System.out.println(temp);
                br.close();
            }
        }
        return false;
    }


}