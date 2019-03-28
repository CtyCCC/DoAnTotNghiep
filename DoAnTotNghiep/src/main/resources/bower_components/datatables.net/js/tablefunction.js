
$(document).ready(function () {



	//action button form candidate info(title)
	$('#btnInterview').on('click', function(){
		$("#formInterview").addClass("in");
		$("#formInterview").attr("style","height: auto;")
		$("#interviewCollapse").find("i").attr('class',"fa fa-angle-down pull-right")
	});
	$('#btnOffer').on('click', function(){
		$("#formOffer").addClass("in");
		$("#formOffer").attr("style","height: auto;")
		$("#offerCollapse").find("i").attr('class',"fa fa-angle-down pull-right")
	});
	$('#btnProbation').on('click', function(){
		$("#formProbation").addClass("in");
		$("#formProbation").attr("style","height: auto;")
		$("#probationCollapse").find("i").attr('class',"fa fa-angle-down pull-right")
		
	});
	//action button form candidate info
	$("#interviewCollapse").on("click",function () {
		$("#interviewCollapse").find("i").toggleClass('fa-angle-right')
										 .toggleClass('fa-angle-down');
	})
	$("#offerCollapse").on("click",function () {
		$("#offerCollapse").find("i").toggleClass('fa-angle-right')
									 .toggleClass("fa-angle-down");
	})
	$("#probationCollapse").on("click",function () {
		$("#probationCollapse").find("i").toggleClass('fa-angle-right')
										 .toggleClass("fa-angle-down");
	})
	//update value label CV to file name(old candidate) 
	$('#upload-cv').change( function(event){
		 var fileName = event.target.files[0].name;
            $("#changeCV").text(fileName);
    });
    //update value label CV to file name(add new candidate)
    $('#upload-cv-new').change( function(event){
		 var fileName = event.target.files[0].name;
            $("#changeCV_New").text(fileName);
    });

	//up load profile picture (candidate)
	$('#upload-photo').change( function(event) {
		var tmppath = URL.createObjectURL(event.target.files[0]);
    	$("img[id=hinh]").fadeIn("fast").attr('src',tmppath);       
	});
	// $('#upload-photo').on('change',function (){
	//        var filePath = $(this).val();
	//    });
	//up load profile picture (admmin)
	$('#upload-photoAd').change( function(event) {
		var tmppath = URL.createObjectURL(event.target.files[0]);
    	$("img[id=hinhAd]").fadeIn("fast").attr('src',tmppath);
    	$("img[id=adminSideBar]").fadeIn("fast").attr('src',tmppath); 
    	$("img[id=adminHeader]").fadeIn("fast").attr('src',tmppath);        
	});

	

	//checkbox all
    var ckbox = $('#checkAllHeader');
    var ckbox2 = $('#checkAllFooter');
   	ckbox.change(function () {
    	$("input:checkbox.checkk").prop('checked',this.checked);
    	ckbox2.prop('checked',this.checked);
    	if (ckbox.is(':checked') || ckbox2.is(':checked')){
    		$('#btnExport').removeAttr('disabled');
 			$('#btnDelete').removeAttr('disabled');
 			$('#btnSetInterview').removeAttr('disabled');
 			$('td').attr("style", "background-color: #E8E8E8");
    	}
    	else{
    		$('#btnExport').attr('disabled','true');
 			$('#btnDelete').attr('disabled','true');
 			$('#btnSetInterview').attr('disabled','true');
 			$('td').removeAttr('style');
    	}
	});
	ckbox2.change(function () {
    	$("input:checkbox.checkk").prop('checked',this.checked);
    	ckbox.prop('checked',this.checked);
    	
    	if (ckbox.is(':checked') || ckbox2.is(':checked')){
    		$('#btnExport').removeAttr('disabled');
 			$('#btnDelete').removeAttr('disabled');
 			$('#btnSetInterview').removeAttr('disabled');
 			$('td').attr("style", "background-color: #E8E8E8");
    	}
    	else{
    		$('#btnExport').attr('disabled','true');
 			$('#btnDelete').attr('disabled','true');
 			$('#btnSetInterview').attr('disabled','true');
 			$('td').removeAttr('style');
    	}
	});
    //checkbox all
	
	//bấm vào row checkbox là nó check, vì cái checkbox quá nhỏ, khó bấm
	var ckColum = $("input:checkbox.checkk")
	$("#example1 tbody tr").click(function(event) {
		if(event.target.tagName == "TD"){
			var last = $(this).find('td:last-child');
			var ck = last.find('input:checkbox.checkk');
			if(ck.is(':checked')){
				ck.prop('checked',false);
			}
			else {
				ck.prop('checked',true);
				$(this).addClass('selected');
			}
			if(ckColum.is(':checked')){
				$('#btnExport').removeAttr('disabled');
 				$('#btnDelete').removeAttr('disabled');
 				$('#btnSetInterview').removeAttr('disabled');
 				$(this).attr("style", "background-color: #E8E8E8");
			}
			else {
				$('#btnExport').attr('disabled','true');
	 			$('#btnDelete').attr('disabled','true');
	 			$('#btnSetInterview').attr('disabled','true');
	 			$(this).removeAttr('style');
			}
		}
		if (event.target.tagName == "INPUT") {
			
			ckColum.change(function() {
				if(ckColum.is(':checked')){
					$('#btnExport').removeAttr('disabled');
 					$('#btnDelete').removeAttr('disabled');
 					$('#btnSetInterview').removeAttr('disabled');
 					$(this).attr("style", "background-color: #E8E8E8");
				}
				else {
					$('#btnExport').attr('disabled','true');
		 			$('#btnDelete').attr('disabled','true');
		 			$('#btnSetInterview').attr('disabled','true');
		 			$(this).removeAttr('style');
				}
			});
		}
	});

	//Select row
	$("#example1 tbody tr").click(function () {
		var last = $(this).find('td:last-child');
		var ck = last.find('input:checkbox.checkk');
		if (ck.is(':checked'))
			$(this).find('td').attr("style", "background-color: #E8E8E8");
		else
			$(this).find('td').removeAttr('style');
    });

    $("#jobDecrt tbody tr").click(function () {
		$('.selected').removeClass('selected');
	    $(this).addClass("selected");

	    //hiện toggle khi click vào row modal JD
	    $(this).attr('data-toggle','collapse');
	    $(this).attr('data-target','#formAdd');
    });

    //select row
	
	//Change color status
    $("ul li a").click(function(){
		var color = $(this).attr("style");
		$("#status").attr("style", "font-weight:bold; "+color);
	});
	$("ul li a").click(function(){
		var color = $(this).attr("style");
		$("#status2").attr("style", "font-weight:bold; "+color);
	  });


	//Change value status
	$("ul li a").click(function(){
		var value =  $(this).parent().attr("value");
		$("#status").attr("value",value);
	  });	
	$("ul li a").click(function(){
		var value =  $(this).parent().attr("value");
	    $("#status2").attr("value",value);
	});

	//button Edit 
	$("#btn_edit").click(function(event) {
		$('#btnSelectSTT').removeAttr('disabled');
		$("#name").removeAttr('readonly');
		$("#email").removeAttr('readonly');
		$("#phone").removeAttr('readonly');
		$("#position").removeAttr('disabled');
		$("#university").removeAttr('readonly');
		$("#gender").removeAttr('disabled');
		$("#source").removeAttr('readonly');
		$("#work_exp").removeAttr('readonly');
		$("#skill").removeAttr('readonly');
		$("#datepicker_Date").removeAttr('disabled');
		$("#datepicker_DOB").removeAttr('disabled');
	});

	

	
});