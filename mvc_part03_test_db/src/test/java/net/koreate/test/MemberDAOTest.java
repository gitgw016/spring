package net.koreate.test;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.test.dao.MemberDAO;
import net.koreate.test.dao.MemberDAOMultiParam;
import net.koreate.test.vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"	
})
public class MemberDAOTest {
	
	//@Inject
	//@Named("memberDAOImpl")
	//@Named("myBatisMember")
	
	@Autowired
	@Qualifier("myBatisMember")
	MemberDAO dao;
	
	@Inject
	MemberDAOMultiParam paramDAO;
	
	//@Test
	public void daoTest() {
		MemberVO member = new MemberVO();
		member.setUserid("jungman");
		member.setUserpw("1234");
		member.setUsername("정만");
		dao.insertMember(member);
	}
	
	@Test
	public void readMember() {
		MemberVO mingu = dao.readMember("qoalsrn");
		System.out.println("readMember : "+mingu);
		List<MemberVO> list = dao.readMemberList();
		for(MemberVO m : list) {
			System.out.println(m);
		}
		System.out.println("----------------------------");
		System.out.println(paramDAO.readMemberWithPass("qoalsrn", "123"));
	}
}
