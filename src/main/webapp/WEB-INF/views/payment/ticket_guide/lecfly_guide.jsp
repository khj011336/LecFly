<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>TICKET/LECFLY</title>
<link type="text/css" rel="stylesheet" href="resources/css/CScenter/CSCenter.css">
<link type="text/css" rel="stylesheet" href="resources/css/CScenter/receive_board.css">
<div class="CSsection">
	<div id="CSsec_title">
		<h2>홈페이지 안내</h2>
	</div>
	<div id="CSsec_subtitle">
		<ul>
			<li><h4>
					<a href="lecfly_guide.LF" class="lecfly_info">이용안내</a>
				</h4></li>
			<li><h4>
					<a href="lecfly_ticket.LF" style="background-color: orange"
						class="lecfly_membership_info">회원권</a>
				</h4></li>
		</ul>
	</div>

	<div class="lecflyticket">
		<div id="lecflyis_logo">
			<br>
			<br>
			<h2>
				<img src="resources/imges/logo/LecFly_LOGO_B_C.png">
			</h2>
		</div>
		<div>
			<br>
			<br>
		</div>
		<div id="lecflyticket_maintitle">
			<h1>회원권 안내</h1>
		</div>
		<div id="lecflyticket_noticebox">
			Lecfly는 카테고리별 회원권으로 이용가능합니다.<br> 한개의 회원권으로 카테고리 내 모든 강의를
			수강가능합니다.<br>
			<br> 카테고리 종류 : 음악, 운동, 요리, 미술, 라이프스타일, 커리어, 여행


			<div class="ticket_category">
				<c:forEach items="${categoryMapTicket }" var="tc" varStatus="vs">
					<div class="ticket_category_block">
						<a
							href="${pageContext.request.contextPath}/search_category.LF?category=${tc.key}"
							onfocus>
							<div id="category_icon">
								<img src="resources/imges/category/카테고리_${tc.value}.png"
									class="category_1" alt="${ctc.value}">
							</div> <span><c:out value="${tc.value}" /></span>
						</a>
					</div>
				</c:forEach>
			</div>

		</div>


		<div id="lecflyticket_box">
			<span style="color: gray;"> <i class="fas fa-crown fa-5x"></i>
			</span><br>
			<br>
			<br>
			<h3>
				<span style="background-color: lightgray">1 카테고리 회원권</span>
			</h3>
			<br>
			<h4>
				<span>12,900원 / 월</span>
			</h4>
			<br> 7개의 카테고리 중<br>한가지를 선택하여<br>해당 카테고리에 한해<br>무제한으로
			이용 가능합니다.<br>
			<div id="lecflyticket_gopay">
				<h4>
					<a class="lecflyticket" href="#">바로가기&nbsp;&gt;</a>
				</h4>
			</div>
		</div>

		<div id="lecflyticket_box">
			<span style="color: gray;"> <i class="fas fa-list-ol fa-5x"></i>
			</span><br>
			<br>
			<br>
			<h3>
				<span style="background-color: lightgray">3 카테고리 회원권</span>
			</h3>
			<br>
			<h4>
				<span>30,900원 / 월</span>
			</h4>
			<br> 7개의 카테고리 중<br>세가지를 선택하여<br>해당 카테고리에 한해<br>무제한으로
			이용 가능합니다.
			<div id="lecflyticket_gopay">
				<h4>
					<a href="#" class="nav_mypage">바로가기&nbsp;&gt;</a>
				</h4>
			</div>
		</div>

		<div id="lecflyticket_box">
			<span style="color: gray;"> <i class="fas fa-infinity fa-5x"></i>
			</span><br>
			<br>
			<br>
			<h3>
				<span style="background-color: lightgray">무제한 회원권</span>
			</h3>
			<br>
			<h4>
				<span>49,900원 / 월</span>
			</h4>
			<br> Lecfly에 업로드된<br>모든 카테고리 강의를<br>무제한으로 이용 가능합니다.<br>&nbsp;
			<div id="lecflyticket_gopay">
				<h4>
					<a href="#">바로가기&nbsp;&gt;</a>
				</h4>
			</div>
		</div>
	</div>
</div>