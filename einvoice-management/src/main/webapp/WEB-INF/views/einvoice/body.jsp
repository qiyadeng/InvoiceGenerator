<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
    </div>
</div>
<script type="text/javascript">
    let dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/einvoice/detail/${id}',
            striped : true,
            rownumbers : true,
            singleSelect : true,
            idField : 'pk_einvoicebody',
            sortName : 'pk_einvoicebody',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '行号',
                field : 'row_no'
            }, {
                title : '名称',
                field : 'xmmc'
            },{
                title : '商品编码',
                field : 'spbm'
            }, {
                title : '计量单位',
                field : 'dw'
            }, {
                title : '规格型号',
                field : 'ggxh'
            } ,{
                title : '项目数量',
                field : 'xmsl'
            }, {
                title : '项目单价',
                field : 'xmdj'
            },{
                title : '项目金额',
                field : 'xmje'
            }, {
                title : '税率',
                field : 'sl'
            },{
                title : '税额',
                field : 'se'
            }] ],
            onLoadSuccess:function(data){
            },
            toolbar : '#toolbar'
        });
    });

</script>