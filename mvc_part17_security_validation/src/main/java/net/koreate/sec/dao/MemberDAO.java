package net.koreate.sec.dao;

import java.util.List;

import net.koreate.sec.vo.AuthVO;
import net.koreate.sec.vo.ValidationMemberVO;

public interface MemberDAO {

	void memberJoin(ValidationMemberVO vo);

	ValidationMemberVO getMemberByID(String u_id) throws Exception;

	// 회원가입한 회원에게 기본 권한 추가 ROLE_USER
	void insertAuth(String u_id) throws Exception;

	void updateVisitDate(String u_id) throws Exception;

	// 등록된 사용자 리스트
	List<ValidationMemberVO> getMemberList() throws Exception;

	// 활성화 여부 수정
	void deleteYN(ValidationMemberVO vo) throws Exception;

	// u_id로 사용자의 모든 권한 검색
	List<AuthVO> getAuthList(String u_id) throws Exception;

	// 권한 부여
	void insertMemberAuth(AuthVO vo) throws Exception;

	// 권한 회수
	void deleteAuth(AuthVO auth) throws Exception;
}
