package net.koreate.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.dao.MemberDAO;
import net.koreate.vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/root-context.xml"})
public class Tests {
	
	@Inject
	DataSource ds;
	
	@Inject
	SqlSessionFactory ssf;
	
	@Autowired
	@Qualifier("myBatisMember")
	MemberDAO dao;
	
	@Test
	public void test() throws Exception{
		Connection conn = null;
		try {
			conn = ds.getConnection();
			System.out.println("database 연결 성공 Connection : " + conn);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("실패");
		}
	}

	@Test
	public void testssf() throws Exception{
		SqlSession session = ssf.openSession();
		System.out.println("SQL SESSION 생성완료 : "+session);
	}
	
	@Test
	public void testInsert() throws Exception{
		MemberVO member = new MemberVO();
		member.setUserid("test1");
		member.setUserpw("testttt");
		member.setUsername("tester");
		member.setEmail("test@test.net");
		dao.insertMember(member);
		System.out.println("입력완료 "+member);
		
	}
}
