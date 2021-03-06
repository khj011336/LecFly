<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="resources/js/creator/video.js"></script>
<script>
var isUpdate = "${isUpdate}";
$(window).on("unload",function(e){
	 var nodename = false;
	 $("#unloadz").val("1");
	 var formdata = new FormData($('#viform')[0]);
	 if(e.target.activeElement.nodeName.toLowerCase() == "body"||e.target.activeElement.nodeName.toLowerCase() == "input"){
		   nodename = true;
		 }else{
			 nodename = false;
		 }
	 if(nodename == false && videoChange()  && upbutton != 1 && isUpdate !=5){
		 $("#uplUP").attr("disabled","disabled");
		 navigator.sendBeacon("video_upload_proc.LF", formdata);
	 }
});
	
</script>
<div id='uplwrap'>

	<div class="uplPa" id="uplz">동영상 업로드 -</div>
	<div class="uplPa" id='uply'>동영상 정보</div>
	
	<c:set var="action" value="video_upload_proc.LF"/>
	<c:if test="${isUpdate == 5}">
	<c:set var="action" value="video_update_proc.LF"/>
	</c:if>
	<form action="${action}" method="post"
		enctype="multipart/form-data" id="viform">
		<input type="hidden" value="0" name = "unloadz" id = "unloadz">
		<input type="hidden" value="${video.CFId}" name="CFId">
		<div id="uplT" class="uplflex">
			<div id="uplL" class="uplPa">
				<div id="uplmar" class='uplshadow'>
					<input type="text" value="${video.title}" size="85" name="title"
						placeholder="영상제목을 입력해주세요">
				</div>
				<div class='uplshadow'>
					<textarea class="uplnotes" cols="83" rows="6"
						placeholder="영상 내용을 입력해주세요." name="info">${video.info}</textarea>
				</div>
			</div>
			<div id="uplR">
				<div class='uplshadow'>
					<video controls id="vioSample">
						<source src="${viPath}${video.videoPath}" type='video/mp4' id="viosrc" />
					</video>
					<div>
						<input type="url"
							placeholder="http://creator/resource/video/wildlife.mp4">
					</div>
					<div id="urlvt">
						<input type="file" accept="video/*" id="uplUP" name="viFile">
					</div>
				</div>
			</div>
		</div>
		<div id="uplb" class="uplflex">
			<div class="uplPa">
				<h3>미리보기 이미지</h3>
				<div id="uplimgbox" class="uplflex">
					<div class="uplimgb uplshadow">
						<img alt="" src="${crPath }${fn:split(video.imgPath,'-')[0] }" id='img1'>
					</div>
					<div class="uplimgb uplshadow padp">
						<img alt="" src="${crPath }${fn:split(video.imgPath,'-')[1] }" id='img2'>
					</div>
					<div class="uplimgb uplshadow padp">
						<img alt="" src='${crPath }${video.gifPath }' id="gif">
					</div>
					<div id="uplfile">
						<input type="file" accept="image/*" id="imgProc" name="addimgFile">
					</div>

				</div>
			</div>
			<div id='uplmr' class="uplPa">
				<h3>저장 카테고리 및 댓글허용</h3>
				<div id="uplRcate" class="uplflex">
					<div>
						<p>저장 카테고리</p>
						<select name="category">
							<option value="${video.category}">${categ[video.category]}</option>
						</select>
					</div>
					<div>
						<p>게시 동영상댓글</p>
						<select name="commentYorN">
							<option value="1">허용</option>
							<option value="2">차단</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div id='uplend' class="uplPa">
			<div id="upltextarea">
				<h3>진행순서 기입</h3>
				<textarea class="uplnotes uplshadow " name="orderInfo" placeholder="강의진행순서를 순서대로 나열해주세요">${video.orderInfo}</textarea>
			</div>
		</div>
		<div>
			<input type="button" value="업로드" class="upl_bt">
		</div>
	</form>
</div>
