<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>发票列表</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
    <%--发票查询--%>
    <form id="searchForm">
        <table>
            <tr>
                <th>发票流水号:</th>
                <td><input name="fpqqlsh" placeholder="请输入发票流水号"/></td>
                <th>商户订单号:</th>
                <td><input name="order_no" placeholder="请输入商户订单号"/></td>
                <th>销售方名称:</th>
                <td><input name="xsf_mc" placeholder="销售方名称"/></td>
                <th>发票状态</th>
                <td>
                    <select name="status" placeholder="请选择发票状态">
                        <option value="0">请选择发票状态</option>
                        <option value="1">开票数据接收成功</option>
                        <option value="2">开票数据接收失败</option>
                        <option value="3">生成开票XML成功</option>
                        <option value="4">生成开票XML失败</option>
                        <option value="5">开票成功</option>
                        <option value="6">开票失败</option>
                        <option value="7">生成PDF成功</option>
                        <option value="8">生成PDF失败</option>
                        <option value="9">电子签名成功</option>
                        <option value="10">电子签名失败</option>
                        <option value="11">结果已回传</option>
                        <option value="12">蓝票已冲红</option>
                        <option value="13">发票正在取消</option>
                        <option value="13">发票取消完成</option>
                    </select>
                </td>
                <th>请求日期:</th>
                <td>
                    从<input name="submit_begin" placeholder="点击选择时间"
                            onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                            readonly="readonly"/>到<input name="submit_end" placeholder="点击选择时间"
                                                         onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                                         readonly="readonly"/>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a
                        href="javascript:void(0);" class="easyui-linkbutton"
                        data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="dataGrid" data-options="fit:true,border:false"></table>
</div>
<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="/einvoice/add">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'icon-add'">添加</a>
    </shiro:hasPermission>
</div>
<script src="${staticPath }/static/utils/utils.js"></script>
<script type="text/javascript">
    let dataGrid;
    $(function () {
        let serviceUrl = '${serviceUrl}'
        dataGrid = $('#dataGrid').datagrid({
            url: '${path }/einvoice/dataGrid',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'pk_einvoicehead',
            sortName: 'pk_einvoicehead',
            autoRowHeight: false,
            sortOrder: 'asc',
            pagination: true,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[
                {
                    field: 'pk_einvoicehead',
                    title: '操作',
                    formatter: function (value, row, index) {
                        let str = '';
                        str += "<a onclick='showDetail(\"" + row.fpqqlsh + "\")' href='javascript:void(0);'>查看</a>"
                        str += '｜<a value="/einvoice/body/' + value + '" class="invoiceBody" href="javascript:void(0)">项目明细</a>';
                        str += "｜<a onclick='copyInvoice(\"" + row.fpqqlsh + "\")' href='javascript:void(0);'>复制开票</a>"

                        if (row.status == 7) {
                            str += "|<a href=" + serviceUrl + "downloadGeneratedPdf/" + row.fpqqlsh + ">下载</a>"
                        }
                        if (row.status == 0 || row.status == 1 || row.status == 3)
                            str += "｜<a onclick='cancelInvoice(\"" + row.fpqqlsh + "\")' href='javascript:void(0);'>取消开票</a>"
                        return str;
                    }
                },
                {
                    title: '发票当前状态',
                    field: 'status',
                    sortable: true,
                    formatter: function (value, row, index) {
                        let temp = "<span title='{0}' style='{1}' '>{2}</span>"

                        let msg = row.returnmsg
                        if (msg == null || msg == undefined) {
                            msg = ""
                        }
                        switch (value) {
                            case 0:
                                return utils.format(temp, msg, '', '等待用户核对信息')
                            case 1:
                                return utils.format(temp, msg, '', '开票数据接收成功')
                            case 2:
                                return utils.format(temp, msg, 'color:red;', '开票数据接收失败')
                            case 3:
                                return utils.format(temp, msg, '', '开票信息生成成功')
                            case 4:
                                return utils.format(temp, msg, 'color:red;', '开票信息生成失败')
                            case 5:
                                return utils.format(temp, msg, '', '开票成功')

                            case 6:
                                return utils.format(temp, msg, 'color:red;', '开票失败')

                            case 7:
                                return utils.format(temp, msg, '', '已开票，PDF发票生成成功')

                            case 8:
                                return utils.format(temp, msg, 'color:red;', '已开票，PDF生成失败')
                            case 9:
                                return utils.format(temp, msg, '', '电子签名成功')
                            case 10:
                                return utils.format(temp, msg, 'color:red;', '电子签名失败')

                            case 11:
                                return utils.format(temp, msg, '', '结果已回传')
                            case 12:
                                return utils.format(temp, msg, '', '蓝票已冲红')

                            case 14:
                                return utils.format(temp, msg, '', '正在取消')

                            case 15:
                                return utils.format(temp, msg, '', '取消成功')
                        }
                    }
                },
                {
                    title: '发票代码',
                    field: 'fp_dm'
                }, {
                    title: '发票号码',
                    field: 'fp_hm'
                }, {
                    title: '销售方纳税人识别号',
                    field: 'xsf_nsrsbh',
                }, {
                    title: '销售方名称',
                    field: 'xsf_mc',
                }, {
                    title: '购买方纳税人识别号',
                    field: 'gmf_nsrsbh',
                }, {
                    title: '购买方名称',
                    field: 'gmf_mc'
                }
                , {
                    title: '发票流水号',
                    field: 'fpqqlsh',
                },
                {
                    title: '开票类型',
                    field: 'kplx',
                    formatter: function (value, row, index) {
                        switch (value) {
                            case '0':
                                return '蓝票';
                            case '1':
                                return '红票';
                        }
                    }
                }, {
                    title: '开票人',
                    field: 'kpr',
                    sortable: true
                }, {
                    title: '合计金额',
                    field: 'hjje',
                    sortable: true
                }, {
                    title: '合计税额',
                    field: 'hjse',
                    sortable: true
                }, {
                    title: '创建时间',
                    field: 'createtime'
                }]],
            toolbar: '#toolbar'
        });
    });

    $('body').on('click', '.invoiceBody', function () {
        let url = $(this).attr('value')
        parent.$.modalDialog({
            title: '项目明细',
            width: 1200,
            height: 500,
            href: '${path}' + url,
            buttons: [{
                text: '关闭',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('close');
                    dataGrid.datagrid('load', {});
                }
            }]
        });
    })

    function searchFun() {
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }

    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }

    function copyInvoice(fpqqlsh) {
        let url = '${path }/einvoice/addPage';
        if (fpqqlsh != null && fpqqlsh != "") {
            url = url + "?fpqqlsh=" + fpqqlsh
        }
        parent.$.modalDialog({
            title: '添加',
            width: 850,
            height: 600,
            href: url,
            buttons: [{
                text: '关闭',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('close');
                    dataGrid.datagrid('load', {});
                }
            }]
        });
    }

    function showDetail(fpqqlsh) {
        parent.$.modalDialog({
            title: '详情',
            width: 800,
            height: 600,
            href: '${path}/einvoice/invoiceDetail?fpqqlsh=' + fpqqlsh,
            buttons: [{
                text: '关闭',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('close');
                    dataGrid.datagrid('load', {});
                }
            }]
        })
    }

    function addFun() {
        let url = '${path }/einvoice/addPage';
        parent.$.modalDialog({
            title: '添加',
            width: 850,
            height: 600,
            href: url,
            buttons: [{
                text: '关闭',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('close');
                    dataGrid.datagrid('load', {});
                }
            }]
        });
    }

    function cancelInvoice(fpqqlsh) {
        $.ajax({
            url: '${serviceUrl}' + "cancelInvoice/?serialNumber=" + fpqqlsh,
            method: 'get',
            async: 'false',
            success: function (res) {
                console.log(res)
                if (res.code == 0) {
                    parent.$.messager.alert('提示', "取消开票成功", 'warning');
                    dataGrid.datagrid('load', {});
                }
            }
        })
    }
</script>
</body>
</html>