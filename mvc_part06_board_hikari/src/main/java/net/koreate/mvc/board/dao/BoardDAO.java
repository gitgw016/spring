package net.koreate.mvc.board.dao;

import java.util.List;

import net.koreate.mvc.board.vo.BoardVO;
import net.koreate.mvc.common.util.Criteria;

public interface BoardDAO {
	// 게시글 작성
	int create(BoardVO vo) throws Exception;
	
	// 게시글 상세보기
	BoardVO read(int bno) throws Exception;
	
	// 게시글 수정
	int update(BoardVO vo) throws Exception;
	
	// 게시글 삭제
	int delete(int bno) throws Exception;
	
	// 조회수 증가
	void updateCnt(int bno) throws Exception;
	
	// 전체 게시물의 개수
	int listCount() throws Exception;
	
	// 페이징 처리된 게시물 리스트
	List<BoardVO> list(Criteria cri) throws Exception;
}
