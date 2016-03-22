<#assign ctx=requestContext.contextPath>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${ctx}/js/jquery-2.0.3.js"></script>
</head>
<body>
	<input type="text" name="userName"/>
	<input type="text" name="passWord"/>
	<input type="text" name="randCode" id="randCode"/>
	<img src="rc">
	<input type="button" name="submit" id="submit"/>
	<script>
		$(document).ready(function(){
			$("#submit").click(function(){
			alert($("#randCode").val());
				$.post("login",{"randCode":$("#randCode").val()},function(data,msg){
					alert(data.data.loginCheck);
				},"json");
			});
		});
	</script>
</body>
</html>