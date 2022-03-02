package net.koreate.sec;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.sec.dao.MemberDAO;
import net.koreate.sec.vo.ValidationMemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:root/root-context.xml","classpath:security/security-context.xml"})
public class MemberTest {

	@Inject
	MemberDAO dao;
	
	@Inject
	PasswordEncoder pwencoder;
	
	@Inject
	DataSource ds;
	
	@Test
	public void passwordEncoder() throws Exception{
		String u_pw = "alsrn@123";
		String encoder = pwencoder.encode(u_pw);
		System.out.println("암호화 전 : "+u_pw);
		System.out.println("암호화 후 : "+encoder);
		// $2a$10$OGjIa1LMT1RCqRrJUUXppOe/X5Z..xwmi/aCp8yJvtoDwjhIbDwaC
		// $2a$10$q06QlI9kyN7Qs4Gk5nu1t.vaKV4ZnOXD.B4rq0hQTCnFc4Ltv7/sW
		System.out.println(pwencoder.matches(u_pw, encoder));
		
		String sql = "UPDATE validation_member SET u_pw = ? WHERE u_id = ?";
		String u_id = "mingu@norise.net";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = ds.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, encoder);
		pstmt.setString(2, u_id);
		int result = pstmt.executeUpdate();
		System.out.println(result);
		pstmt.close();
		conn.close();
	}
	
	// @Test
	public void testDAO() {
		System.out.println(dao);
		ValidationMemberVO vo = new ValidationMemberVO();
		vo.setU_id("mingu@norise.net");
		vo.setU_pw("mingu@123");
		vo.setU_name("배민구");
		vo.setU_phone("01039034660");
		vo.setU_birth("19800228");
		vo.setU_post("47171");
		vo.setU_addr("부산광역시 동래구 금정마을");
		vo.setU_addr_detail("민구네집");
		dao.memberJoin(vo);
	}
}
