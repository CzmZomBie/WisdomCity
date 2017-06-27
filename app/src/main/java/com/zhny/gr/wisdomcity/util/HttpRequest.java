package com.zhny.gr.wisdomcity.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    /**
     * 获得乡镇数据
     */
    public static String getTownData(String token) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(NetWorkPort.GET_TOWN_LIST);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 设置文件类型
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("User-Agent", "okhttp/3.3.1");

            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                return InputStreamTOString(is);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 获得乡村 数据
     */
    public static String getVillageData(String token, String superior_id) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(NetWorkPort.GET_VILLAGE_LIST + "superior_id=" + superior_id + "&limit=1000");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 设置文件类型
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("User-Agent", "okhttp/3.3.1");

            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                return InputStreamTOString(is);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 获得实时监控 数据
     */
    public static String getMonitorData(String token, String superior_id) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(NetWorkPort.GET_MONITOR_LIST + superior_id);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 设置文件类型
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("User-Agent", "okhttp/3.3.1");

            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                return InputStreamTOString(is);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 获得回看 数据
     */
    public static String getRecordData(String token, String ipc_id) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(NetWorkPort.GET_RECORD_LIST + ipc_id+"/record/");
            Log.i("MyTAG","url    "+NetWorkPort.GET_RECORD_LIST + ipc_id+"/record/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 设置文件类型
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("User-Agent", "okhttp/3.3.1");

            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                return InputStreamTOString(is);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 将一个输入流转换成指定编码的字符串
     *
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputStream(InputStream inputStream, String encode) {
        // 内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = null;
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                result = new String(byteArrayOutputStream.toByteArray(), encode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String InputStreamTOString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count = -1;
        while ((count = in.read(data, 0, 4096)) != -1) {
            outStream.write(data, 0, count);
        }
        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }
}
