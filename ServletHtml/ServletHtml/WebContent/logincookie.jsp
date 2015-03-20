<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<title>Login Page</title>
<%String account =(String) session.getAttribute("fail"); %>

<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.8.3.min.js"></script>
<link rel="stylesheet"  href="css/stylecollect.css">

<script language="javascript"  type="text/JavaScript"  src="cookie.js">
</script>
<script language="javascript"  type="text/JavaScript"  src="jquery/jquery-1.3.2.min.js">
</script>
<script language="javascript"  type="text/JavaScript">
// if the device is phone then show "mobileindex.jsp"
{
	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		location.href="mobileindex.jsp";
		}
}
window.onload =  function fillChecklist(){
	var error ="${sessionScope.fail}";;
	if(null != error&& error!=""){
		alert(error);
	}
}

// 	function waidAni(){
// 	       $(function() {
// 	    		$("#loading-div-background").css({ opacity: 0.8 });
// 	                $("#loading-div-background").toggle();
// 	        });
// 	}

	function doFind() {
		if(document.getElementById("username").value.length>0&&document.getElementById("password").value.length>0){
		remember();
		waidAni();
		$.ajax({
			cache : false,
			type : "POST",
			url : "login", //æè¡¨åæ°æ®åéå°ajax.jsp
			//data : $("#ajaxFrm").serialize(), //è¦åéçæ¯ajaxFrmè¡¨åä¸­çæ°æ®
			data: {username: document.getElementById("username").value, password: document.getElementById("password").value},
			async : false,
			error : function(request) {
				waidAni();
				if(request.status ==403){
					alert(request.responseText);
				}else
				alert("send failure");
			},
			success : function(info) {
				waidAni();
				//$("#ajaxDiv").html(data); //å°è¿åçç»ææ¾ç¤ºå°ajaxDivä¸­
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
		<form id="ajaxFrm" class="login" >
		UserName:<input type="text" id="username" ><br />
		Password:<input type="password" id="password"><br/><br/>
		<input value="Submit" id="submitinfo" type="button" onClick="doFind()"/> 
		<input class="btn btn-primary btn-lg" value="Delete" type="button" onClick="DelCookie()" style="display:none;"/>
		</form>
<!-- 		<input type="checkbox" name="remember" id="remember" display="none">Remeber Username</input><br /> -->

		<div id="ajaxDiv"></div>
	<div id="loading-div-background">
		<div id="loading-div" class="ui-corner-all">
			<img src="image/animated-gif.gif" id="animated-gif"
				 />
		</div>
	</div>
</body>
</html>