package net.koreate.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.mvc.vo.MessageVO;

public interface MessageDAO {

	// message 삽입
	@Insert("INSERT INTO tbl_message(targetid,sender,message) VALUES(#{targetid} , #{sender} , #{message})")
	void create(MessageVO vo) throws Exception;

	@Select("SELECT * FROM tbl_message ORDER BY mno DESC")
	List<MessageVO> list() throws Exception;

	@Select("SELECT * FROM tbl_message WHERE mno = #{mno}")
	MessageVO readMessage(int mno) throws Exception;

	@Update("UPDATE tbl_message SET opendate = now() WHERE mno = #{mno}")
	void updateMessage(int mno) throws Exception;
	
}
