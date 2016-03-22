
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
</head>
<body>

	
	1111111111111111111111111111111111111111111qqq
	<div id="ajaxText"></div>
	
	<script>
		function ajaxPost(){
			$.post("testAjax",{},function(data,msg){
				var text = "";
				$.each(data,function(index,item){
					text += item.name+" "+item.age+" ";
				});
				$("#ajaxText").html(text);
			},"json");
		}
	</script>
	
</body>
</html>