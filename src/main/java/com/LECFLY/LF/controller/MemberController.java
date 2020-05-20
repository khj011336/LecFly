package com.LECFLY.LF.controller;

import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.LECFLY.LF.model.dao.inf.member.IMemberDAO;
import com.LECFLY.LF.model.vo.MemberVO;
import com.LECFLY.LF.model.vo.creator.VideoVO;
import com.LECFLY.LF.service.inf.member.ILoginSVC;
import com.LECFLY.LF.service.inf.member.IMypageSVC;

@Controller
public class MemberController {
	
	@Autowired
	ILoginSVC logSvc;
	@Autowired
	IMypageSVC mpSvc;
	
	
	// 로그인창 으로 이동했을때
	
	@RequestMapping(value="login.LF", method=RequestMethod.GET)
	public String memberLoginPage(Model model, String msg) {
		model.addAttribute("msg", msg);
		System.out.println("memberLoginPage()...");
		return "member/login";
	}
	
	// login.LF 에서 이메일 비밀번호 입력후 로그인 클릭시
	@RequestMapping(value="login_proc.LF", method=RequestMethod.POST)
	public String memberLoginedHomePage(HttpSession ses, Model model, String email, String pw) {
		System.out.println("memberLoginedHomePage()...");
		MemberVO mb = new MemberVO();
		int r = logSvc.loginProcess(email, pw);
		if( r == logSvc.MB_EMAIL_AUTH_OK ) {
			mb = logSvc.login(email, pw);
			ses.setAttribute("member", mb);
			System.out.println(mb);
			return "redirect:/";
		} else {
			ses.setAttribute("msg", logSvc.getMsg(r));
//			return "member/login";
			return "redirect:login.LF";
		}
	} 
	
	// 로그아웃 시
	@RequestMapping(value="logout_proc.LF", method=RequestMethod.GET)
	public String memberLogout(HttpSession ses, Model model) {
		System.out.println("logout_proc()...");
		ses.invalidate();
		return "redirect:/";
	} 
	

//	회원가입하기						
	//	clause.lf(form; get; 비회원)			약관 폼 이동
	//login.LF 에서 회원가입을 눌렀을시 약관동의 페이지
	@RequestMapping(value="clause.LF", method=RequestMethod.GET)
	public String memberClausePage() {
		System.out.println("memberClausePage()...");
		return "member/clause";
	}
	
	// 약관동의 에서 넘어와서 회원가입 폼을 준비하는 페이지
	// join_new_member.lf (form; get; 비회원)
	@RequestMapping(value="join_new_member.LF", method=RequestMethod.GET)
	public String memberJoinPage() {
		System.out.println("memberJoinPage()");
			return "member/create_new_member";
	}
	// 회원가입하는 proc
	//member_join.lf (proc; post; dao; 비회원)
	@RequestMapping(value="join_member_proc.LF", method=RequestMethod.POST)
	public String join_member_proc( HttpSession ses, Model model, String cnm_upload_pic,
			String name, String nickname, @DateTimeFormat(pattern="yyyy-MM-dd")Date birthday,
			String gender, String email, String password,
			String pw_confirm, String phNumber, String phNumber2,
			String postalcode, String basic_address, String detail_address,
			String agree_receive_email, String agree_receive_sms
//			String cnm_upLoad_pic,
//			String cnm_mb_name, String cnm_mb_nick, @DateTimeFormat(pattern="yyyy-MM-dd")Date cnm_mb_birth,
//			String cnm_mb_gender, String cnm_mb_email, String cnm_mb_pw,
//			String cnm_mb_pw_confirm, String cnm_mb_ph1, String cnm_mb_ph2,
//			String postalcode, String cnm_mb_adress_basic, String cnm_mb_adress_detail,
//			String cnm_mb_agree_news_bymail, String cnm_mb_agree_news_bysms
			){
		// 인자 테스트
		System.out.println("join_member_proc....");
		System.out.println(phNumber +"/"+ phNumber2 +"/"+ agree_receive_email +"/"+ agree_receive_sms +"/"
				+ birthday);
		if( !name.isEmpty() && name != null ) {
			System.out.println("이름 있음");
			model.addAttribute("name", name);
		}
		if( !nickname.isEmpty() && nickname!=null  ) {
			System.out.println("닉네임 있음");
			model.addAttribute("nickname", nickname);
			if(logSvc.check_dup_nick(nickname))
				model.addAttribute("nick_msg", "중복된 닉네임입니다.");
			else
				model.addAttribute("nick_msg", "사용가능한 닉네임입니다.");
		}
		if( birthday!=null ) {
			System.out.println("생년월일");
			model.addAttribute("birthday", birthday);
		}
		if( !gender.isEmpty()&&gender!=null ) {
			System.out.println("성별있음");
			model.addAttribute("gender", gender);
		}
		if( !email.isEmpty()&&email!=null ) {
			System.out.println("이메일 있음");
			model.addAttribute("email", email);
			if(logSvc.check_dup_email(email))
				model.addAttribute("email_msg", "가입된 이메일입니다.");
			else
				model.addAttribute("email_msg", "사용가능한 이메일입니다.");
		}
		if( !password.isEmpty()&&password!=null ) {
			System.out.println("비밀번호 있음");
			model.addAttribute("password", password);
		}
		if( !pw_confirm.isEmpty()&&pw_confirm!=null ) {
			System.out.println("비밀번호 확인 있음");
			model.addAttribute("pw_confirm", pw_confirm);
		}
		if( !phNumber.isEmpty()&&phNumber!=null ) {
			System.out.println("전화번호 앞자리 있음");
			model.addAttribute("phNumber", phNumber);
		}
		if( !phNumber2.isEmpty()&& phNumber2!=null ) {
			System.out.println("전화번호 뒷자리 있음");
			model.addAttribute("phNumber2", phNumber2);
		}
		
		if(!name.isEmpty()&&name!=null && !nickname.isEmpty()&&nickname!=null && birthday!=null && 
				!gender.isEmpty()&&gender!=null && !email.isEmpty()&&email!=null && 
				!password.isEmpty()&&password!=null && !pw_confirm.isEmpty()&&pw_confirm!=null && 
				!phNumber.isEmpty()&&phNumber!=null && !phNumber2.isEmpty()&& phNumber2!=null) {
			System.out.println("필수 인자값 모두 입력됨!");
			// birthday 인자값 timestamp로 변환
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Timestamp birth = Timestamp.valueOf(sdf.format(birthday));
			// gender 변환
			int gen = 0;
			int code = 0;
			try {
				gen = Integer.parseInt(gender);
				code = Integer.parseInt(postalcode);
			} catch (NullPointerException e) {
				System.out.println(e);
			}
			// ph 인자값 결합
			String ph = "010"+ phNumber + phNumber2;
			// agreeReceive 값 결합 및 변환
			int agreeReceive = 0;
			if( agree_receive_email.equals("agree_email"))
				agreeReceive += 1;
			if( agree_receive_sms.equals("agree_sms"))
				agreeReceive += 2;
			
			if(logSvc.joinMember(cnm_upload_pic, name, nickname, birth, gen, 
					email, password, ph, agreeReceive, basic_address, 
					detail_address, code)) {
				System.out.println(name + "회원 생성 성공");
			} else {
				System.out.println(name + "회원성공 실패");
			}
			
			return "home";
			
		}
		return "member/create_new_member";
//		model.addAttribute("pic", cnm_upload_pic);
//		model.addAttribute("name", name); 
//		model.addAttribute("birthday", birthday);
//		model.addAttribute("gender", gender);
//		model.addAttribute("email", email);
//		model.addAttribute("pw", password);
//		model.addAttribute("pw_confirm", pw_confirm); 
//		model.addAttribute("ph1", phNumber);
//		model.addAttribute("ph2", phNumber2);
//		model.addAttribute("postal_code", postalcode);
//		model.addAttribute("basic_adress", basic_address);
//		model.addAttribute("detail_adress", detail_address);
//		model.addAttribute("agree_mail", agree_receive_email); 
//		model.addAttribute("agree_sms", agree_receive_sms);
		
		
		
//		MemberVO mb = new MemberVO(
//				null, cnm_mb_name, cnm_mb_nick, birthday, cnm_mb_gender, 
//				cnm_mb_email, cnm_mb_pw, ph, agreeReceive, cnm_mb_adress_basic, 
//				cnm_mb_adress_detail, cnm_mb_adress_num);
		
		// 만약 null 예외가 있으면 프론트엔드(즉 JS)에서 처리 후 넘어오게함. 대신 그곳에서 강제로 뚫고 들어왔을때 대비해서 만든 예외처리
		// UQ가 걸린 3개 항목 nickname/email/ph_number를 검색해서 사용가능한지 확인하는 서비스 단을 구성해야됨.
	}
	// 닉네임 중복체크
	@RequestMapping(value="dup_nic_check_proc.LF", method=RequestMethod.GET)
	public String test_proc(HttpSession ses, Model model,
			@RequestParam(value = "nickname")String nickname
			) {
		System.out.println("들어옴"+nickname);
		model.addAttribute("nickname", nickname);
//		ses.setAttribute("nickname", nickname);
		if(logSvc.check_dup_nick(nickname))
//			ses.setAttribute("nick_msg", "중복된 닉네임입니다.");
			model.addAttribute("nick_msg", "중복된 닉네임입니다.");
		else
//			ses.setAttribute("nick_msg", "사용가능한 닉네임입니다.");
			model.addAttribute("nick_msg", "사용가능한 닉네임입니다.");
		return "redirect:join_new_member.LF";
//		return "member/create_new_member";
	}
	//test_proc.LF
//	@RequestMapping(value="test_proc.LF", method=RequestMethod.POST)
//	public String test_proc(HttpSession ses, Model model, String cnm_upload_pic,
//			String name, String nickname, @DateTimeFormat(pattern="yyyy-MM-dd")Date birthday,
//			String gender, String email, String password,
//			String pw_confirm, String phNumber, String phNumber2,
//			String postalcode, String basic_address, String detail_address,
//			String agree_receive_email, String agree_receive_sms
//	@RequestMapping(value="check_dup_nic.LF", method=RequestMethod.POST)
//	public String test_proc(HttpSession ses, Model model 
//			@ModelAttribute(value = "pic")String cnm_upload_pic, 
//			@ModelAttribute(value = "name")String name, 
//			@ModelAttribute(value = "nickname")String nickname, 
//			@ModelAttribute(value = "birthday")String birthday,
//			@ModelAttribute(value = "gender")String gender, 
//			@ModelAttribute(value = "email")String email, 
//			@ModelAttribute(value = "pw")String password,
//			@ModelAttribute(value = "pw_confirm")String pw_confirm, 
//			@ModelAttribute(value = "ph1")String phNumber, 
//			@ModelAttribute(value = "ph2")String phNumber2,
//			@ModelAttribute(value = "postalcode")String postalcode,
//			@ModelAttribute(value = "basic_adress")String basic_address,
//			@ModelAttribute(value = "detail_adress")String detail_address,
//			@ModelAttribute(value = "agree_mail")String agree_receive_email, 
//			@ModelAttribute(value = "agree_sms")String agree_receive_sms
//			){
//		System.out.println("test");
//		System.out.println(nickname + "test");
//		model.addAttribute("pic", cnm_upload_pic);
//		model.addAttribute("name", name); 
//		model.addAttribute("nickname", nickname);
//		model.addAttribute("birthday", birthday);
//		model.addAttribute("gender", gender);
//		model.addAttribute("email", email);
//		model.addAttribute("pw", password);
//		model.addAttribute("pw_confirm", pw_confirm); 
//		model.addAttribute("ph1", phNumber);
//		model.addAttribute("ph2", phNumber2);
//		model.addAttribute("postal_code", postalcode);
//		model.addAttribute("basic_adress", basic_address);
//		model.addAttribute("detail_adress", detail_address);
//		model.addAttribute("agree_mail", agree_receive_email); 
//		model.addAttribute("agree_sms", agree_receive_sms);
//		return "redirect:join_new_member.LF";
//	}
	
//이메일 찾기		******일단 기능 미구현으로 남김...********				
	//	find_mb_login.lf (form; get; 비회원)			이메일찾기 폼 이동
	//	find_login.lf (proc; post; dao; 비회원)			이메일찾기proc실행(findEmail(phNumber) )
	// 로그인창에서 아이디찿기 클릭시 
//	@RequestMapping(value="find_mb_login.LF", method=RequestMethod.GET)
//	public String memberFindLoginPage() {
//		System.out.println("memberFindLoginPage()...");
//		return "member/find_mb_login";
//	}


//비밀번호 찾기						
	//	find_mb_pw.lf (form; get; 비회원)			비밀번호 재발급폼 이동
	// 로그인창에서 비밀번호 찾기 클릭시
	@RequestMapping(value="find_mb_pw.LF", method=RequestMethod.GET)
	public String memberFindPasswordPage() {
		System.out.println("memberFindPasswordPage()...");
		return "member/find_mb_pw";
	}
	
	//	find_pw_proc.lf (proc; post; dao; 비회원)			비밀번호 재발급proc실행(createNewPWToEmail(email)
	@RequestMapping(value="find_pw_proc.LF", method=RequestMethod.POST)
	public String memberFindPasswordProc(Model model,
			String email, String name, String phNumber) {
		System.out.println("memberFindPasswordProc()...");
		if(email!=null&&!email.isEmpty()&&name!=null&&!name.isEmpty()&&phNumber!=null&&!phNumber.isEmpty()) {
			System.out.println("모든 인자값 다 있음");
			if(logSvc.findPw(email, name, phNumber)==1) {
				System.out.println("회원 db에서 찾음");
				String password = logSvc.makeTemptPw();
				System.out.println("password: " + password);
				if(logSvc.makeTempPwIn(email, password)) {
					System.out.println("임시비밀번호 생성 완료");
					return "redirect:/";
				}
			} else {
				System.out.println("등록된 회원 없음...");
				model.addAttribute("msg","가입되지 않은 회원입니다...");
			}
		} else if(email==null || email.isEmpty()) {
			System.out.println("email없음");
			model.addAttribute("msg","이메일을 입력하세요!");
		} else if(name==null || name.isEmpty()) {
			System.out.println("name없음");
			model.addAttribute("msg","이름을 입력하세요!");
		} else if(phNumber==null || phNumber.isEmpty()) {
			System.out.println("phNumber없음");
			model.addAttribute("msg","휴대전화번호를 입력하세요!");
		}
		return "redirect:find_mb_pw.LF";
	}
	
////////////////////////////////////////   위쪽은 로그인 관련 페이지들
	
	//	마이페이지 정보 확인하기								
	//	mypage.lf(form, post, dao)			"마이페이지 메인화면 페이지 이동
	//tikcetVO/couponVO/showClassVideoVO
	//를 dao에서 불러와 우측 기능 실행"			"회원의 등급확인하기
	//회원이 이용중인 이용권 갯수 표시
	//회원이 보유중인 쿠폰 갯수 표시
	//회원이 수강중인 강의 갯수 확인하기
	//회원이 해당 강의 중 보던 영상 표시하기"	
	// 마이페이지 이동
	@RequestMapping(value="mypage.LF", method=RequestMethod.GET)
	public String memberMyPage() {
		System.out.println("memberMyPage()");
		
		return "member/mypage";
	}	
	
//	회원의 프로필 사진 수정하기							
//	change_pro_pic.lf(proc; post, dao, attr)			proc완료후 mypage.lf 프로필사진 업데이트된 상태로 forward
	@RequestMapping(value="change_pro_pic.LF", method=RequestMethod.POST)
	public String memberChangeProfilePicture(HttpSession ses, 
			@RequestParam(value="id", defaultValue ="0") int id, // 바꾸려고하는 id
			@RequestParam(value="pic", defaultValue ="") String filePath,  
			Model model) {
		System.out.println("memberChangeProfilePicture()...");
		// 교수님말: 회원 비밀번호 한번더 인증하는 작업이 필요하다고함 (회원의 개인정보를 바꾸기 때문에)		
		int loginedId = (Integer)ses.getAttribute("mbId");
		boolean b = mpSvc.updateMemberProfileImg(loginedId, id, filePath);
		if( b ) {
			
		} else {
			
		}
		return "member/mypage/mypage"; // forward
	}
	
//크리에이터 신청하기							
//	apply_creator.lf(form, post)			크리에이터 신청 폼으로 이동
	@RequestMapping(value="apply_creator.LF", method=RequestMethod.POST)
	public String memberApplyCreator() {
		System.out.println("memberApplyCreator()...");
		
		
		return "redirect:mypage.LF?pn=apply_creator"; // ??? 조각페이지면 이런형식으로 띄우고 이거 체크필요
	}
	
	
//회원이 신청한 강의목록 표시하기							수강 관리
	
//	수강중인강의
//	mypage_attending_class.lf(proc, post, dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_attending_lec.LF", method=RequestMethod.POST)
	public String memberMypageAttendingLec(HttpSession ses, 
			@RequestParam(value="id", defaultValue ="0") int id, Model model) {
		System.out.println("memberMypageAttendingLec()...");	
		
//		List<VideoVO> vdList = mpSvc.showAllAttendingLec(); // 내가 수강한 비디오 목록을 리스트로 받으려함
//		if(vdList != null) {
//			return "";
//		} else {
//			return "";
//		}
		 return "member/mypage/attend_lec_manager/mypage_attending_lec";
	}
	
	//회원이 찜하기한 강의 확인하기							
//	mypage_will_attend.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_will_attend.LF", method=RequestMethod.POST)
	public String memberMypageWillAttend() {
		System.out.println("memberMypageWillAttend()...");	
		return "member/mypage/attend_lec_manager/mypage_will_attend";
	}

	//회원이 좋아요한 강의 확인하기							
	//	mypage_like.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_like.LF", method=RequestMethod.POST)
	public String memberMypageLike() {
		System.out.println("memberMypageLike()...");	
		return "member/mypage/attend_lec_manager/mypage_like";
	}
	
	
	
	@RequestMapping(value="mypage_no_list.LF", method=RequestMethod.POST)
	public String memberMypageNOList() {
		System.out.println("memberMypageNOList()...");	
		return "member/mypage/attend_lec_manager/mypage_no_list";
	}	
	
//회원이 보던 영상 표시하기							
//	mypage_attending_video.lf(proc, post, dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_attending_video.LF", method=RequestMethod.POST)
	public String memberMypageAttendingVideo() {
		System.out.println("memberMypageAttendingVideo()...");	
		return "redirect:mypage.LF?pn=attending_video";
	}
	

	
////////활동내역
		
//회원이 작성한 댓글내역 확인하기							활동 내역
//	mypage_comment.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_comment.LF", method=RequestMethod.POST)
	public String memberMypageComment() {
		System.out.println("memberMypageComment()...");	
		return "memeber/mypage/activity/mypage_comment";
	}

//회원이 문의한 qna내역 확인하기							문의 내역
//	mypage_qna.lf(proc,post,dao)				해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_qna.LF", method=RequestMethod.POST)
	public String memberMypageQna() {
		System.out.println("memberMypageQna()...");	
		return "member/mypage/activity/mypage_qna";
	}
//펀딩신청내역 확인하기									펀딩신청내역
//	mypage_funding.lf(proc,post,dao)		해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_funding.LF", method=RequestMethod.POST)
	public String memberMypageFunding() {
		System.out.println("memberMypageFunding()...");	
		return "member/mypage/activity/mypage_funding";
	}
	
//회원이 보유중인 쿠폰 표시							나의쿠폰
//	mypage_coupon.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_coupon_info.LF", method=RequestMethod.POST)
	public String memberMypageCoupon() {
		System.out.println("memberMypageCoupon()...");	
		return "member/mypage/activity/coupon_info";
	}

	
//// 정보관리	
	
// 회원이 가입시 입력한 정보 수정하기							회원정보 수정
//	mypage_update_info.lf(proc,post,dao)		해당 조각페이지 불러오게 리턴			
	@RequestMapping(value="mypage_mb_update.LF", method=RequestMethod.POST)
	public String memberMypageUpdateInfo() {
		System.out.println("memberMypageUpdateInfo()...");	
		return "member/mypage/info_manager/mypage_mb_update";
	}
	
//회원의 비밀번호 변경하기								비밀번호 변경
//	mypage_update_pw.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_pw_update.LF", method=RequestMethod.POST)
	public String memberMypageUpdatePw() {
		System.out.println("memberMypageUpdatePw()...");	
		return "member/mypage/info_manager/mypage_pw_update";
	}


//////	 주문 / 배송관리
	
//회원이 결제한 물품의 배송정보 표시하기							배송 관리
//	mypage_payment.lf(form,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_payment_info.LF", method=RequestMethod.POST)
	public String memberMypagePayment() {
		System.out.println("memberMypagePayment()...");	
		return "member/mypage/order_manager/mypage_payment_info";
	}
	
//회원이 결제한 내역 확인하기							
//	mypage_payment_history.lf(form,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_payment_history.LF", method=RequestMethod.POST)
	public String memberMypagePaymentHistory() {
		System.out.println("memberMypagePaymentHistory()...");	
		return "redirect:mypage.LF?pn=payment_history";
	}
	
	
	
//회원이 이용중인 이용권 표시							
//	mypage_ticket.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_ticket.LF", method=RequestMethod.POST)
	public String memberMypageTicket() {
		System.out.println("memberMypageTicket()...");	
		return "redirect:mypage.LF?pn=ticket";
	}

	

	
//장바구니 목록 확인하기							
//	mypage_shopping.lf(form,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_shoppingcart.LF", method=RequestMethod.GET)
	public String memberMypageShopping() {
		System.out.println("memberMypageShopping()...");	
		return "member/mypage/order_manager/mypage_shoppingcart";
	}
	
	// 세현 테스트 mypage.jsp 관련(load 써먹을수있나 테스트중.)..
	@RequestMapping(value="mypage_list.LF", method=RequestMethod.GET)
	public String memberMypageList() {
		System.out.println("memberMypageList()...");	
		return "member/mypage/info_manager/mypage_mb_update";
	}
	
	@RequestMapping(value="mypage_delivery_info.LF", method=RequestMethod.GET)
	public String memberMypageDeliveryInfo() {
		System.out.println("memberMypageDeliveryInfo()...");	
		return "member/mypage/order_manager/mypage_delivery_info";
	}
	
	
	@RequestMapping(value="mypage_recive_address.LF", method=RequestMethod.GET) // ???/
	public String memberMypageReciveAddress() {
		System.out.println("memberMypageReciveAddress()...");	
		return "member/mypage/order_manager/mypage_delivery_info";
	}
	
	
////////////////////////////////////////////////////
	
	// 네비 장바구니 클릭
//	@RequestMapping(value="shopping_cart.LF", method=RequestMethod.GET)
//	public String memberShoppingCart() {
//		System.out.println("memberShoppingCart()...");	
//		return "payment/shoppingCart";
//	}
	
	
	
	


	
}