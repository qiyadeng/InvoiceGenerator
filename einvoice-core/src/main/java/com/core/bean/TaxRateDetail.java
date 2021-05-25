package com.core.bean;


import javax.persistence.*;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 8:49 上午
 */
@Entity
@Table(name = "tax_rate_detail")
public class TaxRateDetail{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 篇
     */
    @Column(name = "piece")
    private String piece;
    /**
     * 类
     */
    @Column(name = "category")
    private String category;
    /**
     * 章
     */
    @Column(name = "chapter")
    private String  chapter;
    /**
     * 节
     */
    @Column(name = "knob")
    private String knob;
    /**
     * 条
     */
    @Column(name = "strips")
    private String strips;
    /**
     * 款
     */
    @Column(name = "sincere")
    private String sincere;
    /**
     * 项
     */
    @Column(name = "item")
    private String item;
    /**
     * 目
     */
    @Column(name = "suborder")
    private String suborder;
    /**
     * 子目
     */
    @Column(name = "subtitle")
    private String subtitle;
    /**
     * 细目
     */
    @Column(name = "detail")
    private String detail;
    /**
     * 合并编码
     */
    @Column(name = "merge_code")
    private String mergeCode;
    /**
     * 货物名称
     */
    @Column(name = "description_goods")
    private String descriptionGoods;
    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;
    /**
     * 说明
     */
    @Column(name = "state")
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
