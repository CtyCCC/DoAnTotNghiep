
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
 			$('#example1 tbody').find('tr').addClass('selected');
    		$('#example1 tbody').find('td').attr("style", "background-color: #E8E8E8");
    	}
    	else{
    		$('#example1 tbody').find('tr').removeClass('selected');
    		$('#example1 tbody').find('td').removeAttr('style');
    		$('#btnExport').attr('disabled','true');
 			$('#btnDelete').attr('disabled','true');
 			$('#btnSetInterview').attr('disabled','true');
    	}
	});
	ckbox2.change(function () {
    	$("input:checkbox.checkk").prop('checked',this.checked);
    	ckbox.prop('checked',this.checked);
    	if (ckbox.is(':checked') || ckbox2.is(':checked')){
    		$('#btnExport').removeAttr('disabled');
 			$('#btnDelete').removeAttr('disabled');
 			$('#btnSetInterview').removeAttr('disabled');
 			$('#example1 tbody').find('tr').addClass('selected');
    		$('#example1 tbody').find('td').attr("style", "background-color: #E8E8E8");
    	}
    	else{
    		$('#example1 tbody').find('tr').removeClass('selected');
    		$('#example1 tbody').find('td').removeAttr('style');
    		$('#btnExport').attr('disabled','true');
 			$('#btnDelete').attr('disabled','true');
 			$('#btnSetInterview').attr('disabled','true');

    	}
	});
    //checkbox all
	
	//bấm vào row checkbox là nó check, vì cái checkbox quá nhỏ, khó bấm
	var ckColum = $("input:checkbox.checkk")
	$('#example1 tbody').on( 'click', 'tr', function () {
		var last = $(this).find('td:last-child');
		var ck = last.find('input:checkbox.checkk');
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            $(this).find('td').removeAttr('style');
            ck.prop('checked',false);
            
        }
        else {
            $(this).addClass('selected');
            $(this).find('td').attr("style", "background-color: #E8E8E8");
        	ck.prop('checked',true);

        }
        if(ckColum.is(':checked')){
			$('#btnExport').removeAttr('disabled');
 			$('#btnDelete').removeAttr('disabled');
 			$('#btnSetInterview').removeAttr('disabled');
			}
		else {
			$('#btnExport').attr('disabled','true');
	 		$('#btnDelete').attr('disabled','true');
	 		$('#btnSetInterview').attr('disabled','true');
		}
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
		$("#btn_saveProfile").removeAttr('disabled');
		$("#upload-photo").removeAttr('disabled');
		$("#upload-cv").removeAttr('disabled');
	});

	

	
});