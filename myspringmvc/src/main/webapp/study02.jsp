<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
	$("#tt").tree({  
		url:"tree_data1.json",
		onClick:function(node)  
	    {  
	    	//alert(node.text);
	    	addTab(node.text, 'http://www.baidu.com');
	    }  
	});
	
	
});

function addTab(title, url){
	if ($('#tbs').tabs('exists', title)){
		$('#tbs').tabs('select', title);
	} else {
		alert(url);
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		$('#tbs').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
	}
}
	
function collapseAll(){
	$('#tt').tree('collapseAll');
}
function expandAll(){
	$('#tt').tree('expandAll');
}
function expandTo(){
	var node = $('#tt').tree('find',113);
	$('#tt').tree('expandTo', node.target).tree('select', node.target);
}
function getSelected(){
	var node = $('#tt').tree('getSelected');
	if (node){
		var s = node.text;
		if (node.attributes){
			s += ","+node.attributes.p1+","+node.attributes.p2;
		}
		alert(s);
	}
}

</script>
<body class="easyui-layout">

	<table class="easyui-datagrid"
		data-options="url:'datagrid_data1.json',method:'get',border:true,singleSelect:true,fit:true,fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'id'" width="20">id</th>
				<th data-options="field:'author'" width="60">author</th>
				<th data-options="field:'publicationDate'" width="60">publicationDate</th>
				<th data-options="field:'name'" width="100">name</th>
				<th data-options="field:'link',align:'center'" width="160">link</th>
			</tr>
		</thead>
	</table>

</body>
</html>