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
            	$('#popwin').window('open');
            } 
        }, '-', { 
            text : '删除', 
            iconCls : 'icon-remove', 
            handler : function() { 
            	var row = $('#user-datagrid').datagrid('getSelected');
            	if (row) {
            	     var rowIndex = $('#user-datagrid').datagrid('getRowIndex', row);
            	     $('#user-datagrid').datagrid('deleteRow', rowIndex);
            	     $('#user-datagrid').datagrid('clearSelections');
            	 }
            } 
        }, '-', { 
            text : '编辑', 
            iconCls : 'icon-edit', 
            handler : function() { 
            	$('#popwin').window('open');
            	var row = $('#user-datagrid').datagrid('getSelected');
            	$('#ff').form('load',{
            		name:row.name
            	});
            } 
        }] 

    //两个[],实现多级表头，合并单元格的效果，产生不规则表头   

    }); 

}); 
function submitForm(){
	$('#ff').form('submit');
}
function clearForm(){
	$('#ff').form('clear');
}
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
	<div id="popwin" class="easyui-window" title="Modal Window" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:300px;padding:10px;">
		<form id="ff" method="post">
		<table cellpadding="5">
	    		<tr>
	    			<td>Name:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Email:</td>
	    			<td><input class="easyui-textbox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Subject:</td>
	    			<td><input class="easyui-textbox" type="text" name="subject" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Message:</td>
	    			<td><input class="easyui-textbox" name="message" data-options="multiline:true" style="height:60px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Language:</td>
	    			<td>
	    				<select class="easyui-combobox" name="language"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
	    			</td>
	    		</tr>
	    	</table>
	    	</form>
	    	<div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
		    </div>
	</div>
</body>
</html>