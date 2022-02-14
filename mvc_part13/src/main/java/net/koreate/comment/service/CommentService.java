package net.koreate.comment.service;

import java.util.Map;

import net.koreate.comment.vo.CommentVO;

public interface CommentService {

	// 댓글 삽입
	void addComment(CommentVO vo) throws Exception;
	
	// 댓글 리스트 및 페이징 정보
	// List<CommentVO>, PageMaker
	Map<String,Object> listPage(int bno, int page) throws Exception;
	
	// 댓글 수정
	void modifyComment(CommentVO vo) throws Exception;
	
	// 댓글 삭제
	void removeComment(int cno) throws Exception;
}
