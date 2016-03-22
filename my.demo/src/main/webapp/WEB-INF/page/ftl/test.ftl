<#assign ctx=requestContext.contextPath>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<script type="text/javascript" src="${ctx}/js/jquery-2.0.3.js"></script>
</head>
<body>
	this is test freemark page!<br/><br/>
	这是一个最简单的 springmvc 整合 freemark 的例子<br/>
	没有加任何其他的spring依赖 springmvc 单独出来<br/>
	所以 spring 不仅仅是一个整体 ，它还是很多单独框架的组合。<br/>
	当我们用这个概念去搭建一个spring框架的时候，<br/>
	事情就变得简单很多。<br/>
	
	<p><#list userList as user>${user.name} ${user.age}<br/></#list></p>
	
	<input type="button" onclick="ajaxPost();" value="ajax调用"/>
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