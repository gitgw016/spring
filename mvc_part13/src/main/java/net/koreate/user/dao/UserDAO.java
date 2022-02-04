package net.koreate.user.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import net.koreate.user.vo.LoginDTO;
import net.koreate.user.vo.UserVO;

public interface UserDAO {

	// 회원가입
	@Insert("INSERT INTO tbl_user(uid,upw,uname) VALUES(#{uid},#{upw},#{uname})")
	void signUp(UserVO vo) throws Exception;
	// 로그인
	@Select("SELECT * FROM tbl_user WHERE uid = #{uid} AND upw = #{upw}")
	UserVO signIn(LoginDTO dto) throws Exception;
	// id로 사용자 정보 검색
	@Select("SELECT * FROM tbl_user WHERE uid = #{uid}")
	UserVO getUserByID(String uid) throws Exception;
}
