package com.management.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 *
 * 用户
 *
 */
public class User implements Serializable {
	/** 主键id */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 登陆名 */
	@TableField(value = "login_name")
	private String loginName;

	/** 用户名 */
	private String name;

	/** 密码 */
	private String password;

	/** 性别 */
	private Integer sex;

	/** 年龄 */
	private Integer age;

	/** 手机号 */
	private String phone;

	/** 用户类别 */
	@TableField(value = "user_type")
	private Integer userType;

	/** 用户状态 */
	private Integer status;

	/** 所属机构 */
	@TableField(value = "organization_id")
	private Integer organizationId;

	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 商户编码
	 */
	private String merchant_code;
	/**
	 * 商户名称
	 */
	private String merchant_name;
	/**
	 * 应用号
	 */
	private String app_no;
	/**
	 * MD5加密所需的KEY
	 */
	private String md5key;
	/**
	 * MD5加密所需的KEY
	 */
	private String wechat_id;
	private String ad;
	/**
	 * 纳税人名称
	 */
	private String tax_merchant_name;
	/**
	 * 纳税人编号
	 */
	private String tax_nsrsbh;
	/**
	 * 纳税人银行账号
	 */
	private String tax_yhzh;
	/**
	 *银行开户地址和账号
	 */
	private String tax_dzdh;
	private String tax_spbm;
	/**
	 * 税率
	 */
	private String tax_sl;
	/**
	 * 省份
	 */
	private String tax_shengfen;
	/**
	 * 发票模板地址
	 */
	private String tax_template_url;
	/**
	 * 审核人
	 */
	private String tax_shenheren;
	/**
	 * 版本
	 */
	private String bmbbbh;
	/**
	 * 商品名称
	 */
	private String tax_spmc;
	/**
	 * 商品单位
	 */
	private String tax_spdw;
	private String tax_rate_id;
	private Long tax_rate_detail_id;
	private Long einvoice_cachet_id;//公章Id
	private String appid;
	private String app_secret;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getApp_secret() {
		return app_secret;
	}

	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}

	private Long template_id;

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

	public Long getTax_rate_detail_id() {
		return tax_rate_detail_id;
	}

	public void setTax_rate_detail_id(Long tax_rate_detail_id) {
		this.tax_rate_detail_id = tax_rate_detail_id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getTax_rate_id() {
		return tax_rate_id;
	}

	public void setTax_rate_id(String tax_rate_id) {
		this.tax_rate_id = tax_rate_id;
	}
}
