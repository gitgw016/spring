package net.koreate.board.service;

import java.util.List;

import net.koreate.board.vo.BoardVO;
import net.koreate.common.utils.Criteria;
import net.koreate.common.utils.PageMaker;

public interface BoardService {

	// 게시글(원본) 추가
	void regist(BoardVO vo) throws Exception;
	
	// 게시글 조회수 증가
	void updatecnt(int bno) throws Exception;
	
	// 게시글 상세 정보
	BoardVO read(int bno) throws Exception;
	
	// 답변글 등록
	void replyRegister(BoardVO vo) throws Exception;
	
	// 게시글 수정
	void modify(BoardVO vo) throws Exception;
	
	// 게시글 삭제
	void remove(int bno) throws Exception;
	
	// 게시글 목록
	List<BoardVO> listReply(Criteria cri) throws Exception;
	
	// 게시글 페이징 블럭 정보
	PageMaker getPageMaker(Criteria cri) throws Exception;
}
