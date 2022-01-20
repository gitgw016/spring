package net.koreate.mvc.common.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchCriteria extends Criteria {
	
	// title - 제목에서 검색
	// content - 내용에서 검색
	// writer - 작성자 검색
	// tc - 제목과 내용에서 검색
	// cw - 내용과 작성자에서 검색
	// tcw - 전체(제목,내용,작성자)에서 검색

	private String searchType;		// 검색타입 - table column
	private String keyword;			// 검색할 값
	
	public SearchCriteria(int page, int perPageNum, String searchType, String keyword) {
		super(page, perPageNum);
		this.searchType = searchType;
		this.keyword = keyword;
	}
	
}
