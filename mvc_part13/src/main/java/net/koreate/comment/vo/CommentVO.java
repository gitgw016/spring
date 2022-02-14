package net.koreate.comment.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {

	private int cno;
	private int bno;
	private String commentText;
	private String commentAuth;
	private Date regdate;
	private Date updatedate;
	private int uno;
}
