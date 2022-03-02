package net.koreate.sec.security.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import net.koreate.sec.vo.AuthVO;
import net.koreate.sec.vo.ValidationMemberVO;

public class CustomMember extends User {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private ValidationMemberVO member;

	public CustomMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomMember(ValidationMemberVO vo) {
		super(vo.getU_id(),vo.getU_pw(),authorities(vo.getAuthList()));
		this.member = vo;
	}
	
	public static Collection<? extends GrantedAuthority> authorities(List<AuthVO> authList){
		List<GrantedAuthority> gList = new ArrayList<>();
		for(AuthVO vo : authList) {
			gList.add(new SimpleGrantedAuthority(vo.getU_auth()));
		}
		return gList;
	}

}
