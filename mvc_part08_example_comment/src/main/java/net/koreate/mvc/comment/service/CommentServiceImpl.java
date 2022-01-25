package net.koreate.mvc.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.koreate.mvc.comment.dao.CommentDAO;
import net.koreate.mvc.comment.vo.CommentVO;
import net.koreate.mvc.common.util.Criteria;
import net.koreate.mvc.common.util.PageMaker;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Inject
	CommentDAO dao;

	@Override
	public String addComment(CommentVO vo) throws Exception {
		int result = dao.addComment(vo);
		return result > 0 ? "SUCCESS" : "FAIL";
	}

	@Override
	public String delete(int cno) throws Exception {
		int result = dao.delete(cno);
		return result > 0 ? "SUCCESS" : "FAIL";
	}

	@Override
	public PageMaker getPageMaker(int page, int bno) throws Exception {
		int totalCount = dao.listCount(bno);
		Criteria cri = new Criteria(page, 5);
		PageMaker pm = new PageMaker();
		pm.setTotalCount(totalCount);
		pm.setCri(cri);
		return pm;
	}

	@Override
	public List<CommentVO> commentListPage(int bno, Criteria cri) throws Exception {
		cri.setPerPageNum(5);
		/*
		Map<String,Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("cri", cri);
		List<CommentVO> list = dao.listPage(map);
		*/
		List<CommentVO> list = dao.listPage(bno,cri);
		return list;
	}

}
