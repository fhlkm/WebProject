<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<!-- This template was designed and developed by Chris Converse, Codify Design Studio -->
<head>
<title>jQuery Mobile Web App</title>
<meta charset="UTF-8">
<meta name="description" content="This site was created from a template originally designed and developed by Codify Design Studio. Find more free templates at http://www.adobe.com/devnet/author_bios/chris_converse.html" />
<link href="jquery-mobile/jquery.mobile-1.0a3.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="includes/stylesheet.css" />
<script src="jquery-mobile/jquery-1.5.min.js" type="text/javascript"></script>
<script src="jquery-mobile/jquery.mobile-1.0a3.min.js" type="text/javascript"></script>
<script language="javascript"  type="text/JavaScript"  src="cookie.js">
</script>
<script language="javascript" type="text/JavaScript">
function doFind() {
	if(document.getElementById("username").value.length>0&&document.getElementById("password").value.length>0){
	remember();
	
	$.ajax({
		cache : false,
		type : "POST",
		url : "login", //把表单数据发送到ajax.jsp
		//data : $("#ajaxFrm").serialize(), //要发送的是ajaxFrm表单中的数据
		data: {username: document.getElementById("username").value, password: document.getElementById("password").value},
		async : false,
		error : function(request) {
			
			if(request.status ==403){
				alert(request.responseText);
			}else
			alert("send failure");
		},
		success : function(info) {
			
			//$("#ajaxDiv").html(data); //将返回的结果显示到ajaxDiv中
			if(info.status ==403)
			alert(info);
			else{
				window.location.href= info;
			}
		//console.log($("#ajaxFrm").serialize());
		}
	});
	}
}
</script>
</head> 
<body>
<div data-role="page" id="page">
	<!-- The 'logo' DIV below was added to the default markup -->
	<div class="logo"></div>
	<div data-role="header" class="heading">
		<h1>http://www.condomaintenance360.com/</h1>
	</div>
  <form method="post" action="demoform.asp">
      <label for="fname" class="labeShow">Name</label>
      <input type="text" name="fname" id="username">
	  <label for="fname" class="labeShow">Password</label>
      <input type="password" name="fname" id="password">
    </form><br><br>
    
	<input type="submit" data-inline="false" value="Login" onClick="doFind()">
	<div data-role="footer">
		<h4>&copy;2014 &bull; Enlan</h4>
	</div>
</div>






</body>
</html>