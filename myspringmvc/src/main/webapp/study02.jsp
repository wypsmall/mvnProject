<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Basic Layout - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css"
	href="./easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="./easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="./easyui/demo/demo.css">
<script type="text/javascript" src="./easyui/jquery.min.js"></script>
<script type="text/javascript" src="./easyui/jquery.easyui.min.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
    $("#user-datagrid").datagrid({ 
        url : 'datagrid_data1.json', 
        //width: 700, 
        //height: 'auto', 
        nowrap: false, 
        striped: true, 
        title : '用户列表', 
        pageSize : 20, 
        pageList : [ 10, 20, 30, 40 ], 
        method:'get',
        fit : true,//自适应窗口大小变化 
        fitColumns : true, 
        border : false, 
        singleSelect:true,//是否单选 
        pagination:true,//分页控件 
        rownumbers:true,//行号 
        idField : 'id', 
        columns : [ [ { 
            title : '用户编号', 
            field : 'id', 
            width : 100 
        //宽度必须，数值随便 
        }, { 
            title : '用户名', 
            field : 'author', 
            width : 100 
        }, { 
            title : '用户密码', 
            field : 'name', 
            width : 100 
        },{ 
            title:'注册时间', 
            field:'link', 
            width:100 
        } ] ], 
        toolbar : [ { 
            text : '增加', 
            iconCls : 'icon-add', 
            handler : function() { 
            } 
        }, '-', { 
            text : '删除', 
            iconCls : 'icon-remove', 
            handler : function() { 
            } 
        }, '-', { 
            text : '编辑', 
            iconCls : 'icon-edit', 
            handler : function() { 
            } 
        }] 

    //两个[],实现多级表头，合并单元格的效果，产生不规则表头   

    }); 

}); 
</script>
<body class="easyui-layout">

<!-- 	<table class="easyui-datagrid"
		data-options="url:'datagrid_data1.json',method:'get',border:true,singleSelect:true,fit:true,fitColumns:true,rownumbers:true,pagination:true,pageSize:3">
		<thead>
			<tr>
				<th data-options="field:'id'" width="20">id</th>
				<th data-options="field:'author'" width="60">author</th>
				<th data-options="field:'publicationDate'" width="60">publicationDate</th>
				<th data-options="field:'name'" width="100">name</th>
				<th data-options="field:'link',align:'center'" width="160">link</th>
			</tr>
		</thead>
	</table> -->
	<table id="user-datagrid"></table>

</body>
</html>