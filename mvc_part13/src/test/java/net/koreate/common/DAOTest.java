package net.koreate.common;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import net.koreate.comment.dao.CommentDAO;
import net.koreate.comment.vo.CommentVO;
import net.koreate.common.utils.Criteria;
import net.koreate.user.dao.UserDAO;
import net.koreate.user.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:context/**/*-context.xml"})
@WebAppConfiguration
public class DAOTest {

	@Inject
	UserDAO dao;
	
	@Inject
	CommentDAO cdao;
	
	@Test
	public void commentTest() throws Exception{
		CommentVO vo = new CommentVO();
		vo.setBno(1);
		vo.setUno(2);
		vo.setCommentText("배민재는 배민구다");
		cdao.addComment(vo);
		
		List<CommentVO> commentList = cdao.listPage(2,new Criteria());
		System.out.println(commentList);
	}
	
	//@Test
	public void userDaoTest() throws Exception {
		UserVO vo = dao.getUserByID("bmg");
		System.out.println(vo);
	}
}
