//Count up
// (function($){
//      var time = 0;
//      var minutes = 1 * 60 * 1000,
//          second = 1000;
//      var timefinish;
//      var a, b,positons;
//   $.fn.runtime = function () {
//     positions = $(this).attr("id");
//     var options = $.extend({
//       callback  : function(){},
//     });
//           (function loop() {
//               timefinish = time;
//               a = Math.floor(time / minutes);
//               b = Math.floor((time - a * minutes) / second);
//               time = time + 1000;
//               updatedou(positions,a,b);
//               setTimeout(loop, second);
//           })();
//           function updatedou(pos,a,b) {
//               $("#"+pos).text(a+":"+b);
//               options.callback(this);
//           }
//           return this;
//   } 

//   $.fn.gettime = function () {
//     alert(timefinish);
//   }
// }(jQuery));

//Count down
(function($){
	   var fulltime = 30 * 60 * 1000;//30 phút
	   var timefund = fulltime;
       var minutes = 1 * 60 * 1000,
       second = 1000;
       var timefinish;
       var a, b,positons,
           processVal;
	$.fn.runtime = function () {
		positions = $(this).attr("id");
    ui(positions);
		var options = $.extend({
			callback	: function(){},
		});
          (function loop() {
          	if (timefund >= 1) {
          		
                timefinish = fulltime-timefund;

                a = Math.floor(timefund / minutes);
      
                b = Math.floor((timefund - a * minutes) / second);
        
                timefund = timefund - 1000;

       			    updatedou(positions,a,b,fulltime,timefinish);

                setTimeout(loop, second);

            }
            else
            {
                timeover(positions,a,b);
            }

          })();
          function updatedou(pos,a,b,fulltime,timeRun) {
          		$("#title-runtime").text(a+":"+b)
              processVal = 100-(timeRun/fulltime)*100;
              console.log(processVal);
              $("#process").css("width",processVal+"%");
          		options.callback(this);
          }
          return this;
	}
  //Sub fun of fun runtime()
  function ui(pos,fundtime,process) {
    var html = '<div id="timebar">'
               +'<div class="progress progress-bar-danger " id="myprogress">'
               +'<div id="process" class="progress-bar  progress-bar-striped  progress-bar-success active"'
               +'role="progressbar"  style="width:0%">'
               +'</div></div>'
               +'<h3  class="" id="title-runtime"></h3>'
               +'</div>'
     $("#"+pos).append(html);
  }

  function timeover(pos,a,b) {
    swal({
          title: "Time up!",
          text: "Click Finish to complete!",
          icon: "success",
          button: "Finish",
        })
        .then((val)=>{
            alert("chưa viết ");
        })
  }

  //Get time  
	$.fn.gettime = function () {
		alert(fulltime-timefund);

	}
}(jQuery));