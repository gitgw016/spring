package net.koreate.mvc.board.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.koreate.mvc.board.dao.BoardDAO;
import net.koreate.mvc.board.vo.BoardVO;
import net.koreate.mvc.common.service.BoardService;
import net.koreate.mvc.common.util.Criteria;
import net.koreate.mvc.common.util.PageMaker;

@Service
public class BoardServiceImpl implements BoardService<BoardVO, PageMaker, Criteria> {
	
	@Inject
	BoardDAO dao;

	@Override
	public String register(BoardVO model) throws Exception {
		int result = dao.create(replace(model));
		System.out.println(result);
		return getMessage(result);
	}
	
	private String getMessage(int result) {
		return (result > 0) ? "성공 ^^7" : "실패 ㅎㅎㅗ";
	}

	public String replaceScript(String text) {
		// 장난질 방지용
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		return text;
	}
	
	public BoardVO replace(BoardVO model) {
		String title = model.getTitle();
		String content = model.getContent();
		String writer = model.getWriter();
		model.setTitle(replaceScript(title));
		model.setContent(replaceScript(content));
		model.setWriter(replaceScript(writer));
		return model;
	}
	
	@Override
	public String modify(BoardVO model) throws Exception {
		int result = dao.update(replace(model));
		return getMessage(result);
	}

	@Override
	public String remove(int bno) throws Exception {
		int result = dao.delete(bno);
		return getMessage(result);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return dao.read(bno);
	}

	@Override
	public void updateViewCnt(int bno) throws Exception {
		dao.updateCnt(bno);
	}

	@Override
	public List<BoardVO> list(Criteria cri) throws Exception {
		List<BoardVO> list = dao.list(cri);
		System.out.println(list);
		return list;
	}

	@Override
	public PageMaker getPageMaker(Criteria cri) throws Exception {
		int totalCount = dao.listCount();
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalCount);
		System.out.println(pageMaker);
		return pageMaker;
	}

	@Override
	public Map<String, Object> getListModel(Criteria cri) throws Exception {
		return null;
	}

}
