package net.koreate.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.comment.vo.CommentVO;
import net.koreate.mvc.common.util.Criteria;

public interface CommentDAO {

	@Select("SELECT * FROM tbl_comment WHERE bno = #{bno} ORDER BY cno DESC")
	List<CommentVO> commentList(int bno) throws Exception;
	
	@Insert("INSERT INTO tbl_comment(bno,commentText,CommentAuth) VALUES(#{bno} , #{commentText} , #{commentAuth})")
	int add(CommentVO vo) throws Exception;
	
	@Update("UPDATE tbl_comment SET commentAuth = #{commentAuth} , commentText = #{commentText} , updatedate = now() WHERE cno = #{cno}")
	int update(CommentVO vo) throws Exception;
	
	@Update("UPDATE tbl_comment SET commentDelete = 'Y' WHERE cno = #{cno}")
	int delete(int cno) throws Exception;
	
	List<CommentVO> listPage(@Param("bno") int bno, @Param("cri") Criteria cri) throws Exception;
}
