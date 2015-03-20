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
var items_selection ="";
window.onload = function fillChecklist() {

		checkUserAndExit();
	
	var selections = "${sessionScope.selections}";
	var arr_sele = selections.split(";");
	items_selection=selections;
	for (var v = 0; v < arr_sele.length; v++) {
		fillForm(arr_sele[v]);
	}
	if (GetCookie("title") != window.manager) {//employee
		document.getElementById("dynadd").style.display = "none";
		document.getElementById("addName").style.display = "none";
		document.getElementById("showinfo").style.display = "none";

	}else{//manager
	}
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


function dynamicAdd() {
	var name = document.getElementById("addName");
	if(null == items_selection.match(name.value)){
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
	}else{
		alert(name.value+" already exists!");
	}

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


</script>
</head>
<body role="document">
	<div class="jumbotron">
		<div class="body-info">
			<input type="text" id="addName" size="25" maxlength="50"
				value="elevator"> <br />
			<button class="btn btn-primary btn-sm" type="button" id="dynadd" onclick="dynamicAdd()" name="add">Add New Option</button><br /><br />
			<form id="checklist" action="selection"></form><br />
			<button class="btn btn-primary btn-sm" type="button" onclick="submitAction()" name="submit">Submit</button><br /><br/>
			<button class="btn btn-primary btn-sm" type="button" id="showinfo" onclick='showInfo("selectionShow",jsonInfo)' name="submit">Show
			Check Log</button><br/><br/>
			<div>
				<table id="myTable"></table>
			</div>
		</div>
		<!-- 		end of body-info -->
	</div>
	<!-- 	end of jumbotron -->
</body>
</html>