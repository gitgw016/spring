package net.koreate.sec.security.user;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.koreate.sec.dao.MemberDAO;
import net.koreate.sec.vo.ValidationMemberVO;

public class CustomUserDetailService implements UserDetailsService {

	@Inject
	MemberDAO dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ValidationMemberVO vo = null;
		try {
			vo = dao.getMemberByID(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo == null ? null : new CustomMember(vo);
	}

}
