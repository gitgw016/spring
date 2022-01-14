package net.koreate.test.dao;

import net.koreate.test.vo.MemberVO;

public interface MemberDAOMultiParam {
	
	MemberVO readMemberWithPass(String userid, String userpass);
}
