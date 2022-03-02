package net.koreate.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.koreate.vo.MemberVO;

@Repository("myBatisMember")
public class MemberDAOImpl implements MemberDAO {
	
	private String namespace = "memberMapper";
	
	@Inject
	SqlSession session;

	@Override
	public void insertMember(MemberVO vo) throws Exception {
		System.out.println(vo);
		session.insert(namespace+".insertMember",vo);
	}

}
