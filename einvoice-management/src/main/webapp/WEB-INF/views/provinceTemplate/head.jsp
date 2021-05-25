<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <title>PDF模板</title>
    <script type="text/javascript">
        var dataGrid;
            function fixHeight(percent)
            {
                return (document.body.clientHeight) * percent ;
            }

            function fixWidth(percent)
            {
                return (document.body.clientWidth - 5) * percent ;
            }
            $(function() {
            dataGrid = $('#dataGrid').datagrid({
                url : '${path}/provinceTemplate/listProvinceTemplate',
                striped : true,
                rownumbers : true,
                pagination : true,
                singleSelect : true,
                idField : 'id',
                sortName : 'createTime',
                sortOrder : 'asc',
                pageSize : 20,
                pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
                columns : [
                    [ {
                    title : '省份',
                    width: fixWidth(0.2),
                    field : 'province',
                },{
                    title : 'PDF模板',
                    width: fixWidth(0.2),
                    field : 'templateUrl',
                    formatter : function(value, row, index) {
                        return '<a href="'+value+'" target="_blank">下载</a>';
                    }
                },{
                    title : '创建时间',
                    width: fixWidth(0.2),
                    field : 'createTime',
                },{
                    title : '更新时间',
                    width: fixWidth(0.2),
                    field : 'updateTime',
                }, {
                        field : 'action',
                        title : '操作',
                        width: fixWidth(0.2),
                        formatter : function(value, row, index) {
                            var str = '';
                            <shiro:hasPermission name="/provinceTemplate/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                            </shiro:hasPermission>
                            return str;
                        }
                    }
                ]],
                onLoadSuccess:function(data){
                    $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
                },
                toolbar : '#toolbar'
            });
        });
        function searchFun() {
            dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
        }
        function cleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }
        function addFun() {
            parent.$.modalDialog.openner_dataGrid = dataGrid;
            parent.$.modalDialog({
                title : '添加',
                width : 300,
                height : 200,
                href : '${path }/provinceTemplate/addPage',
                buttons : [{
                    text : '关闭',
                    handler : function() {
                        parent.$.modalDialog.handler.dialog('close');
                        dataGrid.datagrid('load', {});
                    }
                }]
            });
        }
        function deleteFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.messager.confirm('询问', '您是否要删除当前模板？', function(flag) {
                if (flag) {
                    progressLoad();
                    $.post('${path }/provinceTemplate/delete', {
                        id : id
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid('reload');
                        }
                        progressClose();
                    }, 'JSON')
                }
            });
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
    <form id="searchForm">
        <table>
            <tr>
                <th>省份:</th>
                <td><input name="name" placeholder="请输入省份"/></td>
                <th>创建时间:</th>
                <td>
                    <input name="createTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input  name="createdateEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="dataGrid" data-options="fit:true,border:false"></table>
</div>
<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="/provinceTemplate/add">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    </shiro:hasPermission>
</div>
</body>
</html>