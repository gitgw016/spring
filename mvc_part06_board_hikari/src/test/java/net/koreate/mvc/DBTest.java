package net.koreate.mvc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.mvc.board.dao.BoardDAO;
import net.koreate.mvc.board.vo.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context/root-context.xml"})
public class DBTest {

	@Test
	public void test1() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver 연결 됐다");
		} catch (ClassNotFoundException e) {
			System.out.println("실패애~");
		}
	}
	
	@Inject
	DataSource ds;
	
	@Test
	public void test2() {
		Connection conn = null;
		try {
			System.out.println(ds);
			conn = ds.getConnection();
			System.out.println("Hikari Test Connection"+conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Inject
	BoardDAO dao;
	
	@Test
	public void test3() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("민구같은 제목이다");
		vo.setContent("민구 안선다");
		vo.setWriter("붸밍꾸");
		int result = dao.create(vo);
		System.out.println("result : "+result);
	}
}
