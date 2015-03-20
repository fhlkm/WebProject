
window.manager;
window.manager="Manager";
window.reportPerson="ReportPerson";
window.reportTime="ReportTime";
var jsonInfo={"command":"info"}
var jsonReprot={"command":"problem report"};
function GetCookie (name) 
{  
	var cookies= window.document.cookie.split(";");
	for(var i=0;i<cookies.length;i++){
		if(cookies[i].indexOf(name)!=-1){
		pairs = cookies[i].split("=");
		return pairs[1];
		}
	}
	return null;
}

function getCookieVal (offset)
{ 
	var endstr = window.document.cookie.indexOf (";", offset); 
	if (endstr == -1)
		endstr = window.document.cookie.length; 
	return unescape(window.document.cookie.substring(offset, endstr));
}
function SetCookie (name, value)
{ 
	var exp = new Date(); 
	exp.setTime(exp.getTime() + (30*24*60*60*1000));
	window.document.cookie = name + "=" + escape (value) + "; expires=" + exp.toGMTString()+";path=/";
}
function DeleteCookie (name)
{ 
	var exp = new Date(); 
	exp.setTime (exp.getTime() - 100); 
	var cval = GetCookie (name); 
	window.document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString()+";path=/";
}


function DelCookie()
{
	DeleteCookie("username");
}
function remember()
{
	/*if(document.getElementById("remember").checked){*/
		SetCookie("username",document.getElementById("username").value);
		SetCookie("password",document.getElementById("password").value);
//		alert("Saved!");
	/*}*/	
//	createForm();
}
function showpassword()
{
	 var p=GetCookie(document.getElementById("password").value);
	 if(p!=null)
	document.getElementById("password").value= p;
}
function createForm(){
	 var form = document.createElement("form");
	 form.method="post";
	 form.action="login";
	 var username = document.getElementById("username");
	 var pwd = document.getElementById("password");
	 var hiddenField = document.createElement("input");
     hiddenField.setAttribute("type", "text");
     hiddenField.setAttribute("name","username");
     hiddenField.setAttribute("value",username.value);
     hiddenField.display="none";
     form.appendChild(hiddenField);

	 var hiddenField2 = document.createElement("input");
     hiddenField2.setAttribute("type", "text");
     hiddenField2.setAttribute("name","password");
     hiddenField2.setAttribute("value",pwd.value);
     hiddenField2.display="none";
     form.appendChild(hiddenField2);

/*	 document.body.appendChild(form);*/
	 form.submit();
}
function waidAni(){
    $(function() {
    	$("#loading-div-background").css({ opacity: 0.8 });
             $('#loading-div-background').toggle();
     });
}
function checkUserAndExit(){
	if(GetCookie ("username")==null){
		if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
			location.href="mobileindex.jsp";
		}else{
			location.href="logincookie.jsp";
		}
	}
}

