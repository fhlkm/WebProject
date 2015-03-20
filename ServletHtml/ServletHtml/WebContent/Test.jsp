<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.8.3.min.js"></script>
<script language="javascript" type="text/JavaScript">

$.ajax({
	cache : false,
	type : "POST",
	url : "test", //æè¡¨åæ°æ®åéå°ajax.jsp
	//data : $("#ajaxFrm").serialize(), //è¦åéçæ¯ajaxFrmè¡¨åä¸­çæ°æ®
	data: {username: "user"},
	async : false,
	error : function(request) {

		if(request.status ==403){
			alert(request.responseText);
		}else
		alert("send failure");
	},
	success : function(info) {
		//$("#ajaxDiv").html(data); //å°è¿åçç»ææ¾ç¤ºå°ajaxDivä¸­
		if(info.status ==403)
		alert(info);
		else{
			window.location.href= info;
		}
	//console.log($("#ajaxFrm").serialize());
	}
});

</script>
</head>
<body>
	<button type="button" data-inline="false" value="Login" onClick="test()">Test
</body>
</html>
