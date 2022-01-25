package net.koreate.mvc.comment.query;

public interface CommentQueryProvider {
	// public static final
	// 댓글 삽입
	String INSERT = "INSERT INTO tbl_comment(bno,commentText,commentAuth) VALUES(#{bno},#{commentText},#{commentAuth})";
	
	// 댓글 삭제 commentDelete = 'Y'
	String DELETE = "UPDATE tbl_comment SET commentDelete = 'Y' WHERE cno = #{cno}";
	
	// 전체 게시글 번호가 일치하는 댓글 수
	String COUNT = "SELECT count(*) FROM tbl_comment WHERE bno = #{bno}";
	
	// 댓글 리스트 목록
	String LIST_PAGE = "SELECT * FROM tbl_comment WHERE bno = #{bno} ORDER BY cno DESC limit #{cri.startRow} , #{cri.perPageNum}";
}
