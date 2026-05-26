package com.xxl.job.core.openapi.model;

import java.io.Serializable;

/**
 * 执行器分组请求
 * <p></p>
 *
 * @author gengchen
 * @since 2026/5/26
 */
public class GroupRequest implements Serializable {

    private static final long serialVersionUID = 42L;

    /**
     * 主键
     */
    private int id;

    /**
     * 执行器名称
     */
    private String appname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
}
