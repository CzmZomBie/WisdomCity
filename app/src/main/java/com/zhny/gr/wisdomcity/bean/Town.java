package com.zhny.gr.wisdomcity.bean;

/**
 * Created by tong on 2017/5/8.
 */

public class Town {
    private String id;
    private String name;
    private String superior_id;
    private String image;

    private String ipc_name;
    private String ipc_id;
    private String url_auto;
    private String screenshot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperior_id() {
        return superior_id;
    }

    public void setSuperior_id(String superior_id) {
        this.superior_id = superior_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIpc_name() {
        return ipc_name;
    }

    public void setIpc_name(String ipc_name) {
        this.ipc_name = ipc_name;
    }

    public String getIpc_id() {
        return ipc_id;
    }

    public void setIpc_id(String ipc_id) {
        this.ipc_id = ipc_id;
    }

    public String getUrl_auto() {
        return url_auto;
    }

    public void setUrl_auto(String url_auto) {
        this.url_auto = url_auto;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }
}