package com.LECFLY.LF.service.inf.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.LECFLY.LF.model.vo.admin.PayHistoryVO;
import com.LECFLY.LF.model.vo.cart.CouponVO;
import com.LECFLY.LF.model.vo.creator.KitVO;
import com.LECFLY.LF.model.vo.creator.LectureVO;
import com.LECFLY.LF.model.vo.creator.VideoVO;
import com.LECFLY.LF.model.vo.virtual.CategoryLectureStatVO;

public interface IAdminLectureSVC {
	public static int AD_PAGE_SIZE = 20;
	
	// 강의 관리
	// 강의를 등록할 수 있다.
	boolean insertLecture(LectureVO vo);
	// 강의를 수정할 수 있다.
	boolean updateLecture(LectureVO vo);
	// 강의를 삭제할 수 있다.
	boolean deleteLecture(LectureVO vo);
	// 강의를 상세조회할 수 있다.
	LectureVO selectLecture(LectureVO vo);
	// 강의를 전체조회할 수 있다.
	List<LectureVO> selectLectureList();
	
	// 영상 관리
	// 영상을 등록할 수 있다.
	boolean insertVideo(VideoVO vo);
	// 영상을 수정할 수 있다.
	boolean updateVideo(VideoVO vo);
	// 영상을 삭제할 수 있다.
	boolean deleteVideo(VideoVO vo);
	// 영상을 상세조회할 수 있다.
	VideoVO selectVideo(VideoVO vo);
	// 영상을 전체조회할 수 있다.
	List<VideoVO> selectVideoList();
	
	// 키트 관리(Creator폴더)
	// 키트를 등록할 수 있다.
	boolean insertKit(KitVO vo);
	// 키트를 수정할 수 있다.
	boolean updateKit(KitVO vo);
	// 키트를 삭제할 수 있다.
	boolean deleteKit(KitVO vo);
	// 키트를 상세조회할 수 있다.
	KitVO selectKit(KitVO vo);
	// 키트를 전체조회할 수 있다.
	List<KitVO> selectKitList();
	
	// 쿠폰 관리
	// 쿠폰을 등록할 수 있다.
	boolean insertCoupon(CouponVO vo);
	// 쿠폰을 수정할 수 있다.
	boolean updateCoupon(CouponVO vo);
	// 쿠폰을 삭제할 수 있다.
	boolean deleteCoupon(CouponVO vo);
	// 쿠폰을 상세조회할 수 있다.
	CouponVO selectCoupon(CouponVO vo);
	// 쿠폰을 전체조회할 수 있다.
	List<CouponVO> selectCouponList();
	Map<String,Integer> checkLectureMaxPageNumber();
	List<LectureVO> selectAllLecture(int pageNumber);
	List<VideoVO> selectAllVideo(int pageNumber);	
	Map<String,Integer> checkVideoMaxPageNumber();
	List<LectureVO> selectLectureListSearchFilter(Map<String, Object> condition);
	// 관리자 메인 통계파트
	int selectLectureApproval();
	int selectLectureAll();
	int selectTodayInCnt();
	int selectMemberCnt();
	int selectCreatorCnt();
	int selectNewMemberCnt();
	int selectCreatorApprovalCnt();
	int selectQnaCommentCnt();
	List<CategoryLectureStatVO> selectCategoryLectureCnt();
	Map<String, Integer> checkLectureMaxPageNumberSearch(Map<String, Object> searchMap);
	// 상세조회
	List<LectureVO> selectAllLectureSearch(Map<String, Object> searchMap);
	List<LectureVO> selectAllLectureByApproval(int pageNumber);
	List<LectureVO> selectAllLectureByLike(int pageNumber);
	List<LectureVO> selectAllLectureByApprovalDone(int pageNumber);
	// 승인 일괄 처리
	boolean updateLectureApprovalforIds(ArrayList<Integer> checkList);
	boolean updateMemberApprovalforIds(ArrayList<Integer> checkList);
	boolean updateCreatorApprovalforIds(ArrayList<Integer> checkList);
	// 승인 거절 일괄 처리
	boolean updateLectureDisapprovalforIds(ArrayList<Integer> checkList);
	boolean updateMemberDisapprovalforIds(ArrayList<Integer> checkList);
	boolean updateCreatorDisapprovalforIds(ArrayList<Integer> checkList);
	// 일괄 삭제 처리
	boolean deleteLectureforIds(ArrayList<Integer> checkList);
	boolean deleteMemberforIds(ArrayList<Integer> checkList);
	boolean deleteCreatorforIds(ArrayList<Integer> checkList);
	Map<String, Integer> checkPaymentMaxPageNumber();
	List<PayHistoryVO> selectAllPayment(int pageNumber);
	Map<String, Integer> checkCouponMaxPageNumber();
	List<CouponVO> selectAllCoupon(int pageNumber);
	Map<String, Integer> checkKitMaxPageNumber();
	List<KitVO> selectAllKit(int pageNumber);
	List<PayHistoryVO> selectAllPaymentOld(int pageNumber);
}
