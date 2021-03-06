<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="">
<head>
	<meta charset="utf-8">
	<title>로그인 페이지</title>
	<link type="text/css" rel="stylesheet" href="resources/css/common/main.css">
    <link type="text/css" rel="stylesheet" href="resources/css/member/login.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
		var ROOTCP = '<%= application.getContextPath()%>';
    </script>
</head>
	<div id="emtpy_space"></div>
	<div id="login_wrap">
		<form action="find_pw_proc.LF" method="post" name="pind_pw">
		    <script>
				var ROOTCP = '<%= application.getContextPath()%>';
				$(document).ready(function() {
					$("#find_id2").click(function() {
						var url = ROOTCP+'/member/find_mb_login.LF';
						$("#login_wrap").load(url, function(){
						});			
					}); // #find_id
				});
		    </script>
			<div id="login_top">
				<h2 id="login_title"><a href="home.LF" class="logo_link login_a"><img src="resources/imges/logo/LecFly_LOGO_B_C.png" ></a></h2>
				<h3 id="login_subtitle2">비밀번호 찾기</h3>
			</div>
			<div id="login_middle2">
				<div id="login_description">
					<h4>비밀번호가 기억나지 않으세요?<br><br>
					회원님의 회원 정보에 등록된 이메일로<br>임시 비밀번호를 발급 받으실 수 있습니다.</h4>
				</div>
				<input type="text" placeholder="이메일을 입력해주세요" name="email" id="find_pw_email">
				<input type="text" placeholder="이름 입력해주세요" name="name" id="find_pw_name">
				<input type="text" placeholder="휴대폰번호를 입력해주세요" name="phNumber" id="find_pw_phone">
				<div id="login_description2">
					<!-- <h4 style="color:orangered">이메일찾기</h4> -->
<!-- 					<a id="find_id2" href="#">아이디찾기</a> -->
					${!empty msg ? msg : ''}
				</div>
			</div>
			<div id="login_bottom">
				<div id="find_login_btn">
					<a href="javascript:document.pind_pw.submit();" class="login_a">임시비밀번호 받기</a>
				</div>
			</div>
		</form>
	</div>
</html>
