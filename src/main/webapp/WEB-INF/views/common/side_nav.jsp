<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
function goCart() {
	$("#homemain").load('${pageContext.request.contextPath}' + '/show_cart.LF');
	};
		</script>
<ul class="side-menu">
	<!-- <li><a href="#">&nbsp;</a></li>
	<li><a href="#"><span class="fa fa-code"></span>LECFLY</a></li> -->
	<li><a href="lecfly_guide.LF" class="nav_lecfly_info"><span class="fas fa-bars"></span>LECFLY</a></li>
	<li><a href="#" class="nav_video_list"><span class="fas fa-play-circle"></span>강의목록</a></li>
	<!-- <li><a href="funding_list.LF" class="nav_funding"><span class="fas fa-funnel-dollar"></span>펀딩목록</a></li> -->
	<!-- <li><a href="#" class="nav_wish_list"><span class="fas fa-heartbeat"></span>관심클래스</a></li> -->
	<li><a href="#" onclick="goCart()" class="nav_cart"><span class="fas fa-shopping-cart"></span>장바구니</a></li>
	<li><a href="mypage.LF" class="nav_mypage"><span class="fas fa-user"></span>마이페이지</a></li>
	<li><a href="creator.LF" ><span class="fas fa-chalkboard-teacher"></span>크리에이터센터</a></li>
	<li><a href="cs_qna.LF" class="nav_cs"><span class="fas fa-headset"></span>고객센터</a></li>
	<!-- <li><a href="admin.LF" class="nav_setting"><span class="fa fa-cog"></span>관리자페이지</a></li> -->
	<c:if test="${member.gender == 2}">
		<li><a href="admin.LF" class="nav_setting"><span class="fa fa-cog"></span>관리자페이지</a></li>	
	</c:if>
	
</ul>