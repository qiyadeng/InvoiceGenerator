<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商户管理</title>
    <script type="text/javascript">

        var dataGrid;
        var organizationTree;

        $(function () {
            organizationTree = $('#organizationTree').tree({
                url: '${path }/organization/tree',
                parentField: 'pid',
                lines: true,
                onClick: function (node) {
                    dataGrid.datagrid('load', {
                        organizationId: node.id
                    });
                }
            });

            dataGrid = $('#dataGrid').datagrid({
                url: '${path }/user/dataGrid',
                fit: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                idField: 'id',
                sortName: 'createTime',
                sortOrder: 'asc',
                pageSize: 20,
                pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
                columns: [[{
                    field: 'action',
                    title: '操作',
                    width: 130,
                    formatter: function (value, row, index) {
                        var str = '';
                        <shiro:hasPermission name="/user/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/user/delete">
                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                        return str;
                    }
                }, {
                    width: '80',
                    title: '登录名',
                    field: 'loginName',
                    sortable: true
                }, {
                    width: '80',
                    title: '姓名',
                    field: 'name',
                    sortable: true
                }, {
                    width: '80',
                    title: '客户ID',
                    field: 'organizationId',
                    hidden: true
                }, {
                    width: '80',
                    title: '所属客户',
                    field: 'organizationName'
                }, {
                    width: '100',
                    title: '角色',
                    field: 'rolesList',
                    sortable: true,
                    formatter: function (value, row, index) {
                        var roles = [];
                        for (var i = 0; i < value.length; i++) {
                            roles.push(value[i].name);
                        }
                        return (roles.join('\n'));
                    }
                }, {
                    width: '60',
                    title: '商户类型',
                    field: 'userType',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return "管理员";
                        } else if (value == 1) {
                            return "用户";
                        }
                        return "未知类型";
                    }
                }, {
                    width: '120',
                    title: '电话',
                    field: 'phone',
                    sortable: true
                }, {
                    width: '120',
                    title: '邮箱',
                    field: 'email',
                }, {
                    width: '150',
                    title: '商户编码',
                    field: 'merchant_code',
                }, {
                    width: '150',
                    title: '商户名称',
                    field: 'merchant_name',
                }, {
                    width: '150',
                    title: '商户开户银行及账户',
                    field: 'tax_yhzh',

                }, {
                    width: '150',
                    title: '商户纳税人识别号',
                    field: 'tax_nsrsbh',
                }, {
                    winth: '150',
                    title: '商户公司地址及电话',
                    field: 'tax_dzdh'
                }, {
                    width: '150',
                    title: '商品编码',
                    field: 'tax_spbm',
                }, {
                    width: '150',
                    title: '编码表版本号',
                    field: 'bmbbbh',
                }, {
                    width: '100',
                    title: '应用系统',
                    field: 'app_no',
                }, {
                    width: '80',
                    title: 'MD5通讯密钥',
                    field: 'md5key',
                }, {
                    width: '60',
                    title: '状态',
                    field: 'status',
                    sortable: true,
                    formatter: function (value, row, index) {
                        switch (value) {
                            case 0:
                                return '正常';
                            case 1:
                                return '停用';
                        }
                    }
                }, {
                    width: '130s',
                    title: '创建时间',
                    field: 'createTime',
                    sortable: true
                }]],
                onLoadSuccess: function (data) {
                    $('.user-easyui-linkbutton-edit').linkbutton({text: '编辑', plain: true, iconCls: 'icon-edit'});
                    $('.user-easyui-linkbutton-del').linkbutton({text: '删除', plain: true, iconCls: 'icon-del'});
                },
                toolbar: '#toolbar'
            });
        });

        function addFun() {
            parent.$.modalDialog({
                title: '添加',
                width: 700,
                height: 600,
                href: '${path }/user/addPage',
                buttons: [{
                    text: '添加',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#userAddForm');
                        f.submit();
                    }
                }]
            });
        }

        function deleteFun(id) {
            if (id == undefined) {//点击右键菜单才会触发这个
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            } else {//点击操作里面的删除图标会触发这个
                dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
            }
            parent.$.messager.confirm('询问', '您是否要删除当前商户？', function (b) {
                console.log(${sessionInfo.id})
                if (b) {
                    let currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
                    if (currentUserId != id) {
                        progressLoad();
                        $.post('${path }/user/delete', {
                            id: id
                        }, function (result) {
                            if (result.success) {
                                parent.$.messager.alert('提示', result.msg, 'info');
                                dataGrid.datagrid('reload');
                            }
                            progressClose();
                        }, 'JSON');
                    } else {
                        parent.$.messager.show({
                            title: '提示',
                            msg: '不可以删除自己！'
                        });
                    }
                }
            });
        }

        function editFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            } else {
                dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
            }
            parent.$.modalDialog({
                title: '编辑',
                width: 700,
                height: 650,
                href: '${path }/user/editPage?id=' + id,
                buttons: [{
                    text: '确定',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#userEditForm');
                        f.submit();
                    }
                }]
            });
        }

        function searchFun() {
            dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
        }

        function cleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
    <form id="searchForm">
        <table>
            <tr>
                <th>姓名:</th>
                <td><input name="name" placeholder="请输入用户姓名"/></td>
                <th>创建时间:</th>
                <td>
                    <input name="createdateStart" placeholder="点击选择时间"
                           onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                           readonly="readonly"/>至<input name="createdateEnd" placeholder="点击选择时间"
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
<div data-options="region:'center',border:true,title:'商户列表'">
    <table id="dataGrid" data-options="fit:true,border:false"></table>
</div>
<div data-options="region:'west',border:true,split:false,title:'客户'" style="width:150px;overflow: hidden; ">
    <ul id="organizationTree" style="width:160px;margin: 10px 10px 10px 10px">
    </ul>
</div>
<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="/user/add">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'icon-add'">添加</a>
    </shiro:hasPermission>
</div>
</body>
</html>