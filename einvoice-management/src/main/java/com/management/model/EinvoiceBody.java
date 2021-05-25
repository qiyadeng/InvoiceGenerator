package com.management.model;

/**
 * Created by sdyang on 2016/10/6.
 */

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("einvoice_body")
public class EinvoiceBody {

    @TableId(type = IdType.AUTO)
    private Long pk_einvoicebody;


    private String fpqqlsh;


    private int row_no;//行号


    private String fphxz;//发票行性质：0正常行、1折扣行、2被折扣行


    private String xmmc;//项目名称


    private String dw;//计量单位


    private String ggxh;//规格型号


    private double xmsl;//项目数量


    private double xmdj;//项目单价


    private double xmje;//项目金额


    private double sl;//税率


    private double se;//税额

    private String spbm;

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }
    //---------------------------------新增字段-----------------------------//


    private Long pk_einvoicehead ;


    private int dr=0;//逻辑删除标识

    //---------------------------settet 、 getter -------------------------//


    public Long getPk_einvoicebody() {
        return pk_einvoicebody;
    }

    public void setPk_einvoicebody(Long pk_einvoicebody) {
        this.pk_einvoicebody = pk_einvoicebody;
    }

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }

    public int getRow_no() {
        return row_no;
    }

    public void setRow_no(int row_no) {
        this.row_no = row_no;
    }

    public String getFphxz() {
        return fphxz;
    }

    public void setFphxz(String fphxz) {
        this.fphxz = fphxz;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public double getXmsl() {
        return xmsl;
    }

    public void setXmsl(double xmsl) {
        this.xmsl = xmsl;
    }

    public double getXmdj() {
        return xmdj;
    }

    public void setXmdj(double xmdj) {
        this.xmdj = xmdj;
    }

    public double getXmje() {
        return xmje;
    }

    public void setXmje(double xmje) {
        this.xmje = xmje;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public double getSe() {
        return se;
    }

    public void setSe(double se) {
        this.se = se;
    }

    public Long getPk_einvoicehead() {
        return pk_einvoicehead;
    }

    public void setPk_einvoicehead(Long pk_einvoicehead) {
        this.pk_einvoicehead = pk_einvoicehead;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }
}
