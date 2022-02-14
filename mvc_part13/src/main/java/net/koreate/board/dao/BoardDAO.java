package net.koreate.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import net.koreate.board.provider.BoardQueryProvider;
import net.koreate.board.vo.BoardVO;
import net.koreate.common.utils.Criteria;

public interface BoardDAO {

	@Insert("INSERT INTO re_tbl_board(title,content,uno) VALUES(#{title},#{content},#{uno})")
	void regist(BoardVO vo) throws Exception;
	
	@Update("UPDATE re_tbl_board SET origin = LAST_INSERT_ID() WHERE bno = LAST_INSERT_ID()")
	void updateOrigin() throws Exception;
	
	// 게시글 목록 검색
	/*@Select("SELECT B.*, U.uname AS writer FROM re_tbl_board AS B NATURAL JOIN tbl_user AS U ORDER BY B.origin DESC , B.seq ASC limit #{pageStart},#{perPageNum}")*/
	@SelectProvider(type=BoardQueryProvider.class, method="searchSelectSql")
	List<BoardVO> listReply(Criteria cri) throws Exception;

	// 조회수 증가
	@Update("UPDATE re_tbl_board SET viewcnt = viewcnt + 1 WHERE bno = #{bno}")
	void updateCnt(int bno)throws Exception;

	// 게시글 번호가 일치하는 한개의 게시글 정보 검색
	@Select("SELECT B.*, U.uname AS writer FROM re_tbl_board AS B NATURAL JOIN tbl_user AS U WHERE B.bno = #{bno}")
	BoardVO read(int bno) throws Exception;

	// 답변 글들의 정렬값 수정
	@Update("UPDATE re_tbl_board SET seq = seq + 1 WHERE origin = #{origin} AND seq > #{seq}")
	void updateReply(BoardVO vo) throws Exception;

	// 당변글 등록
	@Insert("INSERT INTO re_tbl_board(title,content,origin,depth,seq,uno) VALUES(#{title},#{content},#{origin},#{depth},#{seq},#{uno})")
	void replyRegister(BoardVO vo) throws Exception;

	// re_tbl_board 전체 게시물
	// @Select("SELECT count(*) FROM re_tbl_board")
	@SelectProvider(type=BoardQueryProvider.class, method="searchSelectCount")
	int listCount(Criteria cri) throws Exception;

	// re_tbl_board title,content,updatedate 수정
	@Update("UPDATE re_tbl_board SET title = #{title} , content = #{content} , updatedate = now() WHERE bno = #{bno}")
	void modify(BoardVO vo) throws Exception;

	// 게시글 삭제 처리 showboard = 'N'
	@Update("UPDATE re_tbl_board SET showboard = 'N' WHERE bno = #{bno}")
	void remove(int bno) throws Exception;

	// 첨부파일 --------------------------------
	
	// 첨부파일 등록
	@Insert("INSERT INTO tbl_attach(fullName,bno) VALUES(#{fullName} , LAST_INSERT_ID())")
	void addAttach(String fullName) throws Exception;

	// 게시글 번호와 일치하는 첨부파일 목록
	@Select("SELECT fullName FROM tbl_attach WHERE bno = #{bno}")
	List<String> getAttach(int bno) throws Exception;

	// tbl_attach table에서 bno값이 일치하는 첨부파일의 정보 삭제
	@Delete("DELETE FROM tbl_attach WHERE bno = #{bno}")
	void deleteAttach(int bno) throws Exception;

	// 수정 게시글 첨부파일 등록
	@Insert("INSERT INTO tbl_attach(bno,fullName) VALUES(#{bno} , #{fullName})")
	void replaceAttach(@Param("bno") int bno, @Param("fullName") String fullName) throws Exception;
}
