package org.example.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpUtil {


    /**
     * Http get请求
     *
     * @param httpUrl 连接
     * @return 响应数据
     */
    public static String doGet(String httpUrl) {
        long start = System.currentTimeMillis();
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(3000);
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String temp = null;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                    }
                }
            }
        }
        catch (SocketTimeoutException e) {
            log.error("SocketTimeoutException:",e);
        } catch (IOException e) {
            log.error("error:",e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("error:",e);
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("error:",e);
                }
            }
            if (connection != null) {
                //关闭远程连接
                connection.disconnect();
            }
        }
        long end = System.currentTimeMillis();
        log.info("HttpUtil.doGet 耗时：{}", end - start);
        return result.toString();
    }

    /**
     * Http post请求
     *
     * @param httpUrl 连接
     * @param param   参数
     */
    public static String doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        long start = System.currentTimeMillis();
        try {
            //创建连接对象
            URL url = new URL(httpUrl);
            //创建连接
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方法
            connection.setRequestMethod("POST");
            //设置连接超时时间
            connection.setConnectTimeout(10);
            //设置读取超时时间
            connection.setReadTimeout(3000);
            //DoOutput设置是否向httpUrlConnection输出，DoInput设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //设置是否可读取
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            //拼装参数
            if (null != param && !param.isEmpty()) {
                //设置参数
                os = connection.getOutputStream();
                //拼装参数
                os.write(param.getBytes(StandardCharsets.UTF_8));
            }
            //读取响应
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String temp;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                        result.append("\r\n");
                    }
                }
            }
            log.info("HttpUtil.doPost 耗时：{}", System.currentTimeMillis() - start);
        }  catch (SocketTimeoutException e) {
            log.info("HttpUtil.doPost 耗时：{}", System.currentTimeMillis() - start);
            log.error("SocketTimeoutException:",e);
        } catch (IOException e) {
            log.error("error:",e);
        } finally {
            log.info("HttpUtil.doPost 耗时：{}", System.currentTimeMillis() - start);
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("error:",e);
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("error:",e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("error:",e);
                }
            }
            if (connection != null) {
                //关闭连接
                connection.disconnect();
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String result = doPost("http://localhost:8888/server", null);
//        String result2 = doPost("http://localhost:8888/server", null);
//        String result3 = doPost("http://localhost:8888/server", null);
        System.out.println(result);
    }

}
