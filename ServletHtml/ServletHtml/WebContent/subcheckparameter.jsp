<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<link rel="stylesheet"  href="css/style3.css">
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="theme.css" rel="stylesheet">
<script language="javascript" type="text/JavaScript"
src="jquery/jquery-1.8.3.min.js"></script>
<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.7.1.js"></script>
<script language="javascript" type="text/JavaScript" src="cookie.js"></script>
<script language="javascript" type="text/javascript">
var titleTime ="Time";
var titleName="Name";
var titleDescription ="Description";

var titleImages="Images";
// show numbers	
	window.onload = function getdata()  {

			checkUserAndExit();
		

		$.ajax({
			cache : false,
			type : "POST",
			dataType : "json",
			url : "Parameters",
			//data : $("#ajaxFrm").serialize(), 
			data : {"command":"PARAMETERS_SHOW"},
			async : false,
			error : function(request) {
				
				if(request.status ==202){
					alert(request.responseText);
				}else
				alert("Request Error");
			},
			success : function(data) {
					
				showNumbers(data);
				
				
				
			}
		});
	}
	
	
	// data={Manager:{}, Employee:[{},{}]}
	function showNumbers(data) {
		var table = document.getElementById("numbers");
		var title = data.MANAGER;
		if(table.rows.length==0){
			var titles = title.split(";");
			var row = table.insertRow(0);
			for(var j =0;j<titles.length+1;j++){
				if(j==0){
					var cell = row.insertCell(0);
					cell.innerHTML = titleTime;
				}else if(j==1){
					var cell = row.insertCell(1);
					cell.innerHTML = titleName;	
				}else{
					var cell = row.insertCell(j);
					cell.innerHTML = titles[j-2];
				}
			}// end for
			
			var list = data.EMPLOYEE;
			var firstRowCells = document.getElementById("numbers").rows[0].cells;
			var x = firstRowCells.length;
			for(j =0;j<list.length;j++){
				
				var row = table.insertRow(1);
				for(var i=0;i<x;i++){// the number of columns
					if(i==0){
						var cell = row.insertCell(i);
						cell.innerHTML=list[j].Date;
					}else if(i==1){
						var cell = row.insertCell(i);
						cell.innerHTML=list[j].Name;
					}else{
						var cell = row.insertCell(i);
						var title = (firstRowCells[i].innerHTML);
						var param= list[j].parameter;
						var json = $.parseJSON(param);
						//var json = list[j].parameter.substring(0,list[j].parameter.length-1);
						cell.innerHTML = json[title];
					}
				}// end for i
			}// end for j 

		}// end if
	
	}// end function
	
	
</script>
</head>

<body role="document">
	<div class="jumbotron">
		<div class="body-info">
		<table id="numbers"></table>
<!-- 		end of body-info -->
		</div>
		<!-- end of jumbotron -->
	</div>
	<!-- end of document -->
</body>

</html>