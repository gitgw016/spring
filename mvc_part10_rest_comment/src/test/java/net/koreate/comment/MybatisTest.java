package net.koreate.comment;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.comment.dao.CommentDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/root-context.xml"})
public class MybatisTest {

	@Inject
	SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void test1SqlSessionFactory() {
		SqlSession session = sqlSessionFactory.openSession();
		System.out.println("session connection : "+session.getConnection());
	}
	
	@Inject
	CommentDAO dao;
	
	@Test
	public void test2DAO() throws Exception {
		System.out.println(dao);
		System.out.println(dao.commentList(1));
	}
}
