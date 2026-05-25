package com.xxl.job.core.openapi.model;

import java.io.Serializable;

/**
 * Group query request model for AdminBiz API
 *
 * @author gengchen
 * @since 2026/5/26 01:36
 */
public class GroupRequest implements Serializable {
    private static final long serialVersionUID = 42L;

    private int    id;       // 按 id 精确查
    private String appname;  // 按 appname 模糊查
    private String title;    // 按 title 模糊查

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAppname() { return appname; }
    public void setAppname(String appname) { this.appname = appname; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
