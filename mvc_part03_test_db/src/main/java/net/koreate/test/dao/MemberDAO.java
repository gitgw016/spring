package net.koreate.test.dao;

import java.util.List;

import net.koreate.test.vo.MemberVO;

public interface MemberDAO {

	void insertMember(MemberVO vo);
	
	MemberVO readMember(String userid);
	
	List<MemberVO> readMemberList();
}
