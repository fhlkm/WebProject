<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<title>Check List</title>
<!-- <link rel="stylesheet"  href="css/style2.css"> -->
<style>
form { display: block; margin: 20px auto; background: #eee; border-radius: 10px; padding: 15px }
/* must set the innerHtml */
table{
	border: 1px solid black;
	background: -webkit-gradient(linear, bottom, left 175px, from(#CCCCCC), to(#EEEEEE));
	background: -moz-linear-gradient(bottom, #CCCCCC, #EEEEEE 175px);
	font-size: 20px;
	cellpadding="10";
	cellspacing="10";
	text-align:left;
	bordercolor:#0000ff;
	bordercolorlight:black;
	bordercolordark:black;
}
table,td {
	border: 1px solid black;
	background: -webkit-gradient(linear, bottom, left 175px, from(#CCCCCC), to(#EEEEEE));
	background: -moz-linear-gradient(bottom, #CCCCCC, #EEEEEE 175px);
}
</style>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="theme.css" rel="stylesheet">
<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.8.3.min.js"></script>
<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.3.2.min.js">
	
</script>
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
	window.onload = function fillChecklist() {
		checkUserAndExit();
		var selections = "${sessionScope.selections}";
		var arr_sele = selections.split(";");
		for (var v = 0; v < arr_sele.length; v++) {
			fillForm(arr_sele[v]);
		}
		if (GetCookie("title") != window.manager) {//employee
			document.getElementById("dynadd").style.display = "none";
			document.getElementById("addName").style.display = "none";
			document.getElementById("showinfo").style.display = "none";
			document.getElementById("upload_img").style.display="inline";
			document.getElementById("show_imag_report").style.display="none";
			document.getElementById("input_number").style.display="inline";
			document.getElementById("show_number").style.display="none";
		}else{//manager
			document.getElementById("show_imag_report").style.display="inline";
			document.getElementById("upload_img").style.display="none";
			document.getElementById("input_number").style.display="none";
			document.getElementById("show_number").style.display="inline";
		}
	}

	
	
	function dynamicAdd() {
		var name = document.getElementById("addName");
		var checkbox = document.createElement("input");
		checkbox.type = "checkbox";
		checkbox.name = name.value;
		checkbox.value = name.value;
		checkbox.id = name.value;
		checkbox.class="check";
		checkbox.width = 19 ;
		checkbox.height = 19;

		var label = document.createElement("label");
		label.htmlFor = "id";
		label.appendChild(document.createTextNode(name.value));

		var container = document.getElementById("checklist");
		container.appendChild(checkbox);
		container.appendChild(label);
		container.appendChild(document.createElement("br"));

	}

	function fillForm(name) {
		var checkbox = document.createElement("input");
		checkbox.type = "checkbox";
		checkbox.name = name;
		checkbox.value = name;
		checkbox.id = name;

		var label = document.createElement("label");
		label.htmlFor = "id";
		label.appendChild(document.createTextNode(name));

		var container = document.getElementById("checklist");
		container.appendChild(checkbox);
		container.appendChild(label);
		container.appendChild(document.createElement("br"));

	}
	function addUserName(container) {
		var mtext = document.createElement("input");
		mtext.type = "text";
		var title = GetCookie("title");
		mtext.name = "title";
		mtext.value = title;
		mtext.id = title;
		mtext.style.display = "none";
		container.appendChild(mtext);

	}

	function submitAction() {
		addUserName(document.getElementById("checklist"));
		var elem = document.getElementById("checklist").elements;
		if (GetCookie("title") == window.manager) {
			for (var i = 0; i < elem.length; i++) {
				elem[i].checked = true;
			}
		}
		var form = document.getElementById("checklist");
		//form.submit();
		showInfo("selection",$('#checklist').serialize());
	}

	function showInfo(listUrl, requestdata) {
		waidAni();

		$.ajax({
			cache : false,
			type : "POST",
			dataType : "json",
			url : listUrl,
			//data : $("#ajaxFrm").serialize(), 
			data : requestdata,
			async : false,
			error : function(request) {
				waidAni();
				if(request.status ==202){
					alert(request.responseText);
				}else
				alert("Request Error");
			},
			success : function(data) {
				waidAni();			
				//alert(data);
				var table = document.getElementById("myTable");
				if(table.rows.length == 0)
				dynamicForm(data);
				else{
					var length = table.rows.length;
					for(var j =0;j<length;j++){
						table.deleteRow(0); 
					}
					dynamicForm(data);
				}
				
				
			}
		});
	}



	function showErrorReport(listUrl, requestdata){
		waidAni();

		$.ajax({
			cache : false,
			type : "POST",
			dataType : "json",
			url : listUrl,
			//data : $("#ajaxFrm").serialize(), 
			data : requestdata,
			async : false,
			error : function(request) {
				waidAni();
				if(request.status ==202){
					alert(request.responseText);
				}else
				alert("Request Error");
			},
			success : function(data) {
				waidAni();			
				//alert(data);
				dynamicReport(data);

				
				
			}
		});
		
	}
	
	function dynamicReport(data){
		var table = document.getElementById("erroreport");
		if(table.rows.length==0){
			for(var i=0;i<=data.length;i++){
				if(i==0){
				var row = table.insertRow(0);
				var cell = row.insertCell(0);
				cell.innerHTML = titleName;
				cell = row.insertCell(1);
				cell.innerHTML = titleTime;
				cell = row.insertCell(2);
				cell.innerHTML = titleDescription;
				cell = row.insertCell(3);
				cell.innerHTML = titleImages;
				}else{
					var row = table.insertRow(i);
					var cell = row.insertCell(0);
					cell.innerHTML= data[i-1].Name;
					cell = row.insertCell(1);
					cell.innerHTML = data[i-1].Date;
				    var description = data[i-1].DESCRIPTION;
				    if(description.length>7){
				    	description = description.substring(0,7)+"..."
				    }
				    cell = row.insertCell(2);
				    cell.innerHTML=description;
					var userName = data[i-1].Name;
					var reportTime= data[i-1].Date;
					var url = "selectionShow";
				  //  var p ="<p><u><font  size=\"2\" color=\"blue\" onclick='requestPictures(\""+url+"\",\""+userName+"\",\"" + reportTime + "\")'  style=\"cursor:pointer\">Check Pcitures</font></u></p>";
				  var p ="<p><u><font  size=\"2\" color=\"blue\" onclick='openBrowsePage(\""+userName+"\",\"" + reportTime + "\")'  style=\"cursor:pointer\">Check Pcitures</font></u></p>";
				    cell = row.insertCell(3);
				    cell.innerHTML = p;
				}//end else
				
			}//end for
		}//end if
	}
	
	function openBrowsePage(name, time){
		//var encodeTime = encodeURIComponent(time);
		SetCookie(window.reportPerson,name);
		SetCookie(window.reportTime,time);
		window.open("ShowImages.jsp");
	}

	function dynamicForm(data) {
		var table = document.getElementById("myTable");
		var map = createHashMap(data);
	
	if (table.rows.length == 0) {
			var lists = data[0].Checklists;
			lists = lists.substring(0, lists.length - 1);
			var arrays = lists.split(";");
			var row = table.insertRow(0);
			for (var j = 0; j <= arrays.length+1; j++) {
				if (j == 0) {
					var cell = row.insertCell(0);
					cell.innerHTML = titleTime;
				} else if(j==1){
					var cell = row.insertCell(1);
					cell.innerHTML = titleName;
				}else {
					var cell = row.insertCell(j);
					cell.innerHTML = arrays[j - 2];
				}
			}
		}

		for (var i = 1; i < data.length; i++) {
			var x = document.getElementById("myTable").rows[0].cells.length;
			var row = table.insertRow(i);
			for (var j = 0; j < x; j++) {
				if(j==0){
					var cell = row.insertCell(j);
					cell.innerHTML=data[i].Date;
				}else if(j==1){
					var cell = row.insertCell(j);
					cell.innerHTML=data[i].Name;
				}else{
					var cell = row.insertCell(j);
					cell.innerHTML = "N/A";
				}			
			}
			var lists = data[i].Checklists;
			lists = lists.substring(0, lists.length - 1);
			var arrays = lists.split(";");
			var cells = document.getElementById("myTable").rows[i].cells;

			for (j = 0; j < arrays.length; j++) {
				if(null != map[arrays[j]])
				cells[map[arrays[j]]+2].innerHTML = arrays[j];
			}
		}
	}

	function createHashMap(data) {
		var table = new Object();
		if (data.length > 0) {
			var lists = data[0].Checklists;
			lists = lists.substring(0, lists.length - 1);
			var arrays = lists.split(";");
			for (var i = 0; i < arrays.length; i++) {
				table[arrays[i]] = i;
			}
		}
		return table;

	}
	
// show numbers	
	function showNumber() {
		waidAni();

		$.ajax({
			cache : false,
			type : "POST",
			dataType : "json",
			url : "Parameters",
			//data : $("#ajaxFrm").serialize(), 
			data : {"command":"PARAMETERS_SHOW"},
			async : false,
			error : function(request) {
				waidAni();
				if(request.status ==202){
					alert(request.responseText);
				}else
				alert("Request Error");
			},
			success : function(data) {
				waidAni();			
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
	<h1>Work Report</h1><br/>
	<div class="body-info">
		<input  type="text" id="addName" size="25" maxlength="50"
			value="elevator"> <br/>
		<button class="btn btn-primary btn-sm"  type="button" id="dynadd" onclick="dynamicAdd()" name="add">Add
			New Option</button><br/><br/>
		<form id="checklist" action="selection"></form><br/>
		<button class="btn btn-primary btn-sm" type="button" onclick="submitAction()" name="submit">Submit</button>
		<br /><br/>
		<button class="btn btn-primary btn-sm" type="button" id="showinfo"
			onclick='showInfo("selectionShow",jsonInfo)' name="submit">Show
			Check Log</button><br/><br/>
		<div>
			<table id="myTable"></table>
		</div>
	<div id="loading-div-background">
		<div id="loading-div" class="ui-corner-all" >
			<img src="image/animated-gif.gif" id="animated-gif"
				 />
		</div>
		</div>
		<br />
		<button class="btn btn-primary btn-sm" type=button id="upload_img"
			onclick="window.open('uploadPictures.html')">Upload Pcitures</button>
		<button class="btn btn-primary btn-sm" type=button id="show_imag_report"
			onclick='showErrorReport("selectionShow",jsonReprot)'>Check
			Problems</button>
		<div><br/>
			<table id="erroreport"></table>
		</div>
		<br />

		<button class="btn btn-primary btn-sm" type=button id="input_number"
			onclick="window.open('devicesparameters.jsp')">Input Number</button>
		<br />
		<button class="btn btn-primary btn-sm" type=button id="show_number" onclick="showNumber()">Check
			Number</button>
		<br />
		<div><br/>
			<table id="numbers"></table>
		</div>
		<br />
	</div>
	<!-- 	end of body-info -->
</div>

</body>
</html>