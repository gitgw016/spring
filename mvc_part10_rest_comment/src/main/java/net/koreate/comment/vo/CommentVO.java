package net.koreate.comment.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
// lombok
@Getter
@Setter
@ToString
public class CommentVO {
	
	private int cno;				// 댓글 번호
	private int bno;				// 댓글이 저장된 게시글 번호
	private String commentAuth;		// 댓글 작성자
	private String commentText;		// 댓글 내용
	private String commentDelete;	// 댓글 삭제 여부
	private Date regdate;			// 등록 시간
	private Date updatedate;		// 수정시간
}
