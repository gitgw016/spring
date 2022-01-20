package net.koreate.mvc.sboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.koreate.mvc.common.service.BoardService;
import net.koreate.mvc.common.util.SearchCriteria;
import net.koreate.mvc.common.util.SearchPageMaker;
import net.koreate.mvc.sboard.dao.SearchBoardDAO;
import net.koreate.mvc.sboard.vo.SearchBoardVO;

@Service
public class SearchBoardServiceImpl implements BoardService<SearchBoardVO, SearchPageMaker, SearchCriteria> {
	
	@Inject
	SearchBoardDAO dao;

	@Override
	public String register(SearchBoardVO model) throws Exception {
		int result = dao.create(model);
		return result > 0 ? "성공^^" : "실패 ㅠ";
	}

	@Override
	public String modify(SearchBoardVO model) throws Exception {
		int result = dao.update(model);
		return result > 0 ? "성공 *^^*" : "실패 ㅠㅠ";
	}

	@Override
	public String remove(int bno) throws Exception {
		return dao.remove(bno) > 0 ? "성공 ㅎㅎ" : "실패라니 ㅠ";
	}

	@Override
	public SearchBoardVO read(int bno) throws Exception {
		return dao.read(bno);
	}

	@Override
	public void updateViewCnt(int bno) throws Exception {
		dao.updateViewCnt(bno);
	}

	@Override
	public List<SearchBoardVO> list(SearchCriteria cri) throws Exception {
		List<SearchBoardVO> list = dao.searchList(cri);
		return list;
	}

	@Override
	public SearchPageMaker getPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = dao.searchListCount(cri);
		SearchPageMaker pm = new SearchPageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public Map<String, Object> getListModel(SearchCriteria cri) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("list", list(cri));
		map.put("pm", getPageMaker(cri));
		return map;
	}

}
