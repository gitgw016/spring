package net.koreate.comment.service;

import java.util.List;

import net.koreate.comment.vo.CommentVO;
import net.koreate.mvc.common.util.Criteria;
import net.koreate.mvc.common.util.PageMaker;

public interface CommentService {
	// 게시물 번호를 전달받아 게시물 번호와 일치하는 전체 댓글 리스트
	List<CommentVO> commentList(int bno) throws Exception;
	
	// 댓글 삽입
	String addComment(CommentVO vo) throws Exception;
	
	// 댓글 수정
	String updateComment(CommentVO vo) throws Exception;
	
	// 댓글 삭제
	String deleteComment(int cno) throws Exception;
	
	// 페이징 처리된 댓글 리스트
	List<CommentVO> commentListPage(int bno, Criteria cri) throws Exception;
	
	// 페이징 블럭 정보
	PageMaker getPageMaker(Criteria cri, int bno) throws Exception;
}
