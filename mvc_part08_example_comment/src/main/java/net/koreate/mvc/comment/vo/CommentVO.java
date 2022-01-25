package net.koreate.mvc.comment.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentVO {
	private int cno;				// 댓글 번호
	private int bno;				// 등록된 게시글 번호
	private String commentText;		// 댓글 내용
	private String commentAuth;		// 댓글 작성자
	private String commentDelete; 	// 댓글 삭제 여부
	private Date regdate;			// 댓글 등록 시간
	private Date updatedate;		// 댓글 마지막 수정 시간
}
