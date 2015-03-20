$(document).ready(function(){
  $("div.first").click(function(){
	  
			$.ajax({
				cache : false,
				type : "POST",
				url : "initalSelect", 
				data: "json",
				async : false,
				error : function(request) {
					if(request.status ==403){
						alert(request.responseText);
					}else
					alert("send failure");
				},
				success : function(info) {
					//$("#ajaxDiv").html(data); //Ã¥Â°ÂÃ¨Â¿ÂÃ¥ÂÂÃ§ÂÂÃ§Â»ÂÃ¦ÂÂÃ¦ÂÂ¾Ã§Â¤ÂºÃ¥ÂÂ°ajaxDivÃ¤Â¸Â­
					if(info.status ==403)
					alert(info);
					else{
						//window.location.href= info;
						window.open(info);
					}
				//console.log($("#ajaxFrm").serialize());
				}
			});
		
  });
});


$(document).ready(function(){
	  $("div.second").click(function(){
		  var title = GetCookie("title");
		  if(title == "Manager"){
			  window.open("subrepair.jsp");
		  }else{
			  window.open("uploadPictures.html");
		  }
	
			
	  });
	});

$(document).ready(function(){
	  $("div.third").click(function(){
		  var title = GetCookie("title");
		  if(title == "Manager"){
			  window.open("subcheckparameter.jsp");
		  }else{
			  window.open("devicesparameters.jsp");
		  }
	
			
	  });
	});


$(document).ready(function(){
	  $("div.four").click(function(){
		  var title = GetCookie("title");
		  if(title == "Manager"){
			  window.open("PartsManual.html");
		  }else{
			  window.open("PartsManual.html");
		  }
	
			
	  });
	});