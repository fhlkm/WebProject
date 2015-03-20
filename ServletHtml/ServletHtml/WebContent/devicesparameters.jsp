<%@ page language="java" import=" com.google.gson.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"  href="css/stylecollect.css">
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<script language="javascript"  type="text/JavaScript"  src="cookie.js">
</script>
<script language="javascript" type="text/JavaScript"
	src="jquery/jquery-1.8.3.min.js"></script>
<script language="javascript" type="text/javascript">

	window.onload = function fillParameters() {
		checkUserAndExit();
		$.ajax({
			cache : false,
			type : "POST",
			dataType : "json",
			url : "Parameters",
			//data : $("#ajaxFrm").serialize(), 
			data : {
				"command" : "Parameters"
			},
			async : false,
			error : function(request) {
				
				if (request.status == 202) {
					alert(request.responseText);
				} else if (request.status == 200) {
					add(request);
				} else
					alert("Request Error");
			},
			success : function(data) {
				
				add(data);
			}
		});

	}

	function allowChars(oTextbox,oEvent){
		oEvent = EventUtil.formatEvent(oEvent);
		var sValidChars = oTextbox.getAttribute("validchars");
		var sChar = String.fromCharCode(oEvent.charCode);
		var bisValidChar = sValidChars.indexOf(sChar)>-1;
		return bisValidChar || oEvent.ctrlKey;
		
	}
	function add(data) {
		var titles = data.responseText;

		var form = document.getElementById("parameters");
		var title = titles.split(";");
		for (var i = 0; i < title.length - 1; i++) {
			var p = '<p>test: <input type=\"text\" name=\"fname\" /></p>';
			var p = document.createElement('p');
			var input = document.createElement('input');
			input.type = "text";
			input.name = title[i];
			input.validchars=".1234567890";
			input.onkeypress="return allowChars(this,event)";
			var node = document.createTextNode(title[i]);
			p.appendChild(node);
			p.appendChild(input);

			form.insertBefore(p, document.getElementById("submit"));
		}
	}
</script>

<script language="javascript" type="text/javascript">
        // wait for the DOM to be loaded 
  // this is the id of the form
  
  $(document).ready(function() {

	// process the form
	$('form').submit(function(event) {

		// get the form data
		// there are many ways to get this data using jQuery (you can use the class or id also)

		// process the form
		waidAni();
					$.ajax({
				type : "POST",
				url : "Parameters",
				data : $("#parameters").serialize(), // serializes the form's elements.
				error : function(data) {
					waidAni();
					alert(data.responseText);
				},
				success : function(data) {
					waidAni();
					alert(data); // show response from the php script.
				}
			});
/* 		$.ajax({
			type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
			url 		: 'Parameters', // the url where we want to POST
			data 		: $("#parameters").serialize(), // our data object
			dataType 	: 'json', // what type of data do we expect back from the server
            encode          : true
		})
			// using the done promise callback
			.done(function(data) {

				// log data to the console so we can see
				console.log(data); 

				// here we will handle errors and validation messages
			}); */

		// stop the form from submitting the normal way and refreshing the page
		event.preventDefault();
	});

});


// 		$("parameters").submit(function(ev) {
// 			ev.preventDefault();

// 			$.ajax({
// 				type : "POST",
// 				url : "Parameters",
// 				data : $("#parameters").serialize(), // serializes the form's elements.
// 				error : function(data) {
// 					alert(data.responseText);
// 				},
// 				success : function(data) {
// 					alert(data.responseText); // show response from the php script.
// 				}
// 			});

// 			// ev.preventDefault(); // avoid to execute the actual submit of the form.
// 		});
        
	</script> 
    
</head>
<body role="document">
	<div class="jumbotron">

		<form action="" method="post" id="parameters">
			<!--   <p>First name: <input type="text" name="fname" /></p> -->
			<!--   <p>Last name: <input type="text" name="lname" /></p> -->
			<input id="submit" type="submit" value="Submit" />
		</form>

		<div id="loading-div-background">
			<div id="loading-div" class="ui-corner-all">
				<img src="image/animated-gif.gif" id="animated-gif" />
			</div>
		</div>
	</div>
</body>

</html>
