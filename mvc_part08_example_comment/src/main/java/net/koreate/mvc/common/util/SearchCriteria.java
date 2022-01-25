package net.koreate.mvc.common.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchCriteria extends Criteria {
	// 검색타입 - table Column
	/**
	 * title - 제목에서 검색
	 * content - 내용에서 검색
	 * writer - 작성자 검색
	 * tc - 제목과 내용에서 검색
	 * cw - 내용과 작성자에서 검색
	 * tcw - 전체(제목,내용,작성자) 검색 
	 */
	private String searchType;
	// 검색할 값
	private String keyword;
	
	public SearchCriteria(int page, int perPageNum, String searchType, String keyword) {
		super(page, perPageNum);
		this.searchType = searchType;
		this.keyword = keyword;
	}
}
