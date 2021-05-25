package com.core.bean;

import javax.persistence.*;
import java.util.Date;
/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:16 上午
 */
@Entity
@Table(name = "result_xml")
public class ResultXml {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * uuid
     */
    @Column
    private String uuid;

    /**
     * 文件地址
     */
    @Column
    private String url;

    /**
     * 创建时间
     */
    @Column
    private Date createTime;

    /**
     * 状态：1正常 0 失效
     */
    @Column
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
