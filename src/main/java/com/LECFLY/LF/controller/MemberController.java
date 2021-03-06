package com.LECFLY.LF.controller;

import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.LECFLY.LF.model.vo.LecAttendVO;
import com.LECFLY.LF.model.vo.LecTypeVO;
import com.LECFLY.LF.model.vo.admin.PayHistoryVO;
import com.LECFLY.LF.model.vo.creator.CreatorVO;
import com.LECFLY.LF.model.vo.creator.KitVO;
import com.LECFLY.LF.model.vo.cart.CouponVO;
import com.LECFLY.LF.model.vo.cscenter.QnaCommentVO;
import com.LECFLY.LF.model.vo.cscenter.QnaVO;
import com.LECFLY.LF.model.vo.member.CommentVO;
import com.LECFLY.LF.model.vo.member.MemberVO;
import com.LECFLY.LF.service.inf.creator.IFileSVC;
import com.LECFLY.LF.service.inf.member.ILoginSVC;
import com.LECFLY.LF.service.inf.member.IMypageSVC;

@Controller
public class MemberController {

	@Autowired
	ILoginSVC logSvc;
	@Autowired
	private IMypageSVC mpSvc;
	@Autowired
	IFileSVC fiSvc;


	// 로그인창 으로 이동했을때
	@RequestMapping(value="login.LF", method=RequestMethod.GET)
	public String memberLoginPage(Model model, String msg) {
		model.addAttribute("login_msg", msg);
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
			if(logSvc.incLoginCnt(mb.getId())) {
				mb.setLoginCount(mb.getLoginCount()+1);
				System.out.println("로그인횟수 증가 / 현재:"+mb.getLoginCount()+"회");
			}
			if(logSvc.updateLoginDate(mb.getId()))
				System.out.println("로그인일시 변경 / 현재 : "+ mb.getLoginedAt());
			ses.setAttribute("member", mb);
			System.out.println(mb);
			return "redirect:/";
		} else {
			model.addAttribute("login_msg", logSvc.getMsg(r));
			model.addAttribute("email", email);
			model.addAttribute("pw", pw);
			return "member/login";
//			return "redirect:login.LF";
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
	public String join_member_proc( HttpSession ses, Model model, MultipartFile cnm_upload_pic,
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Timestamp birth = Timestamp.valueOf(sdf.format(birthday));
			model.addAttribute("birthday", birth);
			System.out.println(birth);
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
		if( !postalcode.isEmpty()&& postalcode!=null ) {
			System.out.println("우편번호 있음");
			model.addAttribute("postalcode", postalcode);
		}
		if( !basic_address.isEmpty()&& basic_address!=null ) {
			System.out.println("기본 주소 있음");
			System.out.println(basic_address);
			model.addAttribute("basic_address", basic_address);
		}
		if( !detail_address.isEmpty()&& detail_address!=null ) {
			System.out.println("상세주소 있음");
			model.addAttribute("detail_address", detail_address);
		}
		model.addAttribute("msg",
				logSvc.check_none(name, nickname, birthday, gender,
				email, password, pw_confirm, phNumber, phNumber2));
		if(!name.isEmpty()&&name!=null && !nickname.isEmpty()&&nickname!=null && birthday!=null &&
				!gender.isEmpty()&&gender!=null && !email.isEmpty()&&email!=null &&
				!password.isEmpty()&&password!=null && !pw_confirm.isEmpty()&&pw_confirm!=null &&
				!phNumber.isEmpty()&&phNumber!=null && !phNumber2.isEmpty()&& phNumber2!=null) {
			System.out.println("필수 인자값 모두 입력됨!");
			// birthday 인자값 timestamp로 변환
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Timestamp birth = Timestamp.valueOf(sdf.format(birthday));
			System.out.println("birthday check" + birthday);
			// gender 변환
			int gen = 0;
			int code = 0;
			try {
				gen = Integer.parseInt(gender);
				code = Integer.parseInt(postalcode);
			} catch (NullPointerException e) {
				System.out.println(e);
			}
			System.out.println("gen" + gen + "/code" +  code);
			// ph 인자값 결합
			String ph = "010"+ phNumber + phNumber2;
			// agreeReceive 값 결합 및 변환
			int agreeReceive = 0;
			if( agree_receive_email==null || agree_receive_email.isEmpty() || agree_receive_sms==null || agree_receive_sms.isEmpty()) {
				agreeReceive = 0;
				System.out.println("agree없음");
			}else {
				if( agree_receive_email.equals("agree_email"))
					agreeReceive += 1;
				if( agree_receive_sms.equals("agree_sms"))
					agreeReceive += 2;
				System.out.println("agree있음");
			}
			System.out.println("agreeReceive " + agreeReceive);
			fiSvc.makeDir(name);
			Map rMap = fiSvc.writeFile(cnm_upload_pic, 1, name);
			String pic = "/"+(String)rMap.get("file");
			if(logSvc.joinMember(pic, name, nickname, birth, gen,
					email, password, ph, agreeReceive, basic_address,
					detail_address, code)) {
				System.out.println(name + "회원 생성 성공");
			} else {
				System.out.println(name + "회원성공 실패");
			}

			return "member/login";

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
	@RequestMapping(value="nic_dupcheck.LF", method=RequestMethod.GET)
	@ResponseBody
	public String nicknameDupCheck(String nickname) {
		// req.getParam과 타입맵핑을 자동으로 해줌
		System.out.println("nic_dupcheck.LF" + nickname);
		//String login = req.getParameter("login");
		if( nickname != null && !nickname.isEmpty() ) {
			if( logSvc.check_dup_nick(nickname) ) {
				return "yes";
			} else {
				return "no";
			}
		} else {
			return "error";
		}
	}
	// 이메일 중복체크
	@RequestMapping(value="email_dupcheck.LF", method=RequestMethod.GET)
	@ResponseBody
	public String emailDupCheck(String email) {
		// req.getParam과 타입맵핑을 자동으로 해줌
		System.out.println("email_dupcheck.LF");
		//String login = req.getParameter("login");
		if( email != null && !email.isEmpty() ) {
			if( logSvc.check_dup_email(email) ) {
				return "yes";
			} else {
				return "no";
			}
		} else {
			return "error";
		}
	}
//		System.out.println("들어옴"+nickname);
//		ses.setAttribute("nickname", nickname);
//		if(logSvc.check_dup_nick(nickname))
//			ses.setAttribute("nick_msg", "중복된 닉네임입니다.");
//			model.addAttribute("nick_msg", "중복된 닉네임입니다.");
//		else
//			ses.setAttribute("nick_msg", "사용가능한 닉네임입니다.");
//			model.addAttribute("nick_msg", "사용가능한 닉네임입니다.");
//		return "redirect:join_new_member.LF";
//		return "member/create_new_member";
//	}
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
	@RequestMapping(value="mypage.LF", method= {RequestMethod.GET, RequestMethod.POST})
	public String memberMyPage(HttpSession ses, Model model) {
		System.out.println("memberMyPage()");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		if(mb != null) {
			//마이페이지에 필요한거 카테고리 이용권개수(무엇을이용하는지(카테고리) + 종료날짜) + 쿠폰 개수 + 강의신청 목록 개수
			int mbId = mb.getId();
			Map<String, Object> pMap = mpSvc.selectMyPageContents(mbId);
			if(pMap != null) {
				String ticketFrontName = (String)pMap.get("ticketFrontName");
				String ticketName = (String)pMap.get("ticketName");
				List<String> strCateList = (List<String>)pMap.get("strCateList");
				Timestamp tiketEndDay = (Timestamp)pMap.get("tiketEndDay");
				int cntCoupon = (int)pMap.get("cntCoupon");
				int cntLecture = (int)pMap.get("cntLecture");


				System.out.println("ticketFrontName = " + ticketFrontName + " / ticketName = " + ticketName +
						" / strCateList.size() = " + strCateList.size() + " / tiketEndDay = " + tiketEndDay +
						" / cntCoupon = " + cntCoupon + " / cntLecture = " + cntLecture);

				model.addAttribute("ticketFrontName", ticketFrontName);
				model.addAttribute("ticketName", ticketName);
				model.addAttribute("strCateList", strCateList);
				model.addAttribute("ticketEndDay", tiketEndDay);
				model.addAttribute("cntCoupon", cntCoupon);
				model.addAttribute("cntLecture", cntLecture);

				model.addAttribute("mb", mb);
				model.addAttribute("mbLoginNicname", mb.getNicname());
				model.addAttribute("mpNone", "");
				
				// member pic path 입력

				List<LecAttendVO> laList = mpSvc.selectLecToStatusForMbIdStatus(mbId, LecTypeVO.STATUS_ATTENDING);
				if(laList != null) {
					model.addAttribute("msg_status", "수강중인 강의");
					model.addAttribute("laList", laList);
				} else {
					model.addAttribute("msg_status", "수강중인 강의");
				}
			} else {
				System.out.println("pMap == null ");
				System.out.println("mb = " + mb);
				model.addAttribute("mbLoginNicname", mb.getNicname());
				model.addAttribute("mpNone", "");
			}
			String path = "/images/2020/"+mb.getName()+"/Img";
			model.addAttribute("pic", path);
			return "member/mypage.ho";
		} else {
			// 실패시 로그인창으로~
			model.addAttribute("login_msg", "로그인후 이용가능합니다.");
			return "member/login";
		}
	}

//	회원의 프로필 사진 수정하기
//	change_pro_pic.lf(proc; post, dao, attr)			proc완료후 mypage.lf 프로필사진 업데이트된 상태로 forward
	@RequestMapping(value="change_pro_pic.LF", method=RequestMethod.POST)
	public String memberChangeProfilePicture(HttpSession ses,
			@RequestParam(value="pic", defaultValue ="") String filePath,
			Model model) {
		System.out.println("memberChangeProfilePicture()...");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		int mbId = mb.getId();
		boolean b = mpSvc.updateMemberProfileImg(mbId, filePath);
		if( b ) {
			model.addAttribute("msg", "회원 프로필 이미지 변경 성공");
		} else {
			model.addAttribute("msg", "회원 프로필 이미지 변경 실패");
			model.addAttribute("errCode", 1);
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

	@RequestMapping(value="mypage_delete_attending_lec.LF", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberMypageDeleteAttendingLec(HttpSession ses)
	{
		System.out.println("memberMypageDeleteAttendingLec()");
		Map<String, Object> rMap = new HashMap<>();
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		int mbId = mb.getId();

		return null;
	}

	/**
	 * 마이페이지 상단에 내가 수강중인 클래스 (이미지 클릭시 보여주는것)
	 *
	 *
	 */
	@RequestMapping(value="mypage_attending_lec.LF", method={RequestMethod.GET, RequestMethod.POST})
	public String memberMypageAttendingLec(HttpSession ses,
			@RequestParam(value="status", defaultValue ="0") int status, Model model) {
		System.out.println("memberMypageAttendingVideo()...");
		// 내가 수강한 비디오 목록을 리스트로 받으려함
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		System.out.println("mb = " + mb);
		if(mb != null) {
			int mbId = mb.getId();
			Map<String, Object> listMap = mpSvc.selectVideoAndCreImgPathAndCreNicname(mbId, status);
			if(listMap != null) {
				List<Integer> idList = (List<Integer>)listMap.get("idList");
				List<String> strCateList = (List<String>)listMap.get("cateList");
				List<String> subTitleList = (List<String>)listMap.get("titleList");
				List<String> imgPathList = (List<String>)listMap.get("imgPathList");
				List<String> nickNameList = (List<String>)listMap.get("nicList");
				List<Integer> likeCountList = (List<Integer>)listMap.get("likeCountList");
				List<String> creImgList = (List<String>)listMap.get("creatorImgList");

				model.addAttribute("msg_status", "수강중인 강의");
				model.addAttribute("idList", idList);
				model.addAttribute("cateList", strCateList);
				model.addAttribute("titleList", subTitleList);
				model.addAttribute("imgPathList", imgPathList);
				model.addAttribute("nicNameList", nickNameList);
				model.addAttribute("likeCountList", likeCountList );
				model.addAttribute("creImgList", creImgList);


			} else {
				System.out.println("laList = null");
				model.addAttribute("msg_status", "강의 신청 목록");
				model.addAttribute("mp_msg", "수강중인 강의 신청 목록 내역이 없습니다.");
			}
			return "member/mypage/attend_lec_manager/mypage_lec_type";
		} else {
			model.addAttribute("login_msg", "로그인후 이용가능합니다.");
			return "member/login";
		}
	}




//회원이 신청한 강의목록 표시하기							수강 관리
//	수강중인강의
//	mypage_attending_class.lf(proc, post, dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_attending_vd.LF", method=RequestMethod.POST)
	public String memberMypageAttendingVideo(HttpSession ses,
			@RequestParam(value="status", defaultValue ="0") int status, Model model) {
		System.out.println("memberMypageAttendingVideo()...");
		// 내가 수강한 비디오 목록을 리스트로 받으려함
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		System.out.println("mb = " + mb);
		if(mb != null) {
			int mbId = mb.getId();
			List<LecAttendVO> laList = mpSvc.selectLecToStatusForMbIdStatus(mbId, status);
			if(laList != null) {
				System.out.println("laList = " + laList + " / laList.size() = " + laList.size());
				model.addAttribute("msg_status", "수강중인 강의");
				model.addAttribute("laList", laList);
			} else {
				System.out.println("laList = null");
				model.addAttribute("msg_status", "수강중인 강의");
				model.addAttribute("mp_msg", "수강중인 강의 내역이 없습니다.");
			}
			return "member/mypage/attend_lec_manager/mypage_attending_lec";
		} else {
			model.addAttribute("login_msg", "로그인후 이용가능합니다.");
			return "member/login";
		}
	}

	//회원이 찜하기한 강의 확인하기
//	mypage_will_attend.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_will_attend.LF", method=RequestMethod.POST)
	public String memberMypageWillAttend(HttpSession ses,
			@RequestParam(value="status", defaultValue ="1") int status, Model model) {
		System.out.println("memberMypageWillAttend()...");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		if(mb != null) {
			int mbId = mb.getId();
			Map<String, Object> listMap = mpSvc.selectVideoAndCreImgPathAndCreNicname(mbId, status);
			if(listMap != null) {
				List<Integer> idList = (List<Integer>)listMap.get("idList");
				List<String> strCateList = (List<String>)listMap.get("cateList");
				List<String> subTitleList = (List<String>)listMap.get("titleList");
				List<String> imgPathList = (List<String>)listMap.get("imgPathList");
				List<String> nickNameList = (List<String>)listMap.get("nicList");
				List<Integer> likeCountList = (List<Integer>)listMap.get("likeCountList");
				List<String> creImgList = (List<String>)listMap.get("creatorImgList");
				model.addAttribute("msg_status", "찜하기한 강의");
				model.addAttribute("idList", idList);
				model.addAttribute("cateList", strCateList);
				model.addAttribute("titleList", subTitleList);
				model.addAttribute("imgPathList", imgPathList);
				model.addAttribute("nicNameList", nickNameList);
				model.addAttribute("likeCountList", likeCountList );
				model.addAttribute("creImgList", creImgList);
			} else {
				model.addAttribute("msg_status", "찜하기한 강의");
				model.addAttribute("mp_msg", "찜하기한 강의 내역이 없습니다.");
			}
			return "member/mypage/attend_lec_manager/mypage_lec_type";
		} else {
			System.out.println("mb = null");
			model.addAttribute("login_msg", "로그인후 이용가능합니다.");
			return "member/login";
		}
	}

	//회원이 좋아요한 강의 확인하기
	//	mypage_like.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_like.LF", method=RequestMethod.POST)
	public String memberMypageLike(HttpSession ses,
			@RequestParam(value="status", defaultValue ="2") int status, Model model) {
		System.out.println("memberMypageLike()...");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		if(mb != null) {
			int mbId = mb.getId();
			Map<String, Object> listMap = mpSvc.selectVideoAndCreImgPathAndCreNicname(mbId, status);
			System.out.println("listMap = " + listMap);
			if(listMap != null) {
				List<Integer> idList = (List<Integer>)listMap.get("idList");
				List<String> strCateList = (List<String>)listMap.get("cateList");
				List<String> subTitleList = (List<String>)listMap.get("titleList");
				List<String> imgPathList = (List<String>)listMap.get("imgPathList");
				List<String> nickNameList = (List<String>)listMap.get("nicList");
				List<Integer> likeCountList = (List<Integer>)listMap.get("likeCountList");
				List<String> creImgList = (List<String>)listMap.get("creatorImgList");

				model.addAttribute("msg_status", "좋아요한  강의");
				model.addAttribute("idList", idList);
				model.addAttribute("cateList", strCateList);
				model.addAttribute("titleList", subTitleList);
				model.addAttribute("imgPathList", imgPathList);
				model.addAttribute("nicNameList", nickNameList);
				model.addAttribute("likeCountList", likeCountList );
				model.addAttribute("creImgList", creImgList);
			} else {
				model.addAttribute("msg_status", "좋아요한  강의");
				model.addAttribute("mp_msg", "좋아요한 강의 내역이 없습니다.");
			}
			return "member/mypage/attend_lec_manager/mypage_lec_type";
		} else {
			model.addAttribute("login_msg", "로그인후 이용가능합니다.");
			return "member/login";
		}
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
	public String memberMypageComment(HttpSession ses,
			@RequestParam(value = "pn", required = false, defaultValue = "1" ) int pageNumber,
			Model model)
	{
		System.out.println("memberMypageComment()...");
		System.out.println("pn =" + pageNumber);
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		int mbId = mb.getId();
		System.out.println("mb = " + mb);
		Map<String, Object> comMap = mpSvc.selectAllMyComment(mbId, pageNumber);
		System.out.println("comMap = " + comMap);
		if( comMap != null ) {
			int totalRecords = (int)comMap.get("totalRecords");
			int maxPG = (int)comMap.get("maxPG");
			List<CommentVO> comList = (List<CommentVO>)comMap.get("comList");
			List<String> titleList = (List<String>)comMap.get("titleList");

			model.addAttribute("totalRecords", totalRecords);
			model.addAttribute("maxPG", maxPG);
			model.addAttribute("comList", comList);
			model.addAttribute("titleList", titleList);
			model.addAttribute("pn", pageNumber);
		} else {
			model.addAttribute("msg_status", "댓글 내역");
			model.addAttribute("mp_msg", mb.getNicname() + " 님의 댓글 내역이 없습니다.");
		}
		return "member/mypage/activity/mypage_comment";
	}

//회원이 문의한 qna내역 확인하기							문의 내역
//	mypage_qna.lf(proc,post,dao)				해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_qna.LF", method=RequestMethod.POST)
	public String memberMypageQna( HttpSession ses,
			@RequestParam(value = "pn", required = false, defaultValue = "1" ) int pageNumber,
			Model model ) {
		System.out.println("memberMypageQna()...");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		int mbId = mb.getId();
		System.out.println("mb = " + mb);
		Map<String, Object> comqnaMap = mpSvc.selectAllMyCommentQna(mbId, pageNumber);

		if( comqnaMap != null ) {
			int totalRecords = (int)comqnaMap.get("totalRecords");
			int maxPG = (int)comqnaMap.get("maxPG");
			List<QnaVO> qnaList = (List<QnaVO>)comqnaMap.get("qnaList");

			model.addAttribute("totalRecords", totalRecords);
			model.addAttribute("maxPG", maxPG);
			model.addAttribute("qnaList", qnaList);
			model.addAttribute("pn", pageNumber);
		} else {
			model.addAttribute("msg_status", "문의 내역" );
			model.addAttribute("mp_msg", mb.getNicname() + " 님의 문의 내역이 없습니다.");
		}
		return "member/mypage/activity/mypage_qna";

	}
//펀딩신청내역 확인하기	펀딩 날림. -세현


//회원이 보유중인 쿠폰 표시							나의쿠폰
//	mypage_coupon.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_coupon_info.LF", method=RequestMethod.POST)
	public String memberMypageCoupon( HttpSession ses,
			@RequestParam(value = "pn", required = false, defaultValue = "1" ) int pageNumber,
			Model model ) {
		System.out.println("memberMypageCoupon()...");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		int mbId = mb.getId();
		Map<String, Object> couponMap = mpSvc.selectAllMyCoupon(mbId, pageNumber); // select 저함수안에서 디버그처리
		if( couponMap != null ) {
			int totalRecords = (int)couponMap.get("totalRecords");
			int maxPG = (int)couponMap.get("maxPG");
			List<CouponVO> couponList = (List<CouponVO>)couponMap.get("couponList");
			model.addAttribute("totalRecords", totalRecords);
			model.addAttribute("maxPG", maxPG);
			model.addAttribute("couponList", couponList);
			model.addAttribute("pn", pageNumber);
		} else {
			System.out.println("couponMap 은 null");
			model.addAttribute("msg_status", "나의 쿠폰" );
			model.addAttribute("mp_msg", mb.getNicname() + " 님이 등록하신 쿠폰이 없습니다.");
		}

		return "member/mypage/activity/mypage_coupon_info";
	}


//// 정보관리

// 회원이 가입시 입력한 정보 수정하기							회원정보 수정
//	mypage_update_info.lf(proc,post,dao)		해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_mb_update.LF", method=RequestMethod.POST)
	public String memberMypageUpdateInfo(HttpSession ses) {
		System.out.println("memberMypageUpdateInfo()...");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		String postalcode = mb.getPostalCode()+"";
		if (postalcode.length()!=5) {
			System.out.println("우편번호 길이 오류상태");
			for (int i=postalcode.length(); i<5; i++) {
				postalcode = "0"+postalcode;
			}
		}
		ses.setAttribute("postalcode", postalcode);
		return "member/mypage/info_manager/mypage_mb_update";
	}

	@RequestMapping(value="mypage_mb_update_proc.LF", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberMypageUpdateInfoProc(
			HttpSession ses,
			@RequestParam(value="nickname") String nickname,
			@RequestParam(value="ph1") String ph1,
			@RequestParam(value="ph2") String ph2,
			@RequestParam(value="agreeEmail") String agreeEmail,
			@RequestParam(value="agreeSms") String agreeSms,
			@RequestParam(value="postalcode") int postalcode,
			@RequestParam(value="basicAddress") String basicAddress,
			@RequestParam(value="detailAddress") String detailAddress
			) {
		System.out.println("memberMypageUpdateInfoProc()...");
		String templateTop =
				"<div class=\"popup\">" +
		"<a class=\"close\" href=\"#\">x</a>" +
		"<div class=\"mypage_mb_update_popup_content\">" +
			"<h2 class=\"mypage_mb_isupdate\">";
		String templateMiddle = "";//mb.getName() +"'님 회원정보 수정 성공";
		String templateBottom = "</h2></div>" +
				"<input id=\"mypage_mb_update_popup_submitbtn\" type=\"button\" value=\"확인\" onclick='goBack()'>" +
				"</div>";
		Map<String, Object> rMap = new HashMap<>();
		MemberVO mb = (MemberVO)ses.getAttribute("member");

		if(mb==null)
			templateMiddle = "로그아웃되셨습니다. 재로그인이 필요합니다.";
		else {
			Map<String, Object>updateMap =
					mpSvc.updateOneMemberInfo(mb, nickname, ph1, ph2, agreeEmail,
					agreeSms, postalcode, basicAddress, detailAddress);
			templateMiddle = (String)updateMap.get("update_info_msg");
			ses.setAttribute("member", updateMap.get("update_member"));
		}
		System.out.println(templateMiddle);
		String template = templateTop + templateMiddle + templateBottom;
		rMap.put("temp", template);

		return rMap;
	}



//회원의 비밀번호 변경하기								비밀번호 변경
//	mypage_update_pw.lf(proc,post,dao)			해당 조각페이지 불러오게 리턴
	@RequestMapping(value="mypage_pw_update.LF", method=RequestMethod.POST)
	public String memberMypageUpdatePw() {
		System.out.println("memberMypageUpdatePw()...");
		return "member/mypage/info_manager/mypage_pw_update";
	}

	@RequestMapping(value="mypage_pw_update_proc.LF", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberMypageUpdatePwProc(
			HttpSession ses,
			@RequestParam(value="oldPw") String oldPw,
			@RequestParam(value="newPw") String newPw,
			@RequestParam(value="confirmPw") String confirmPw
			) {
		System.out.println("memberMypageUpdateInfoProc()...");
		String templateTop =
				"<div class=\"popup\">" +
		"<a class=\"close\" href=\"#\">x</a>" +
		"<div class=\"mypage_pw_update_popup_content\">" +
			"<h2 class=\"mypage_pw_isupdate\">";
		String templateMiddle = "";//mb.getName() +"'님 비밀번호 수정 성공
		String templateBottom = "</h2></div>" +
				"<input id=\"mypage_pw_update_popup_submitbtn\" type=\"button\" value=\"확인\" onclick='goBack()'>" +
				"</div>";
		Map<String, Object> rMap = new HashMap<>();
		MemberVO mb = (MemberVO)ses.getAttribute("member");

		if(mb==null)
			templateMiddle = "로그아웃되셨습니다. 재로그인이 필요합니다.";
		else {
			if(logSvc.loginProcess(mb.getEmail(),oldPw)==logSvc.MB_EMAIL_AUTH_OK)
				templateMiddle = (String)mpSvc.updateOneMemberPw(mb.getEmail(), newPw, confirmPw).get("update_pw_msg");
			else
				templateMiddle = "비밀번호가 틀립니다!";
		}

		System.out.println(templateMiddle);
		String template = templateTop + templateMiddle + templateBottom;
		rMap.put("temp", template);

		return rMap;
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

//	@RequestMapping(value="mypage_delivery_info.LF", method=RequestMethod.POST)
//	public String memberMypageDeliveryInfo(HttpSession ses,
//			Model model )
//	{
//
//		// payHistory 에서 deliveryStatus 체크해야됨
//		System.out.println("memberMypageDeliveryInfo()...");
//		MemberVO mb = (MemberVO)ses.getAttribute("member");
//		int mbId = mb.getId();
//		System.out.println("mbId = " + mbId);
//		Map<String, Object> rMap = mpSvc.selectMyPageDeliveryInfoMap(mbId);
//		if(rMap != null) {
//			List<PayHistoryVO> phisList =
//					(List<PayHistoryVO>)rMap.get("phisList");
//			List<CreatorVO> creList = (List<CreatorVO>)rMap.get("creList");
//			List<KitVO> kitList = (List<KitVO>)rMap.get("kitList");
//			int[] deliveryStatusArray = (int[])rMap.get("deliveryStatusArray");
//			int kitCount = (int)rMap.get("kitCount");
//
//			model.addAttribute("phisList", phisList);
//			model.addAttribute("creList", creList);
//			model.addAttribute("kitList", kitList);
//			model.addAttribute("deliveryStatusArray", deliveryStatusArray);
//			model.addAttribute("kitCount", kitCount);
//		} else {
//
//		}
//
//		return "member/mypage/order_manager/mypage_delivery_info";
//	}

	/* Order confirmation 주문서확인   == Payment waiting 결제 대기중
	Preparing product 상품준비중 == Delivery Preparation 배송준비
	Shipping in progress 배송중
	Delivery completed 	배송완료		*/
//	@RequestMapping(value="delivery_stat1.LF", method=RequestMethod.POST)
//	public String memberMyPageDeliveryStatPaymentWaiting(HttpSession ses,
//			@RequestParam(value="deliveryStat", defaultValue="1") int deliveryStat,
//			Model model) {
//		System.out.println("delivery_stat1.LF 컨트롤러도착");
//		MemberVO mb = (MemberVO)ses.getAttribute("member");
//		if(mb != null) {
//			int mbId = mb.getId();
//			Map<String, Object> rMap = mpSvc.selectMyPageDeliveryStatMap(mbId, deliveryStat);
//			// phisList kitList creList kitCount
//			List<PayHistoryVO> phisList = (List<PayHistoryVO>)rMap.get("phisList");
//			List<CreatorVO> creList = (List<CreatorVO>)rMap.get("creList");
//			List<KitVO> kitList = (List<KitVO>)rMap.get("kitList");
//
//			model.addAttribute("delStatHead", "결제대기");
//			model.addAttribute("phisList", phisList);
//			model.addAttribute("creList", creList);
//			model.addAttribute("kitList", kitList);
//
//		} else { // mb == null 로그인페이지로 보내야됨
//			model.addAttribute("delStatHead", "결제대기");
//			model.addAttribute("delStat", "결제대기중인");
//		}
//		return "member/mypage/order_manager/delivery_stat";
//	}

//	@RequestMapping(value="delivery_stat2.LF", method=RequestMethod.POST)
//	public String memberMyPageDeliveryStatDeliveryPreparation(HttpSession ses,
//			@RequestParam(value="deliveryStat", defaultValue="2") int deliveryStat,
//			Model model) {
//		System.out.println("delivery_stat2.LF 컨트롤러도착");
//		MemberVO mb = (MemberVO)ses.getAttribute("member");
//		if(mb != null) {
//			int mbId = mb.getId();
//			Map<String, Object> rMap = mpSvc.selectMyPageDeliveryStatMap(mbId, deliveryStat);
//			// phisList kitList creList kitCount
//			List<PayHistoryVO> phisList = (List<PayHistoryVO>)rMap.get("phisList");
//			List<CreatorVO> creList = (List<CreatorVO>)rMap.get("creList");
//			List<KitVO> kitList = (List<KitVO>)rMap.get("kitList");
//
//			model.addAttribute("delStatHead", "배송준비");
//			model.addAttribute("phisList", phisList);
//			model.addAttribute("creList", creList);
//			model.addAttribute("kitList", kitList);
//
//		} else { // mb == null 로그인페이지로 보내야됨
//			model.addAttribute("delStatHead", "결제대기");
//			model.addAttribute("delStat", "배송준비중인");
//		}
//		return "member/mypage/order_manager/delivery_stat";
//	}

//	@RequestMapping(value="delivery_stat3.LF", method=RequestMethod.POST)
//	public String memberMyPageDeliveryStatShippingInProgress(HttpSession ses,
//			@RequestParam(value="deliveryStat", defaultValue="3") int deliveryStat,
//			Model model) {
//		MemberVO mb = (MemberVO)ses.getAttribute("member");
//		if(mb != null) {
//			int mbId = mb.getId();
//			Map<String, Object> rMap = mpSvc.selectMyPageDeliveryStatMap(mbId, deliveryStat);
//			// phisList kitList creList kitCount
//			List<PayHistoryVO> phisList = (List<PayHistoryVO>)rMap.get("phisList");
//			List<CreatorVO> creList = (List<CreatorVO>)rMap.get("creList");
//			List<KitVO> kitList = (List<KitVO>)rMap.get("kitList");
//
//			model.addAttribute("delStatHead", "배송중");
//			model.addAttribute("phisList", phisList);
//			model.addAttribute("creList", creList);
//			model.addAttribute("kitList", kitList);
//
//		} else { // mb == null 로그인페이지로 보내야됨
//			model.addAttribute("delStatHead", "배송중");
//			model.addAttribute("delStat", "배송중인");
//		}
//		return "member/mypage/order_manager/delivery_stat";
//	}

//	@RequestMapping(value="delivery_stat4.LF", method=RequestMethod.POST)
//	public String memberMyPgeDeliveryStatDeliveryCompleted(HttpSession ses,
//			@RequestParam(value="deliveryStat", defaultValue="4") int deliveryStat,
//			Model model) {
//		System.out.println("delivery_stat4.LF 컨트롤러도착");
//		MemberVO mb = (MemberVO)ses.getAttribute("member");
//		if(mb != null) {
//			int mbId = mb.getId();
//			Map<String, Object> rMap = mpSvc.selectMyPageDeliveryStatMap(mbId, deliveryStat);
//			// phisList kitList creList kitCount
//			List<PayHistoryVO> phisList = (List<PayHistoryVO>)rMap.get("phisList");
//			List<CreatorVO> creList = (List<CreatorVO>)rMap.get("creList");
//			List<KitVO> kitList = (List<KitVO>)rMap.get("kitList");
//
//			model.addAttribute("delStatHead", "배송완료");
//			model.addAttribute("phisList", phisList);
//			model.addAttribute("creList", creList);
//			model.addAttribute("kitList", kitList);
//
//		} else { // mb == null 로그인페이지로 보내야됨
//			model.addAttribute("delStatHead", "배송완료");
//			model.addAttribute("delStat", "배송완료된");
//		}
//		return "member/mypage/order_manager/delivery_stat";
//	}

	@RequestMapping(value="mypage_pay_{payStatus}.LF", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberMyPageShowPayStatus(HttpSession ses,
			@PathVariable(value="payStatus") String payStatus ){
		System.out.println("controller :: memberMyPageShowPayStatus()..");
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		if(mb != null && payStatus != null && !payStatus.isEmpty()) {
			int mbId = mb.getId();
			Map<String, Object> pMap =
					mpSvc.selectMemberPayHistoriesByPayStatusMbId(payStatus, mbId);
		} else {
			System.out.println("mb == null 이거나 payStatus == null 이거나 payStatus.isEmpty() == true");
		}
		return null;
	}



	@RequestMapping(value="mypage_recive_address.LF", method=RequestMethod.GET) // ???/
	public String memberMypageReciveAddress() {
		System.out.println("memberMypageReciveAddress()...");
		return "member/mypage/order_manager/mypage_delivery_info";
	}


////////////////////////////////////////////////////

	// 05/29 마이페이지 프로필 사진 수정
	@RequestMapping(value="mypage_change_pic_proc.LF", method=RequestMethod.POST) // ???/
	@ResponseBody
	public Map<String, Object> mypageChangePicProc(
			HttpSession ses, Model model, MultipartFile mypageimgProc
			) {
		System.out.println("mypageChangePicProc()...");
		Map<String, Object> rMap = new HashMap<>();
		MemberVO mb = (MemberVO)ses.getAttribute("member");
		if(mb==null)
			return rMap;
		rMap.put("file",fiSvc.writeFile(mypageimgProc, mb.getId(), mb.getName()).get("file"));
		if(rMap.get("file")!=null) {
			mpSvc.updateMemberProfileImg(mb.getId(), (String)rMap.get("file"));
		}
		return rMap;
	}
	




}
