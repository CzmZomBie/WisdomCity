package com.zhny.gr.wisdomcity.util;

/**
 * Created by czm on 2017/5/2.
 */

public class NetWorkPort {

    public static final String HTTP = "http://";

    public static final String IP_PORT = "192.168.0.55:8080";

    public static final String IP_PORT2 = "192.168.0.251:8080/";

    public static final String IP_PORT3 = "124.160.43.91:11380/";

    //作物信息 channelIds 园区选择 count 返回条数 first=???
    public static final String CROP_MESSAGE_URL = HTTP + IP_PORT + "/api/content/list.jspx";


    public static final String COLUMN_URL = HTTP + IP_PORT2 + "api/channel/list.jspx";

    public static final String CONTENT_LIST_URL = HTTP + IP_PORT2 + "api/content/list.jspx";

    public static final String CONTENT_URL = HTTP + IP_PORT2 + "api/content/get.jspx";

    public static final String TOTAL_COUNT = HTTP + IP_PORT2 + "api/content/getTotal.jspx";


    public static final String LOGIN_URLS = HTTP + IP_PORT3 + "account/api/login/";

    public static final String GET_TOWN_LIST = HTTP + IP_PORT3 + "asset/api/town/?limit=1000";

    public static final String GET_VILLAGE_LIST = HTTP + IP_PORT3 + "asset/api/village/?";

    public static final String GET_MONITOR_LIST = HTTP + IP_PORT3 + "monitoring/api/real-time-video/?limit=1000&village_id=";

    public static final String GET_RECORD_LIST = HTTP + IP_PORT3 + "/monitoring/api/ip-camera/";

}
