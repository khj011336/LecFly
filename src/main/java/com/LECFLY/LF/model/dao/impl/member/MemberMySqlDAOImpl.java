package com.LECFLY.LF.model.dao.impl.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.net.ntp.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.LECFLY.LF.model.dao.inf.member.IMemberDAO;
import com.LECFLY.LF.model.vo.member.MemberVO;

@Repository
public class MemberMySqlDAOImpl implements IMemberDAO {
	
	class MemberImplRowMapper implements RowMapper<MemberVO> {

		@Override
		public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberVO mb = new MemberVO(rs.getInt("id"), 
					rs.getString("pic"), rs.getString("name"),
					rs.getString("nicname"), rs.getTimestamp("birthday"),
					rs.getInt("gender"), rs.getString("email"),
					rs.getString("ph_number"), rs.getTimestamp("joined_at"),
					rs.getInt("agree_receive"), rs.getInt("use_ticket"),
					rs.getInt("check_creator"), rs.getInt("login_count"),
					rs.getTimestamp("loginedat"), 
					// 이부분부터는 모르겠어서 MemberVO 보고 때려맞춤 sql 콜룸명이랑 타입 맞는지확인부탁
					rs.getString("baisc_address"), 
					rs.getString("detail_address"), rs.getInt("postal_code"), 
					rs.getString("address_name"), rs.getString("receiver_name"));
			
			if(mb != null) {
				System.out.println("mb = " + mb);
				return mb;
			} else {
				System.out.println("memberMySqlDAOImpl.. rs.get() 틀림");
				return null;
			}
		}
		
	}
	
	//sql 정의부
	public static final String SQL_INSERT_NEW_MEMBER = 
			"insert into members values(null,?,?,?,?,?,?,hex(aes_encrypt(?,?)), " 
					+"?,now(),?,0,0,0,now(),?,?,?,null,null)";
//			"insert into members values(null,?,?,?,?,?,?,"
//			+ "hex(aes_encrypt(?,?)),?,now(),?,0,0,0,now(),?,?,?,null,null)";
	private static final String SQL_SELECT_MEMBER_ID_BY_EMAIL = "select id from members where email=?";
	private static final String SQL_SELECT_MEMBER_COUNT_BY_NIC = "select count(id) from members where nicname=?";
	private static final String SQL_SELECT_MEMBER_BY_ID = "select * from members where id=?";
	private static final String SQL_SELECT_MEMBER_BY_NIC = "select * from members where nicname=?";
	private static final String SQL_SELECT_MEMBER_PW_CHECK = "select * from members where password = hex(aes_encrypt(?,?))";
	private static final String SQL_FIND_MB_EMAIL = "select email from members where ph_number=? and name=?";
	private static final String SQL_FIND_MB_EMAIL_IN_DB = "select * from members where email=?";
	private static final String SQL_UPDATE_MEMBER_PW_BY_EMAIL = 
			"update members set password=hex(aes_encrypt(?,?)) where email=?";
	private static final String SQL_UPDATE_ONE_MEMBER_BY_INFO = 
			"update members set nicname=?,ph_number=?,agree_receive=?,basic_address=?,detail_address=?,postalcode=? where id=?";
	private static final String SQL_INC_MEMBER_LOGIN_CNT="update members set login_count=login_count+1 where id=?";
	private static final String SQL_UPDATE_MEMBER_LOGIN_DATE="update members set logined_at=now() where id=?";
	private static final String SQL_UPDATE_MEMBER_PRO_PIC = "update members set pic=? where id=?";
//	private static final String SQL_="";
	
	@Autowired
	private JdbcTemplate jtem;
	
	@Override
	public boolean insertNewMember(MemberVO mb) {
		// TODO Auto-generated method stub
		return false;
	}
	
//	회원가입하기(Create)
	@Override
	public boolean insertNewMember(String pic, String name, String nicname, Timestamp birthday, int gender,
			String email, String password, String phNumber, int agreeReceive, String basicAddress, String detailAddress,
			int postalCode) {
		PwSecurityEncoding pwCode = new PwSecurityEncoding(email);
		boolean r = this.jtem.update(SQL_INSERT_NEW_MEMBER, 
				pic, name, nicname, birthday, gender, email, password, pwCode.getEmail(), phNumber,
				agreeReceive, basicAddress, detailAddress, postalCode) == 1;
		return r;
	}	

	@Override
	public List<MemberVO> selectAllMembersAscLimitTen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> selectAllMembersDescLimitTen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> selectOneMonthInsertMembersAscLimitTen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> selectOneMonthInsertMembersDescLimitTen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO selectOneMemberById(int id) {
		System.out.println("jtem selectOneMemberById");
		return jtem.queryForObject(SQL_SELECT_MEMBER_BY_ID, 
				BeanPropertyRowMapper.newInstance(MemberVO.class), id);
	}

	@Override
	public MemberVO selectOneMemberByNicName(String nicname) {
		System.out.println("jtem selectOneMemberByNicName");
		return jtem.queryForObject(SQL_SELECT_MEMBER_BY_NIC, 
				BeanPropertyRowMapper.newInstance(MemberVO.class), nicname);
	}

	@Override
	public MemberVO selectOneMemberByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO selectOneMemberByPhNumber(String phNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateMemberById(int id, MemberVO mb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMemberByEmail(String email, MemberVO mb) {
		
		return false;
	}
	
	@Override
	public boolean updateMemberNicnameToId(int id, String nicname) {
		// TODO Auto-generated method stub
		return false;
	}
	
//	회원의 비밀번호 변경하기 (Update)
	@Override
	public boolean updateMemberPasswordToId(int id, String password) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean updateMemberAgreeReceiveById(int id, int agreeReceive) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMemberUseTicketById(int id, int useTicket) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMemberAgreeReceiveByEmail(String email, int agreeReceive) {
		
		return false;
	}


	@Override
	public boolean updateMemberUseTicketByEmail(String email, int useTicket) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int doubleCheckEmailPassword(String email, String password) {
		// TODO Auto-generated method stub
		return 0;
	}


//	로그인하기(이메일 유무 체크, Read)
	@Override
	public int memberEamil(String email) {
		try {
			return this.jtem.queryForObject(SQL_SELECT_MEMBER_ID_BY_EMAIL, Integer.class, email);
		} catch (Exception e) {
			System.out.println("sql오류");
			return 0;
		}
	}

//	로그인하기(이메일과 패스워드 일치 체크, Read)
	@Override
	public MemberVO memberPassword(String email, String password) {
		try {
			MemberVO mb = jtem.queryForObject(SQL_SELECT_MEMBER_PW_CHECK, 
					BeanPropertyRowMapper.newInstance(MemberVO.class), password, new PwSecurityEncoding(email).getEmail());
			mb.setPassword(null);
			return mb;
		} catch (Exception e) {
			System.out.println("sql오류");
			return null;
		}
//		return 0;
//		boolean r = jtem.query();
	}
	
// 이름과 전화번호로 이메일 찾기
	@Override
	public MemberVO findEmailByPhNmuberAndName(String phNumber, String name) {
		return this.jtem.queryForObject(SQL_FIND_MB_EMAIL, BeanPropertyRowMapper.newInstance(MemberVO.class), phNumber, name);
	}

// 가입된 이메일로 임시 비밀번호 생성하기
	@Override
	public boolean setNewPwByEmail(String email, String pw) {
		
		return false;
	}
//	회원의 프로필 사진 수정하기 (Update)
	@Override
	public boolean updatePicById(int id) {
		// TODO Auto-generated method stub
		return false;
	}
//	크리에이터 상태 업데이트(Udpate)
	@Override
	public boolean setCreatorStatusById(int id, int creatorStatus) {
		// TODO Auto-generated method stub
		return false;
	}
//	회원이 가입시 입력한 정보 수정하기 (Update)
	@Override
	public boolean updateOneMemberById(int id, MemberVO mb) {
		// TODO Auto-generated method stub
		return false;
	}

	
//	회원탈퇴...?(Delete)
	@Override
	public boolean deleteOneMemberById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberVO findEmailInDB(String email) {
		System.out.println("jdbc: findEmailInDB");
		return this.jtem.queryForObject(SQL_FIND_MB_EMAIL_IN_DB, BeanPropertyRowMapper.newInstance(MemberVO.class), email);
	}

	
	
	// 세현 추가 마이페이지에서 회원 id, 업데이트할 사진 path 입력하면은 sql 에 업데이트 하려고함
	@Override
	public boolean updateMemberProfileImg(int mbId, String filePath) {
		System.out.println("jdbc: updateMemberProfileImg");
		int r = this.jtem.update(SQL_UPDATE_MEMBER_PRO_PIC, filePath, mbId);
		System.out.println("프로필 사진 업데이트 sql 지나감");
		return r==1;
	}
	
	
	@Override
	public boolean updateMemberPasswordToEmail(String email, String password) {
		System.out.println("jdbc: updateMemberPasswordToEmail");
		int r = this.jtem.update(SQL_UPDATE_MEMBER_PW_BY_EMAIL, password, 
				new PwSecurityEncoding(email).getEmail(), email);
		return r == 1;
	}

	@Override
	public boolean dupCheckNickname(String nickname) {
		System.out.println("jdbc: dupCheckNickname");
		int r = jtem.queryForObject(SQL_SELECT_MEMBER_COUNT_BY_NIC, Integer.class, nickname);
		System.out.println(r);
		return r>=1;
	}

	@Override
	public boolean updateOneMemberInfo(MemberVO mb) {
		System.out.println("updateOneMemberInfo");
		int r = this.jtem.update(SQL_UPDATE_ONE_MEMBER_BY_INFO,
				mb.getNicname(), mb.getPhNumber(), mb.getAgreeReceive(), 
				mb.getbasicAddress(), mb.getDetailAddress(), mb.getPostalCode(), mb.getId());
		return r==1;
	}

	@Override
	public boolean incLoginCnt(int id) {
		System.out.println("DAO: incLoginCnt 로그인 횟수 증가");
		return jtem.update(SQL_INC_MEMBER_LOGIN_CNT, id) == 1;
	}

	@Override
	public boolean updateLoginDate(int id) {
		System.out.println("DAO: updateLoginDate 로그인 날자 갱신");
		return jtem.update(SQL_UPDATE_MEMBER_LOGIN_DATE, id) == 1;
	}
}
