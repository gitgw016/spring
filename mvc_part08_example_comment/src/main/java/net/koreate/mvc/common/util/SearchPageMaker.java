package net.koreate.mvc.common.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class SearchPageMaker extends PageMaker {

	@Override
	public String query(int page) {
		UriComponents uri 
		= UriComponentsBuilder.newInstance()
		.queryParam("page", page)
		.queryParam("perPageNum", cri.getPerPageNum())
		.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
		.queryParam("keyword",((SearchCriteria)cri).getKeyword())
		.build();
		return uri.toUriString();
	}
	
}






