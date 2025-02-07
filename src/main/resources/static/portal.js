Get: $(document).ready(
	function(){
		
		//Get Request
		$("#accessportal").click(function(event){
			event.preventDefault();
			ajaxGet();
		});
		
		//Do Get
		function ajaxGet(){
			$.ajax({
				url : "http://localhost:9000/demo/aboutDet",
				success: function(result){
					$("#apiresponse").html(result);
				}
			});
		}
		
		
	}
	
);