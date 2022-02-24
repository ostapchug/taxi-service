$(function(){
	
	$("#origin_street").change(function(){
		var name = $(this).find(":selected").text();
		var target = $("#origin_street_number");
		setStreetNumbers(name, target);
	});
	
	$("#dest_street").change(function(){
		var name = $(this).find(":selected").text();
		var target = $("#dest_street_number");
		setStreetNumbers(name, target);
	});
	
	function setStreetNumbers(name, target){
		$.ajax({
			  method: "POST",
			  url: "?command=get_street_numbers",
			  data: {street_name: name}
		}).done(function(data) {
			target.prop( "disabled", false);
			target.html(data);
		});
		
	}

});

