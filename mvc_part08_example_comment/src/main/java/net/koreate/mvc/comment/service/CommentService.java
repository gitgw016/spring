package net.koreate.mvc.comment.service;

import java.util.List;

import net.koreate.mvc.comment.vo.CommentVO;
import net.koreate.mvc.common.util.Criteria;
import net.koreate.mvc.common.util.PageMaker;

public interface CommentService {
	
	// 댓글 삽입
	String addComment(CommentVO vo) throws Exception;
	
	// 댓글 삭제
	String delete(int cno)throws Exception;

	// 게시물 번호가 일치하는 댓글 페이지 블럭 정보
	PageMaker getPageMaker(int page, int bno)throws Exception;
	
	// 페이징 처리된 해당 번호의 게시물 댓글 리스트
	List<CommentVO> commentListPage(int bno, Criteria cri) throws Exception;
}












