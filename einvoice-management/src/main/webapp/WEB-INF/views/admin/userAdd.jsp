<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userAddForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>姓名</td>
                    <td><input name="name" type="text" placeholder="请输入姓名" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>手机号码</td>
                    <td>
                        <input type="text" name="phone" placeholder="请输入手机号码" class="easyui-numberbox"
                               data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>登录名称</td>
                    <td><input name="loginName" type="text" placeholder="请输入登录名称" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>密码</td>
                    <td><input name="password" type="password" placeholder="请输入密码" class="easyui-validatebox"
                               data-options="required:true"></td>
                </tr>
                <tr>
                    <td>客户</td>
                    <td><select id="organizationId" name="organizationId" style="width: 140px; height: 29px;"
                                class="easyui-validatebox" data-options="required:true"></select></td>
                    <td>角色</td>
                    <td><select id="roleIds" name="roleIds" style="width: 140px; height: 29px;"></select></td>
                </tr>
                <tr>
                    <td>商户名称</td>
                    <td><input name="merchant_name" type="text" placeholder="请输入商户名称" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>商户类型</td>
                    <td>
                        <select name="userType" class="easyui-combobox"
                                data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">管理员</option>
                            <option value="1" selected="selected">用户</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>邮箱</td>
                    <td><input name="email" type="text" placeholder="请输入邮箱" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>应用系统</td>
                    <td><input name="app_no" type="text" placeholder="请输入应用系统" class="easyui-validatebox"
                               data-options="required:true"></td>
                </tr>
                <tr>
                    <td>微信id</td>
                    <td><input name="wechat_id" type="text" placeholder="微信通知id" class="easyui-validatebox"></td>
                    <td>门店活动</td>
                    <td><input name="ad" type="text" placeholder="门店活动" class="easyui-validatebox"></td>
                </tr>

                <tr>
                    <td>商户公司名称</td>
                    <td><input name="tax_merchant_name" type="text" placeholder="请输入商户公司名称" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>商户纳税人识别号</td>
                    <td><input name="tax_nsrsbh" type="text" placeholder="请输入商户纳税人识别号" class="easyui-validatebox"
                               data-options="required:true"></td>
                </tr>
                <tr>
                    <td>商户开户银行</td>
                    <td><input name="tax_yh" type="text" placeholder="请输入商户开户银行" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>商户银行账号</td>
                    <td><input name="tax_zh" type="text" placeholder="请输入商户银行账号" class="easyui-validatebox"
                               data-options="required:true"></td>
                </tr>
                <tr>
                    <td>商户地址</td>
                    <td><input name="tax_dz" type="text" placeholder="请输入商户地址" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>商户电话</td>
                    <td><input name="tax_dh" type="text" placeholder="请输入商户电话" class="easyui-validatebox"
                               data-options="required:true"></td>
                </tr>
                <tr>
                    <td>服务名称</td>
                    <td><input id="selectTaxDetail" class="easyui-combogrid"/></td>
                    <td>商品单位</td>
                    <td><input name="tax_spdw" type="text" placeholder="请输入商品单位" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>商品编码</td>
                    <td><input id="taxSpbm" name="tax_spbm" type="text" placeholder="请输入税务19位商品编码，如3040201010000000000"
                               class="easyui-validatebox" data-options="required:true"></td>
                    <td>税率</td>
                    <td><input id="tax_sl" name="tax_sl" type="text" placeholder="如果是6%，请输入6" class="easyui-validatebox"
                               data-options="required:true">%
                    </td>
                </tr>

                <tr>
                    <td>商户区域</td>
                    <td><input name="tax_shengfen" type="text" placeholder="省份" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>发票模板</td>
                    <td>
                        <select id="invoice_template" class="easyui-combobox"
                                data-options="width:140,height:29,editable:false,panelHeight:'auto',required:true">

                        </select>
                    </td>
                </tr>

                <tr>
                    <td>开票审核用户</td>
                    <td><input name="tax_shenheren" type="text" placeholder="开票审核用户" class="easyui-validatebox"
                               data-options="required:true"></td>
                    <td>开票版本</td>
                    <td><input name="bmbbbh" type="text" placeholder="版本" class="easyui-validatebox"
                               data-options="required:true"></td>
                </tr>
                <%--                <tr>--%>
                <%--                    <td>证书上传</td>--%>
                <%--                    <td><input class="easyui-filebox" name="" buttonText="选择证书文件"></td>--%>
                <%--                    <td>证书密钥</td>--%>
                <%--                    <td><input class="easyui-validatebox" type="text" name="" buttonText="请填写证书密钥"></td>--%>
                <%--                </tr>--%>
                <tr>
                    <td>公章上传</td>
                    <td><input class="easyui-filebox" name="file" buttonText="选择公章图片" accept="application/png"
                               data-options="required:true"></td>
                    <td>商品名称</td>
                    <td><input name="tax_spmc" type="text" placeholder="默认商品名称" class="easyui-validatebox"
                               data-options="required:true"></td>
                </tr>
                <input id="tax_rate_detail_id" class="easyui-validatebox" name="tax_rate_detail_id" type="hidden"/>
                <input id="template_id" class="easyui-validatebox" name="template_id" type="hidden"/>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#organizationId').combotree({
            url: '${path }/organization/tree',
            parentField: 'pid',
            lines: true,
            panelHeight: 'auto'
        });

        $('#invoice_template').combobox({
            url: '${path}/provinceTemplate/listTemplate',
            valueField: "id",
            textField: "province",
            onSelect: function (record) {
                $("#template_id").val(record.id)
            }
        })

        $('#selectTaxDetail').combogrid({
            url: '${path }/taxRateDetail/list',
            delay: 500,
            idField: "descriptionGoods",
            mode: 'remote',
            panelWidth: 500,
            loadMsg: "正在加载...",
            columns: [[
                {field: 'id', title: 'id', width: 50},
                {field: 'mergeCode', title: '合并编码', width: 225},
                {field: 'descriptionGoods', title: '服务名称', width: 225}
            ]],
            onHidePanel: function () {
                let grid = $('#selectTaxDetail').combogrid('grid');
                let selected = grid.datagrid('getSelected')
                $('#taxSpbm').val(selected.mergeCode)
                $('#tax_rate_detail_id').val(selected.id);
            }
        });

        $('#roleIds').combotree({
            url: '${path }/role/tree',
            multiple: true,
            required: true,
            panelHeight: 'auto'
        });

        $('#userAddForm').form({
            url: '${path }/user/add',
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
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });
    });
</script>