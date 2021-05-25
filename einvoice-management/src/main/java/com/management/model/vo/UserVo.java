package com.management.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.management.model.Role;

public class UserVo implements Serializable {
    private Long id;

    private String loginName;

    private String name;

    @JsonIgnore
    private String password;

    private Integer sex;

    private Integer age;

    private Integer userType;

    private Integer status;

    private Integer organizationId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String phone;

    private List<Role> rolesList;

    private String organizationName;

    private String roleIds;

    private Date createdateStart;
    private Date createdateEnd;

    private String email;// 邮箱

    private String merchant_code;//商户编码

    private String merchant_name;//商户名称

    private String app_no;//应用号

    private String md5key;//MD5加密所需的KEY

    private String wechat_id;
    private String ad;
    private String tax_merchant_name;
    private String tax_nsrsbh;
    private String tax_yh;
    private String tax_zh;
    private String tax_dz;
    private String tax_dh;
    private String tax_yhzh;
    private String tax_dzdh;




    private String tax_spbm;
    private String tax_sl;
    private String tax_shengfen;
    private String tax_template_url;
    private String tax_shenheren;
    private Long tax_rate_id;
    private String bmbbbh;
    private String tax_spmc;//商品名称（发票上默认）
    private String tax_spdw;//商品单位（发票上默认）
    private Long tax_rate_detail_id;
    private Long einvoice_cachet_id;//公章Id
    //模板文件表Id
    private Long template_id;

    public String getTax_yhzh() {
        return tax_yhzh;
    }

    public void setTax_yhzh(String tax_yhzh) {
        this.tax_yhzh = tax_yhzh;
    }

    public String getTax_dzdh() {
        return tax_dzdh;
    }

    public void setTax_dzdh(String tax_dzdh) {
        this.tax_dzdh = tax_dzdh;
    }

    public Long getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Long template_id) {
        this.template_id = template_id;
    }

    public Long getEinvoice_cachet_id() {
        return einvoice_cachet_id;
    }

    public void setEinvoice_cachet_id(Long einvoice_cachet_id) {
        this.einvoice_cachet_id = einvoice_cachet_id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getTax_rate_detail_id() {
        return tax_rate_detail_id;
    }

    public void setTax_rate_detail_id(Long tax_rate_detail_id) {
        this.tax_rate_detail_id = tax_rate_detail_id;
    }

    public Long getTax_rate_id() {
        return tax_rate_id;
    }

    public void setTax_rate_id(Long tax_rate_id) {
        this.tax_rate_id = tax_rate_id;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public List<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Role> rolesList) {
        this.rolesList = rolesList;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Date getCreatedateStart() {
        return createdateStart;
    }

    public void setCreatedateStart(Date createdateStart) {
        this.createdateStart = createdateStart;
    }

    public Date getCreatedateEnd() {
        return createdateEnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getApp_no() {
        return app_no;
    }

    public void setApp_no(String app_no) {
        this.app_no = app_no;
    }

    public String getMd5key() {
        return md5key;
    }

    public void setMd5key(String md5key) {
        this.md5key = md5key;
    }

    public void setCreatedateEnd(Date createdateEnd) {
        this.createdateEnd = createdateEnd;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTax_merchant_name() {
        return tax_merchant_name;
    }

    public void setTax_merchant_name(String tax_merchant_name) {
        this.tax_merchant_name = tax_merchant_name;
    }

    public String getTax_nsrsbh() {
        return tax_nsrsbh;
    }

    public void setTax_nsrsbh(String tax_nsrsbh) {
        this.tax_nsrsbh = tax_nsrsbh;
    }

    public String getTax_yh() {
        return tax_yh;
    }

    public void setTax_yh(String tax_yh) {
        this.tax_yh = tax_yh;
    }

    public String getTax_zh() {
        return tax_zh;
    }

    public void setTax_zh(String tax_zh) {
        this.tax_zh = tax_zh;
    }

    public String getTax_dz() {
        return tax_dz;
    }

    public void setTax_dz(String tax_dz) {
        this.tax_dz = tax_dz;
    }

    public String getTax_dh() {
        return tax_dh;
    }

    public void setTax_dh(String tax_dh) {
        this.tax_dh = tax_dh;
    }

    public String getTax_spbm() {
        return tax_spbm;
    }

    public void setTax_spbm(String tax_spbm) {
        this.tax_spbm = tax_spbm;
    }

    public String getTax_sl() {
        return tax_sl;
    }

    public void setTax_sl(String tax_sl) {
        this.tax_sl = tax_sl;
    }

    public String getTax_shengfen() {
        return tax_shengfen;
    }

    public void setTax_shengfen(String tax_shengfen) {
        this.tax_shengfen = tax_shengfen;
    }

    public String getTax_template_url() {
        return tax_template_url;
    }

    public void setTax_template_url(String tax_template_url) {
        this.tax_template_url = tax_template_url;
    }

    public String getTax_shenheren() {
        return tax_shenheren;
    }

    public void setTax_shenheren(String tax_shenheren) {
        this.tax_shenheren = tax_shenheren;
    }

    public String getBmbbbh() {
        return bmbbbh;
    }

    public void setBmbbbh(String bmbbbh) {
        this.bmbbbh = bmbbbh;
    }

    public String getTax_spmc() {
        return tax_spmc;
    }

    public void setTax_spmc(String tax_spmc) {
        this.tax_spmc = tax_spmc;
    }

    public String getTax_spdw() {
        return tax_spdw;
    }

    public void setTax_spdw(String tax_spdw) {
        this.tax_spdw = tax_spdw;
    }

}