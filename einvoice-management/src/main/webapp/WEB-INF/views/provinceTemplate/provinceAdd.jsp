<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <table>
            <tr style="vertical-align:top" >
                <td>
                    <form id="einvoiceAddForm" enctype="multipart/form-data" method="post">
                        <table class="grid">
                            <tr>
                                <td>省份</td>
                                <td><input name="province" type="text" placeholder="模板省份" class="easyui-validatebox" data-options="required:true"></td>
                            </tr>
                            <tr>
                                <td>模板</td>
                                <td>
                                    <input class="easyui-filebox" name="file"  buttonText="选择模板" accept="application/pdf" data-options="required:true">
                                </td>
                            </tr>
                        </table>
                    </form>
                    <button onclick="saveFun()">保存</button>
                </td>
            </tr>
        </table>
    </div>
</div>
<!-- polyfill.min.js解决浏览器兼容性问题v6.26.0-->
<script src="${staticPath }/static/hiprint/polyfill.min.js"></script>

<script type="text/javascript">
    function saveFun() {
        $("#einvoiceAddForm").submit();
    }
    $(function() {
        $('#organizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });
        $('#roleIds').combotree({
            url: '${path }/role/tree',
            multiple: true,
            required: true,
            panelHeight : 'auto'
        });

        $('#einvoiceAddForm').form({
            url : '${path }/provinceTemplate/saveTemplate',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }else {
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });
    });
</script>