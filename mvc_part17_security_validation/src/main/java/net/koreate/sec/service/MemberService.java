package net.koreate.sec.service;

import java.util.List;

import net.koreate.sec.vo.AuthVO;
import net.koreate.sec.vo.ValidationMemberVO;

public interface MemberService {
	
	// 회원가입
	void memberJoin(ValidationMemberVO vo) throws Exception;
	
	// 아이디 체크
	boolean getMemberByID(String u_id) throws Exception;
	
	// 최종 방문 시간 현재시간으로 수정
	void updateVisitDate(String u_id) throws Exception;
	
	// 회원탈퇴 여부 y,n
	void DeleteYN(ValidationMemberVO vo) throws Exception;
	
	// 회원 권한 정보 수정
	// 권한 정보가 존재하면 삭제
	// 권한 정보가 존재하지 않으면 추가
	// 해당 회원의 권한 정보를 List로 반환
	List<AuthVO> updateAuth(AuthVO vo) throws Exception;
	
	// 회원 리스트 정보
	List<ValidationMemberVO> getMemberList() throws Exception;
}
