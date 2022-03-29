$(function(){
  		
  		var sortButtonName = $("#sort-menu").find(".active").children().html();
  		$("#sort-button").html(sortButtonName);
  		
		$("#byPhone").click(function() {
			 $(this).toggleClass("active");	
			 $("#byDate").attr("class", "");
			 $("#phoneFilter").toggle();
			 $("#dateFilter").hide();
		});
		
		$("#byDate").click(function() {
			$(this).toggleClass("active");	
			$("#byPhone").attr("class", "");
			$("#dateFilter").toggle();
			$("#phoneFilter").hide();
		});
  		
		$("a.accepted, a.completed, a.cancelled").click(function() {
			var form = $(this).parents("form");
			var status = $(this).attr("class");
			form.children('input[name="tripStatus"]').val(status);
			form.submit();
		})
		
		$(".offer").click(function() {
			var value = $(this).val();
			$('input[name="offer"]').val(value);

		})

});

