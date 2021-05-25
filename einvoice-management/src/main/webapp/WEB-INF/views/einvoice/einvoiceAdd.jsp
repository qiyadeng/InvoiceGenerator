<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <table>
            <tr style="vertical-align:top">
                <td>
                    <form id="einvoiceAddForm" method="post">
                        <input name="merchant_code" type="hidden" placeholder="商户编号" class="easyui-validatebox"
                               data-options="required:true" value="${user.merchant_code}" readonly="readonly">
                        <table class="grid">
                            <tr>
                                <td>商户名称</td>
                                <td colspan="3"><span>${user.tax_merchant_name}(${user.tax_nsrsbh})</span></td>
                            </tr>
                            <tr>
                                <td>服务、商品名称</td>
                                <td>
                                    <span id="productName">*${taxDetail.productName}*</span>
                                    <input value="${user.tax_spmc}" id="xmmc" name="xmmc" type="text" placeholder="商品名称"
                                           class="easyui-validatebox" data-options="required:true">
                                </td>
                                <td>开票金额</td>
                                <td><input value="${head.jshj}" id="jshj" name="jshj" type="text" placeholder="开票金额"
                                           class="easyui-validatebox" data-options="required:true" value="">元
                                </td>
                            </tr>
                            <tr>
                                <td>开票公司名称</td>
                                <td><input  value="${head.gmf_mc}" id ="gmf_mc" name="gmf_mc" type="text" placeholder="开票公司名称" class="easyui-validatebox"></td>
                                <%--                                <td><input value="${head.gmf_mc}" id="selectCompany" name="gmf_mc" type="text"--%>
                                <%--                                           placeholder="开票公司名称" class="easyui-combogrid"></td>--%>
                                <td>纳税人识别号</td>
                                <td><input value="${head.gmf_nsrsbh}" id="gmf_nsrsbh" name="gmf_nsrsbh" type="text"
                                           placeholder="纳税人识别号" class="easyui-validatebox"></td>
                            </tr>
                            <tr>
                                <td>开户银行名称</td>
                                <td><input value="${yh}" id="gmf_yh" name="gmf_yh" type="text" placeholder="开户银行名称"
                                           class="easyui-validatebox"></td>
                                <td>开户银行账号</td>
                                <td><input value="${zh}" id="gmf_zh" name="gmf_zh" type="text" placeholder="开户银行账号"
                                           class="easyui-validatebox"></td>
                            </tr>
                            <tr>
                                <td>公司地址</td>
                                <td><input id="gmf_dz" value="${dz}" name="gmf_dz" type="text" placeholder="公司地址"
                                           class="easyui-validatebox"></td>
                                <td>公司电话</td>
                                <td><input id="gmf_dh" name="gmf_dh" value="${dh}" type="text" placeholder="公司电话"
                                           class="easyui-validatebox"></td>
                            </tr>
                            <tr>
                                <td>税率</td>
                                <td><input id="tax_sl" name="tax_sl" type="text" placeholder="税率"
                                           class="easyui-validatebox" data-options="required:true"
                                           value="${user.tax_sl}">%
                                </td>
                                <td>通知邮箱</td>
                                <td><input id="messageEmail" name="gmf_email" type="email" placeholder="邮箱地址"
                                           class="easyui-validatebox"></td>
                            </tr>
                            <input id="taxRateId" name="taxRateId" type="hidden" placeholder="taxRateId"
                                   class="easyui-validatebox" value="${user.tax_rate_id}" readonly="readonly">
                            <input name="key" type="hidden" placeholder="md5key" class="easyui-validatebox"
                                   value="${user.md5key}" readonly="readonly">
                            <input name="kplx" type="hidden" placeholder="开票类型" class="easyui-validatebox" value="0"
                                   readonly="readonly">
                            <input name="fpqqlsh" type="hidden" placeholder="发票请求流水号" class="easyui-validatebox"
                                   value="${fpqqlsh}" readonly="readonly">
                            <input id="submitType" name="submitType" type="hidden" placeholder="是否是直接提交"
                                   class="easyui-validatebox"/>
                            <input name="descriptionGoods" type="hidden" placeholder="货物和劳务名称"
                                   class="easyui-validatebox" value="${taxDetail.descriptionGoods}">
                            <input name="tax_spbm" type="hidden" placeholder="商品编码" class="easyui-validatebox"
                                   value="${taxDetail.mergeCode}">
                            <input name="productName" type="hidden" placeholder="商品和服务分类简称" class="easyui-validatebox"
                                   value="${taxDetail.productName}">
                        </table>
                    </form>
                    <button id="createQrCode" style="margin-top: 10px;" onclick="saveFun()">保存并生成二维码</button>
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
<script src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.js"></script>
<script type="text/javascript">
    let hiprintTemplate;

    //打印
    function print() {
        hiprintTemplate.print({});
    }

    //保存并生成二维码
    function saveFun() {
        if ($("#xmmc").val() == null || $("#xmmc").val() == "") {
            parent.$.messager.alert('提示', "请填写商品名称", 'warning');
            return
        }
        if ($("#jshj").val() == null || $("#jshj").val() == "") {
            parent.$.messager.alert('提示', "请填写开票金额", 'warning');
            return
        }
        if ($("#tax_sl").val() == null || $("#tax_sl").val() == "") {
            parent.$.messager.alert('提示', "请填写开票税率", 'warning');
            return
        }
        if ($("#jshj").val() < 0) {
            parent.$.messager.alert('提示', "请填写正确的开票金额", 'warning');
            return
        }
        if ($("#tax_sl").val() < 0) {
            parent.$.messager.alert('提示', "请填写正确的开票税率", 'warning');
            return
        }
        $('#printBtn').show()
        $('#submitType').val("0")
        $("#einvoiceAddForm").submit();
    }

    $(function () {
        $("#createQrCode").html("保存并生成二维码")
        $('#printBtn').hide()
        $("#tax_sl").textbox()
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
        $('#einvoiceAddForm').form({
            url: '${path }/einvoice/add',
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success: function (result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    $("#createQrCode").html("重新生成")
                    let user = result.obj.user;
                    let ein = result.obj.einvoiceHead;
                    let serviceName = $('#xmmc').val()
                    let productName = $('#productName').html()
                    hiprint.init();
                    <!-- 创建打印模板对象-->
                    hiprintTemplate = new hiprint.PrintTemplate();
                    <!-- 模板对象添加打印面板 paperHeader：页眉线 paperFooter：页尾线-->
                    let qrcode = utils.format(apiConfig.qrcodeUrl, [ein.fpqqlsh])
                    let panel = hiprintTemplate.addPrintPanel({
                        width: 65,
                        height: 150,
                        paperFooter: 240,
                        paperHeader: 10,
                        paperNumberDisabled:true
                    });
                    panel.addPrintText({
                        options: {
                            width: 150,
                            height: 15,
                            fontSize:11,
                            textAlign: 'center',
                            fontWeight:'blod',
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
                            title: "流水号:"+ein.fpqqlsh
                        }
                    });
                    panel.addPrintText({
                        options: {
                            width: 150,
                            height: 15,
                            top: 60,
                            left: 20,
                            title: "服务、商品名称："+productName + serviceName
                        }
                    });
                    panel.addPrintText({
                        options: {
                            width: 150,
                            height: 15,
                            top: 80,
                            left: 20,
                            title: "开票金额：" + ein.jshj + "元"
                        }
                    });
                    panel.addPrintText({
                        options: {
                            width: 80,
                            height: 80,
                            top: 95,
                            left: 40,
                            title: qrcode,
                            textType: 'qrcode'
                        }
                    });

                    panel.addPrintText({
                        options: {
                            width: 150,
                            height: 15,
                            top: 180,
                            left: 20,
                            title: '扫描以上二维码提交抬头信息或下载电子发票'
                        }
                    });
                    console.log(ein.createtime)
                    let createTime = new Date(ein.createtime).toLocaleString().replace(/\//g, '-')
                    panel.addPrintText({
                        options: {
                            width: 155,
                            height: 15,
                            top: 205,
                            left: 20,
                            title: "创建日期：" + createTime
                        }
                    });
                    let temp = new Date().setDate(new Date(ein.createtime).getDate()+7)

                    let failure =  new Date(temp).toLocaleString().replace(/\//g, '-')

                    panel.addPrintText({
                        options: {
                            width: 140,
                            height: 15,
                            top: 215,
                            left: 20,
                            title: '请于'+failure+'前扫码开票，否则二维码将失效'
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

                    panel.addPrintLongText({
                        options: {
                            width: 140,
                            height: 35,
                            top: 340,
                            left: 20,
                            title: '${user.ad}'
                        }
                    });
                    $("#templateDesignDiv").html(hiprintTemplate.getHtml({}));
                } else {
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });
    });
</script>