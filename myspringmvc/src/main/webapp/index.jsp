<html>
<!-- <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script> -->
<script type="text/javascript" src="./javascript/jquery-1.8.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("button").click(function(){
		var url = '';
		$.get("./jsonfeed.json", function(data,status){
			alert("Data: " + data + "\nStatus: " + status);
			
			data.items.forEach(function(e){  
			    alert(e.name);  
			})  
			
			//json字符串转json对象：jQuery.parseJSON(jsonStr);
			//json对象转json字符串：JSON.stringify(jsonObj);
			alert(JSON.stringify(data.items));
		  });
	});
});
</script>
<body>
<h2>Hello World!</h2>

<button type="button">ajax invoke</button>
</body>
</html>
