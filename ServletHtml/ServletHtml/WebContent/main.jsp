<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Main Menu</title>
<link rel="stylesheet"  href="css/style4.css"> 
<script src="jquery/jquery-1.8.3.min.js"></script>
<script src="jquery/selection.js"></script>
<script language="javascript" type="text/JavaScript" src="cookie.js">
</script>

<script language="javascript" type="text/javascript">
window.onload =  function fillChecklist(){
	checkUserAndExit();
}	
function logout(){
	DelCookie();
	checkUserAndExit();
}
</script>
</head>

<body>
<h1>Work Report <img id="exit" src="image/exit.png" onclick="logout()"></img></h1>    

<div class="wrapper">
    <!--Style one-->
    <div class="sharp color1">
        <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b> 
        <div class="content first">  
        </div>
        <b class="b5"></b><b class="b6"></b><b class="b7"></b><b class="b8"></b> 
		<p class="description">Clean</p>		
    </div>
    
	<!--Style tow-->
    <div class="sharp color2">
        <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b> 
        <div class="content second">  
        </div>
        <b class="b5"></b><b class="b6"></b><b class="b7"></b><b class="b8"></b>  
		<p class="description">Repair</p>			
    </div>    
    
	<!--Style Three-->
    <div class="sharp color3">
        <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b> 
        <div class="content third">  
        </div>
        <b class="b5"></b><b class="b6"></b><b class="b7"></b><b class="b8"></b> 
		<p class="description">Number</p>			
    </div> 
    
    	<!--Styel Four-->
    <div class="sharp color4">
        <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b> 
        <div class="content four">  
        </div>
        <b class="b5"></b><b class="b6"></b><b class="b7"></b><b class="b8"></b>  
        <p class="description">Equipment</p>	  
    </div> 
</div>
</body>
</html>
