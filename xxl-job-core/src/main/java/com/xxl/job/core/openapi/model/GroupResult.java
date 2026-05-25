package com.xxl.job.core.openapi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Group result model for AdminBiz API
 *
 * @author gengchen
 * @since 2026/5/26 01:36
 */
public class GroupResult implements Serializable {
    private static final long serialVersionUID = 42L;

    private int          id;
    private String       appname;
    private String       title;
    private int          addressType;    // 0=自动注册，1=手动录入
    private String       addressList;    // 手动录入的地址列表（逗号分隔）
    private List<String> registryList;   // 在线节点列表（自动注册）
    private Date         updateTime;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAppname() { return appname; }
    public void setAppname(String appname) { this.appname = appname; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAddressType() { return addressType; }
    public void setAddressType(int addressType) { this.addressType = addressType; }

    public String getAddressList() { return addressList; }
    public void setAddressList(String addressList) { this.addressList = addressList; }

    public List<String> getRegistryList() { return registryList; }
    public void setRegistryList(List<String> registryList) { this.registryList = registryList; }

    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}
