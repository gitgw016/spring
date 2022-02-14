package net.koreate.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.comment.vo.CommentVO;
import net.koreate.common.utils.Criteria;

public interface CommentDAO {

	// 댓글 삽입
	@Insert("INSERT INTO re_tbl_comment(bno,commentText,uno) VALUES(#{bno},#{commentText},#{uno})")
	void addComment(CommentVO vo) throws Exception;
	
	// 댓글 수정
	@Update("UPDATE re_tbl_comment SET commentText = #{commentText} , updatedate = now() WHERE cno = #{cno}")
	void modifyComment(CommentVO vo) throws Exception;
	
	// 댓글 삭제
	@Delete("DELETE FROM re_tbl_comment WHERE cno = #{cno}")
	void removeComment(int cno) throws Exception;
	
	// 해당 게시글에 등록된 모든 댓글 삭제
	@Delete("DELETE FROM re_tbl_comment WHERE bno = #{bno}")
	void deleteComment(int bno) throws Exception;
	
	// 페이징 블럭 처리를 위한 전체 댓글 갯수
	@Select("SELECT count(cno) FROM re_tbl_comment WHERE bno = #{bno}")
	int totalCount(int bno) throws Exception;
	
	// 페이징 처리된 댓글 리스트
	@Select("SELECT C.*, U.uname AS commentAuth FROM re_tbl_comment AS C NATURAL JOIN tbl_user AS U WHERE bno = #{bno} ORDER BY cno DESC LIMIT #{cri.pageStart} , #{cri.perPageNum}")
	List<CommentVO> listPage(@Param("bno") int bno, @Param("cri") Criteria cri) throws Exception;
}
