<%@ page language="java" import=" com.google.gson.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<meta charset="utf-8">
<title>Elan Condominium Management Company</title>

<!-- Shared assets -->
<link rel="stylesheet" type="text/css" href="css/style2.css">

<!-- Example assets -->
<link rel="stylesheet" type="text/css" href="css/jcarousel.basic.css">

<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="jquery/jquery.jcarousel.min.js"></script>

<script type="text/javascript" src="jquery/jcarousel.basic.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script language="javascript" type="text/javascript">
window.onload =  function fillChecklist(){
	checkUserAndExit();
}	
		
		function addimag(array){
		for(var i=0;i<array.length;i++){
			var index = array[i].PICTURE.indexOf("pictures");
			var directory= array[i].PICTURE.substring(index,array[i].PICTURE.length);
		$('#carousel').append('<li><a href=\"#\"><img src=\"'+directory+'\"   width=\"1200\" height=\"1800\" alt=\"\"/></a></li>');
		}
		}
		
		function addDescription(data){
			var description = document.getElementById("description");
			description.innerHTML = data.DESCRIPTION;
		}
		function addpictures(array){
			var frame = document.getElementById("show_pictures");
			var host="http://www.condomaintenance360.com/";
	
			for(var i=0;i<array.length;i++){
				var index = array[i].PICTURE.indexOf("pictures");
// 				var directory=host+ array[i].PICTURE.substring(index,array[i].PICTURE.length);// change it when publish on server
				var directory=array[i].PICTURE.substring(index,array[i].PICTURE.length);
				var node = document.createElement("div");
			    directory= directory.split('\\').join('/');
			    directory=host+directory;
				directory = '<img    class="inline" src='+directory+'   width="800" height="600" alt="\"/><br/><br/>';
				$('#lightwindow_info_tab_span')
	              .prepend(directory);
				//node.innerHTML = '<img src='+directory+'   width="800" height="600" alt="\"/><br/><br/>';
				frame.insertBefore(node);
			}
		}
	//	$('#description').append("");
		</script>
<script language="javascript" type="text/JavaScript" src="cookie.js">	
</script>

<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.3.2.min.js">
</script>

<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.8.3.min.js"></script>

<script language="javascript" type="text/javascript">

	
	window.onload = function requestPictures() {
		var name = GetCookie(window.reportPerson);
		var time = GetCookie(window.reportTime);
		time =unescape(time); 
		//time = decodeURIComponent(time);
		var listUrl="selectionShow";
		$.ajax({
			cache : false,
			type : "POST",
			dataType : "json",
			url : "selectionShow",
			data : {command : "images","name" : name,"time" : time},
			async : false,
			error : function(request) {
				waidAni();
				if (request.status == 202) {
					alert(request.responseText);
				} else
					alert("Request Error");
			},
			success : function(data) {
				var pictures = data.PICTURES;
				addDescription(data);
				addpictures(pictures);
				
			}
		});

	}
</script>
</head>
<body>
<!-- 	<div class="wrapper"> -->

<!-- 		<h1 id="description"></h1> -->



<!-- 		<div class="jcarousel-wrapper"> -->
<!-- 			<div class="jcarousel"> -->
<!-- 				<ul id="carousel"> -->

<!-- 				</ul> -->
<!-- 			</div> -->

<!-- 			<p class="photo-credits"> -->
<!-- 				Photos by <a href="http://http://www.enlan.com/"> Elan -->
<!-- 					Condominium</a> -->
<!-- 			</p> -->

<!-- 			<a href="#" class="jcarousel-control-prev">&lsaquo;</a> <a href="#" -->
<!-- 				class="jcarousel-control-next">&rsaquo;</a> -->

<!-- 			<p class="jcarousel-pagination"></p> -->
<!-- 		</div> -->
	
<!-- 	</div> -->

	<div id="show_pictures" align="center">
	<p id="description"></p>
	<span id="lightwindow_info_tab_span" class="up"></span>
	</div>

</body>
</html>