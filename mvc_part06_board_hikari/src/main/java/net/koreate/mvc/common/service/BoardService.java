package net.koreate.mvc.common.service;

import java.util.List;
import java.util.Map;

import net.koreate.mvc.board.vo.BoardVO;
import net.koreate.mvc.sboard.vo.SearchBoardVO;
// <Model, PageMaker, Criteria>
public interface BoardService<M,P,C> {

	// 게시글 등록
	String register(M model) throws Exception;
	
	// 게시글 수정
	String modify(M model) throws Exception;
	
	// 게시글 삭제
	String remove(int bno) throws Exception;
	
	// 한개의 게시글 정보
	M read(int bno) throws Exception;
	
	// 게시글 조회수 증가
	void updateViewCnt(int bno) throws Exception;
	
	// 검색 게시물 리스트(페이징)
	List<M> list(C cri) throws Exception;
	
	// PageMaker 정보
	P getPageMaker(C cri) throws Exception;
	
	// 뷰 페이지에 전달된 Model 정보를 Map으로 전달
	Map<String, Object> getListModel(C cri) throws Exception;
}
