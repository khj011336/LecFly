package com.LECFLY.LF.model.dao.inf.admin;

import java.util.List;

import com.LECFLY.LF.model.vo.creator.CreatorVO;
import com.LECFLY.LF.model.vo.member.MemberVO;
import com.LECFLY.LF.model.vo.virtual.MemberStatVO;

public interface IAdminMemberDAO {
	// 일반 회원 관리
	// 일반 회원을 등록할 수 있다.
	boolean insertNomalMember(MemberVO vo);
	// 일반 회원을 수정할 수 있다.
	boolean updateNomalMember(MemberVO vo);
	// 일반 회원을 삭제할 수 있다.
	boolean deleteNomalMember(MemberVO vo);
	// 일반 회원을 상세조회할 수 있다.
	MemberVO selectNomalMember(MemberVO vo);
	// 일반 회원을 전체조회할 수 있다.
	List<MemberVO> selectNomalMemberList();
	
	// 크리에이터 관리
	// 을 등록할 수 있다.
	boolean insertCreatorMember(CreatorVO vo);
	// 을 수정할 수 있다.
	boolean updateCreatorMember(CreatorVO vo);
	// 을 삭제할 수 있다.
	boolean deleteCreatorMember(CreatorVO vo);
	// 을 상세조회할 수 있다.
	CreatorVO selectCreatorMember(CreatorVO vo);
	// 을 전체조회할 수 있다.
	List<CreatorVO> selectCreatorMemberList();
	
	int checkNumberOfCreators();
	int checkNumberOfMembers();
	
	List<CreatorVO> searchCreatorForAll(int offset, int limit);
	List<MemberVO> searchMemberForAll(int offset, int limit);
	
	//통계
	List<MemberStatVO> statCountMemberByMonth();
	List<MemberVO> searchMemberForAllByApproval(int offset, int limit);
	List<MemberVO> searchMemberForAllByApprovalDone(int offset, int limit);
	List<MemberVO> searchMemberForAllByNew(int offset, int limit);
}
