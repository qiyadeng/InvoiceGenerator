<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <table>
            <tr style="vertical-align:top">
                <td>
                    <table class="grid">
                        <tr>
                            <td>商户</td>
                            <td colspan="3"><span>${user.tax_merchant_name}(${user.tax_nsrsbh})</span></td>
                        </tr>
                        <tr>
                            <td>服务、商品名称</td>
                            <td>
                                <span id="productName">*${detail.productName}*${body.xmmc}</span>
                            </td>
                            <td>开票金额</td>
                            <td><span>${head.jshj}元</span></td>
                        </tr>

                        <tr>
                            <td>开票公司名称</td>
                            <td><span id="gmf_mc">${head.gmf_mc}</span></td>
                            <td>纳税人识别号</td>
                            <td><span id="gmf_nsrsbh">${head.gmf_nsrsbh}</span></td>
                        </tr>
                        <tr>
                            <td>开户银行名称</td>
                            <td><span id="gmf_yh">${head.gmf_yhzh.substring(0,head.gmf_yhzh.indexOf(" "))}</span></td>
                            <td>开户银行账号</td>
                            <td><span id="gmf_zh">${head.gmf_yhzh.substring(head.gmf_yhzh.indexOf(" "))}</span></td>
                        </tr>
                        <tr>
                            <td>公司地址</td>
                            <td><span id="gmf_dz">${head.gmf_dzdh .substring(0,head.gmf_dzdh.indexOf(" "))}</span></td>
                            <td>公司电话</td>
                            <td><span id="gmf_dh">${head.gmf_dzdh.substring(head.gmf_dzdh.indexOf(" "))}</span></td>
                        </tr>
                        <tr>
                            <td>税率</td>
                            <td><span>${user.tax_sl}%</span></td>
                            <td>通知邮箱</td>
                            <td><span id="messageEmail">${head.gmf_email}</span></td>
                        </tr>
                    </table>
                </td>
                <td>
                    <button id="printBtn" onclick="print()">打印</button>
                    <div id="templateDesignDiv"></div>
                </td>
            </tr>
        </table>
    </div>
</div>
<!-- hiprint -->
<link rel="stylesheet" type="text/css" href="${staticPath }/static/hiprint/css/hiprint.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${staticPath }/static/hiprint/css/print-lock.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${staticPath }/static/hiprint/css/print-lock.css" rel="stylesheet"/>

<!-- 加载 hiprint 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<!-- polyfill.min.js解决浏览器兼容性问题v6.26.0-->
<script src="${staticPath }/static/hiprint/polyfill.min.js"></script>
<!-- hiprint 核心js-->
<script src="${staticPath }/static/hiprint/hiprint.bundle.js"></script>
<!-- 条形码生成组件-->
<script src="${staticPath }/static/hiprint/plugins/JsBarcode.all.min.js"></script>
<!-- 二维码生成组件-->
<script src="${staticPath }/static/hiprint/plugins/qrcode.js"></script>
<!-- 调用浏览器打印窗口helper类，可自行替换-->
<script src="${staticPath }/static/hiprint/plugins/jquery.hiwprint.js"></script>
<script src="${staticPath}/static/api.js"></script>
<script src="${staticPath}/static/utils/utils.js"></script>
<script type="text/javascript">
    function print() {
        hiprintTemplate.print({});
    }

    var hiprintTemplate;
    $(function () {
        $('#organizationId').combotree({
            url: '${path }/organization/tree',
            parentField: 'pid',
            lines: true,
            panelHeight: 'auto'
        });

        $('#roleIds').combotree({
            url: '${path }/role/tree',
            multiple: true,
            required: true,
            panelHeight: 'auto'
        });
        $("#tax_sl").textbox()
        hiprint.init();
        <!-- 创建打印模板对象-->
        hiprintTemplate = new hiprint.PrintTemplate();
        <!-- 模板对象添加打印面板 paperHeader：页眉线 paperFooter：页尾线-->
        let qrcode = utils.format(apiConfig.qrcodeUrl, ['${head.fpqqlsh}'])
        let panel = hiprintTemplate.addPrintPanel({width: 65, height: 150, paperFooter: 240, paperHeader: 10, paperNumberDisabled:true});
        panel.addPrintText({
            options: {
                width: 150,
                height: 15,
                fontSize: 11,
                textAlign: 'center',
                fontWeight:"blod",
                top: 20,
                left: 20,
                title: '${user.tax_merchant_name}'
            }
        });
        panel.addPrintText({
            options: {
                width: 150,
                height: 15,
                top: 40,
                left: 20,
                title: "流水号:"+'${head.fpqqlsh}'
            }
        });

        panel.addPrintText({
            options: {
                width: 150,
                height: 15,
                top: 60,
                left: 20,
                title: "服务、商品名称：" + '*${detail.productName}*' + '${body.xmmc}'
            }
        });
        let date = new Date('${head.createtime}').toLocaleString().replace(/\//g, '-')
        panel.addPrintText({
            options: {
                width: 150,
                height: 15,
                top: 80,
                left: 20,
                title: "开票金额：" + '${head.jshj}' + "元"
            }
        });
        panel.addPrintText({options: {width: 80, height: 80, top: 95, left: 40, title: qrcode, textType: 'qrcode'}});
        panel.addPrintText({
            options: {
                width: 150,
                height: 15,
                top: 180,
                left: 20,
                title: '扫描以上二维码提交抬头信息或下载电子发票'
            }
        });
        panel.addPrintText({options: {width: 155, height: 15, top: 205, left: 20, title: "创建日期：" + date}});
        console.log('${failureTime}')
        let failureTime = new Date('${failureTime}').toLocaleString().replace(/\//g, '-')

        panel.addPrintText({
            options: {
                width: 140,
                height: 15,
                top: 215,
                left: 20,
                title: '请于'+failureTime+'前扫码开票，否则二维码将失效'
            }
        });
        panel.addPrintText({
            options: {
                width: 140,
                height: 15,
                top: 245,
                left: 20,
                title: '1.请使用支付宝或微信扫码'
            }
        });
        panel.addPrintText({
            options: {
                width: 140,
                height: 15,
                top: 265,
                left: 20,
                title: '2.填写开票抬头信息后提交'
            }
        });
        panel.addPrintText({
            options: {
                width: 140,
                height: 15,
                top: 280,
                left: 20,
                title: '3.申请完成后会将发票信息发送到您的邮箱或再次扫码下载发票PDF文件'
            }
        });
        panel.addPrintText({
            options: {
                width: 140,
                height: 15,
                top: 315,
                left: 20,
                title: '4.如需纸质发票，请自行打印'
            }
        });
        panel.addPrintLongText({options: {width: 140, height: 35, top: 340, left: 20, title: '${user.ad}'}});
        $("#templateDesignDiv").html(hiprintTemplate.getHtml({}));
    });
</script>