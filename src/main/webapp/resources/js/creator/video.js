 $().ready(function() {
 function videoChange(){
	var video = $("#viosrc").attr("src");
	if(video == undefined || video == null || video == ""){
	 
		return false;
	} else{
		 
		return true;
	}
}
var upbutton = 0;
function ispty(){
    var each =	$('#viform').find("input[type!=hidden],textarea,select").not("input[type=url]").not("input[type=button]").not("input[type=file]");
    var a = 0;
    for(var i =0 ; i<each.length;i++ ){
    	if(each[i].value == undefined || each[i].value == null || each[i].value ==""){
    		a++;
    	}
    } 
    	if(a>0){
    		alert("값을 다입력해주세요");
    		return true;
    	}else{
    		return false;
    	}
  }

$(window).on("unload",function(e){
	 var nodename = false;
	 $("#unloadz").val("1");
	 var formdata = new FormData($('#viform')[0]);
	 if(e.target.activeElement.nodeName.toLowerCase() == "body"||e.target.activeElement.nodeName.toLowerCase() == "input"){
		   nodename = true;
		 }else{
			 nodename = false;
		 }
	 console.log(e.target.activeElement.nodeName.toLowerCase());
	 if(nodename == false && videoChange() == true && upbutton != 1){
		 $("#uplUP").attr("disabled","disabled");
		 navigator.sendBeacon("video_upload_proc.LF", formdata);
	 }
});
	
		$(".upl_bt").on("click",function(){
			if(!ispty()){
				if(videoChange() == false){
					alert("동영상을 등록해주세요");
				}else{
					upbutton = 1;
			var form = $("#viform");
			$("#uplUP").attr("disabled","disabled");
			$("#imgProc").attr("disabled","disabled");
			form.submit();
			}
			}
		});
	$("#imgProc").change(function () {
		if(videoChange() == false){
			alert("비디오를 먼저 추가해주세요");
		}else{
			var formdataimg = new FormData();
			var fileimg = this.files[0];
			formdataimg.append("addimgFile", fileimg);
			$.ajax({
				type : "post",
				enctype : "multipart/form-data",
				url : "video_img_proc.LF",
				processData : false,
				contentType : false,
				data : formdataimg,
				success : function(result) {
					console.log(result.png);
					$("#img1").attr("src","${crPath}${video.imgPath}");
					 
				},
				error : function() {

				}
			});
		}
	});
		$("#uplUP").change(function() {
			$("#vioSample").attr("poster","resources/imges/creator/loading.gif");
			var formdata = new FormData();
			var file = this.files[0];
			formdata.append("viFile", file);
			$.ajax({
				type : "post",
				enctype : "multipart/form-data",
				url : "video_proc.LF",
				processData : false,
				contentType : false,
				data : formdata,
				dataType:"json",
				success : function(result) {
					
					var cr = result.cr ,
					  vi = result.vi,
					  png = result.png ,
					 gif =result.gif ,
					 video =result.video ,
					videoChange = 1;
					var ims = png.split('-');
					console.log(result.video);
					$("#img1").attr("src",cr+ims[0] );
					$("#img2").attr("src",cr+ims[1] );
					$("#gif").attr("src",cr+gif );
					$("#viosrc").attr("src",vi+video );
					$("#vioSample")[0].load();
					$("#vioSample").attr("poster","");
					 
				},
				error : function() {

				}
			});
		});
	});