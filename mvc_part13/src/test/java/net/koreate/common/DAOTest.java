package net.koreate.common;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.user.dao.UserDAO;
import net.koreate.user.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:context/root-context.xml"})
public class DAOTest {

	@Inject
	UserDAO dao;
	
	@Test
	public void userDaoTest() throws Exception {
		UserVO vo = dao.getUserByID("bmg");
		System.out.println(vo);
	}
}
