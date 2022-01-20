package net.koreate.mvc.sboard.vo;

import java.util.Date;

import lombok.Data;
@Data
public class SearchBoardVO {

	private int bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int viewcnt;
}
