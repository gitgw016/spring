package net.koreate.mvc.sboard.dao;

import java.util.List;

import net.koreate.mvc.common.util.SearchCriteria;
import net.koreate.mvc.sboard.vo.SearchBoardVO;

public interface SearchBoardDAO {

	// 게시글 삽입
	int create(SearchBoardVO vo) throws Exception;
	
	// 게시글 번호가 일치하는 게시글 정보
	SearchBoardVO read(int bno) throws Exception;
	
	// 게시글 수정
	int update(SearchBoardVO vo) throws Exception;
	
	// 게시글 삭제
	int remove(int bno) throws Exception;
	
	// 조회수 증가
	void updateViewCnt(int bno) throws Exception;
	
	// 페이징 처리 - 검색된 전체 게시물 개수
	int searchListCount(SearchCriteria cri) throws Exception;
	
	// 페이징 처리 - 검색된 게시물 목록
	List<SearchBoardVO> searchList(SearchCriteria cri) throws Exception;
	
}
