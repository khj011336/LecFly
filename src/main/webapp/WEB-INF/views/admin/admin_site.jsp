<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h4>사이트 이용안내</h4>

<div class="admin_table_filter">
	<table>
		<caption>검색조건설정</caption>
		<tr>
			<th>기간검색</th>
			<td>
				<span class="date_filter"><a href="#">오늘</a></span> |
				<span class="date_filter"><a href="#">3일</a></span> |
				<span class="date_filter"><a href="#">7일</a></span> |
				<span class="date_filter"><a href="#">1개월</a></span>
				<input type="date"/> ~ <input type="date"/>
			</td>
		</tr>
		<tr>
			<th>분류 선택</th>
			<td>
				<select name="">
   					<option value="">전체</option>
  					<option value="">회원</option>
    				<option value="">크리에이터</option>
    				<option value="">관리자</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>키워드 검색</th>
			<td>
				<select name="">
   					<option value="">전체</option>
  					<option value="">아이디</option>
    				<option value="">닉네임</option>
    				<option value="">연락처</option>
    				<option value="">회원번호</option>
				</select>
			<input type="text" size="40"></td>
		</tr>
		<tr>
			<th>신청상태</th>
			<td>
				<lable><input name="board_con" type="radio" value="">전체보기</lable>
				<lable><input name="board_con" type="radio" value="">미신청</lable>
				<lable><input name="board_con" type="radio" value="">신청</lable>
				<lable><input name="board_con" type="radio" value="">승인미완료</lable>
				<lable><input name="board_con" type="radio" value="">승인완료</lable>
			</td><td></td>
		</tr>
	</table>
	<div class="admin_search_btns">
		<button type="button">조회하기</button>
	</div>
</div>

<div class="board_sort_filter">
	<h6 class="admin_search_result">
	[오늘 등록된 정보 <span class="board_result_count">1</span>건]
	검색결과 <span class="board_result_count">20</span>건
	</h6>
	
	<ul class="admin_search_edit">	
		<li>
			<span class="date_filter"><a href="#">전체선택</a></span>
			<span class="date_filter"><a href="#">수정</a></span>
			<span class="date_filter"><a href="#">삭제</a></span>
			<span class="date_filter"><a href="#">저장</a></span>
		</li>
	</ul>	
	<ul class="admin_search_sort">	
		<li><a href="#">정확도순</a></li>
		<li><a href="#">가입일순</a></li>
		<li><a href="#">최근방문순</a></li>
		
	</ul>
</div>    



<div class="admin_table_wrap">
	<table>
		<tr class="admin_table_head">
			<th width=2%><input type="checkbox"/></th> 
			<th>번호</th> 
			<th>회원번호</th> 
			<th>프로필이미지</th> 
			<th>이름</th> 
			<th>닉네임</th> 
			<th>생년월일</th>
			<th>성별</th> 
			<th>이메일</th> 
			<th>연락처</th> 
			<th>가입일</th>
			<th>수신동의</th>
			<th>이용권</th>
			<th>업로더신청</th>
			<th>최근방문일</th>
			<th>기본주소</th>
			<th>상세주소</th>
		</tr>
		<% for(int i=1; i<=20;i++) {%>
		<tr>
			<td><input type="checkbox"/></td> 
			<td><%=i %></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td>
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
			<td></td> 
		</tr>
		<% } %>
	</table>
</div>