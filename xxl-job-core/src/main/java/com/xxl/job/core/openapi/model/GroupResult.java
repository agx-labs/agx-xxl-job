package com.xxl.job.core.openapi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分组信息结果
 * <p>包含分组的基本信息和在线节点列表</p>
 * <p></p>
 *
 * @author gengchen
 * @since 2026/5/26
 */
public class GroupResult implements Serializable {
    
    private static final long serialVersionUID = 42L;
    /**
     * 主键
     */
    private int id;
    /**
     * 执行器名称
     */
    private String appname;
    /**
     * 标题
     */
    private String title;
    /**
     * 注册类型
     * <p>0=自动注册，1=手动录入</p>
     */
    private int addressType;
    /**
     * 注册地址列表
     * <p>手动录入的地址列表（逗号分隔）</p>
     */
    private String addressList;
    /**
     * 在线节点列表
     * <p>在线节点列表（自动注册）</p>
     */
    private List<String> registryList;
    /**
     * 更新时间
     */
    private Date updateTime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAddressType() {
        return addressType;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    public String getAddressList() {
        return addressList;
    }

    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }

    public List<String> getRegistryList() {
        return registryList;
    }

    public void setRegistryList(List<String> registryList) {
        this.registryList = registryList;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
