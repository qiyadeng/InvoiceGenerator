package com.management.model;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 8:49 上午
 */
@TableName("province_template")
public class TaxRateDetail implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 篇
     */
    private String piece;
    /**
     * 类
     */
    private String category;
    /**
     * 章
     */
    private String  chapter;
    /**
     * 节
     */
    private String knob;
    /**
     * 条
     */
    private String strips;
    /**
     * 款
     */
    private String sincere;
    /**
     * 项
     */
    private String item;
    /**
     * 目
     */
    private String suborder;
    /**
     * 子目
     */
    private String subtitle;
    /**
     * 细目
     */
    private String detail;
    /**
     * 合并编码
     */
    private String mergeCode;
    /**
     * 货物名称
     */
    private String descriptionGoods;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 说明
     */
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getKnob() {
        return knob;
    }

    public void setKnob(String knob) {
        this.knob = knob;
    }

    public String getStrips() {
        return strips;
    }

    public void setStrips(String strips) {
        this.strips = strips;
    }

    public String getSincere() {
        return sincere;
    }

    public void setSincere(String sincere) {
        this.sincere = sincere;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSuborder() {
        return suborder;
    }

    public void setSuborder(String suborder) {
        this.suborder = suborder;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMergeCode() {
        return mergeCode;
    }

    public void setMergeCode(String mergeCode) {
        this.mergeCode = mergeCode;
    }

    public String getDescriptionGoods() {
        return descriptionGoods;
    }

    public void setDescriptionGoods(String descriptionGoods) {
        this.descriptionGoods = descriptionGoods;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
