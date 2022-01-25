package net.koreate.mvc.sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.mvc.common.util.SearchCriteria;
import net.koreate.mvc.sboard.vo.SearchBoardVO;

public interface SearchBoardDAO {

	// 게시글 삽입
	@Insert("INSERT INTO tbl_board(title, content, writer) VALUES(#{title}, #{content}, #{writer})")
	int create(SearchBoardVO vo) throws Exception;
	
	// 게시글 번호가 일치하는 게시글 정보
	@Select("SELECT * FROM tbl_board WHERE bno = #{bno}")
	SearchBoardVO read(int bno) throws Exception;
	
	// 게시글 수정
	@Update("UPDATE tbl_board SET title = #{title} , writer = #{writer} , content = #{content} WHERE bno = #{bno}")
	int update(SearchBoardVO vo) throws Exception;
	
	// 게시글 삭제
	@Delete("DELETE FROM tbl_board WHERE bno = #{bno}")
	int remove(int bno) throws Exception;
	
	// 조회수 증가
	@Update("UPDATE tbl_board SET viewcnt = viewcnt + 1 WHERE bno = #{bno}")
	void updateViewCnt(int bno) throws Exception;
	
	// 페이징 처리 - 검색된 전체 게시물 개수
	int searchListCount(SearchCriteria cri) throws Exception;
	
	// 페이징 처리 - 검색된 게시물 목록
	List<SearchBoardVO> searchList(SearchCriteria cri) throws Exception;
	
}
