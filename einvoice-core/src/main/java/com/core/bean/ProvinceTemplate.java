package com.core.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Effort
 * @description pdf模板
 * @date 2020/9/25 4:45 下午
 */
@Entity
@Table(name = "province_template")
public class ProvinceTemplate{

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 省份
     */
    @Column
    private String province;
    @Column
    private String uuid;
    /**
     * pdf模板
     */
    @Column
    private String templateUrl;
    /**
     *  创建时间
     */
    @Column
    private Date createTime;
    /**
     * 更新时间
     */
    @Column
    private Date updateTime;
    /**
     * 状态：1正常 0 删除
     */
    @Column
    private int status = 1;

    public Long getId() {
        return id;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
