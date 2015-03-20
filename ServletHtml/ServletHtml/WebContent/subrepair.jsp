<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"  href="css/style3.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="theme.css" rel="stylesheet">
<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.8.3.min.js"></script>
<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.7.1.js">
</script>
<script language="javascript" type="text/JavaScript" src="cookie.js">
</script>

<script language="javascript" type="text/javascript">
var titleTime ="Time";
var titleName="Name";
var titleDescription ="Description";
var titleImages="Images";
	window.onload = function getdata() {

			checkUserAndExit();
	
		$.ajax({
			cache : false,
			type : "POST",
			dataType : "json",
			url : "selectionShow",
			//data : $("#ajaxFrm").serialize(), 
			data : jsonReprot,
			async : false,
			error : function(request) {
				if (request.status == 202) {
					alert(request.responseText);
				} else
					alert("Request Error");
			},
			success : function(data) {
				//alert(data);
				dynamicReport(data);

			}
		});
	}

	function openBrowsePage(name, time){
		//var encodeTime = encodeURIComponent(time);
		SetCookie(window.reportPerson,name);
		SetCookie(window.reportTime,time);
		window.open("ShowImages.jsp");
	}
	function dynamicReport(data) {
		var table = document.getElementById("erroreport");
		if (table.rows.length == 0) {
			for (var i = 0; i <= data.length; i++) {
				if (i == 0) {
					var row = table.insertRow(0);
					var cell = row.insertCell(0);
					cell.innerHTML = titleName;
					cell = row.insertCell(1);
					cell.innerHTML = titleTime;
					cell = row.insertCell(2);
					cell.innerHTML = titleDescription;
					cell = row.insertCell(3);
					cell.innerHTML = titleImages;
				} else {
					var row = table.insertRow(i);
					var cell = row.insertCell(0);
					cell.innerHTML = data[i - 1].Name;
					cell = row.insertCell(1);
					cell.innerHTML = data[i - 1].Date;
					var description = data[i - 1].DESCRIPTION;
					if (description.length > 7) {
						description = description.substring(0, 7) + "..."
					}
					cell = row.insertCell(2);
					cell.innerHTML = description;
					var userName = data[i - 1].Name;
					var reportTime = data[i - 1].Date;
					var url = "selectionShow";
					//  var p ="<p><u><font  size=\"2\" color=\"blue\" onclick='requestPictures(\""+url+"\",\""+userName+"\",\"" + reportTime + "\")'  style=\"cursor:pointer\">Check Pcitures</font></u></p>";
					var p = "<p><u><font  size=\"2\" color=\"blue\" onclick='openBrowsePage(\""
							+ userName
							+ "\",\""
							+ reportTime
							+ "\")'  style=\"cursor:pointer\">Check Pcitures</font></u></p>";
					cell = row.insertCell(3);
					cell.innerHTML = p;
				}//end else

			}//end for
		}//end if
	}
</script>





	
</head>
<body role="document">
	<div class="jumbotron">
		<div class="body-info">
		<table id="erroreport"></table>
<!-- 		end of body-info -->
		</div>
		<!-- end of jumbotron -->
	</div>
	<!-- end of document -->
</body>
</html>