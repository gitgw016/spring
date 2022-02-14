package net.koreate.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.koreate.comment.dao.CommentDAO;
import net.koreate.comment.vo.CommentVO;
import net.koreate.common.utils.Criteria;
import net.koreate.common.utils.PageMaker;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Inject
	CommentDAO dao;

	@Override
	public void addComment(CommentVO vo) throws Exception {
		dao.addComment(vo);
	}

	@Override
	public Map<String, Object> listPage(int bno, int page) throws Exception {
		Criteria cri = new Criteria();
		cri.setPage(page);
		cri.setPerPageNum(5);
		List<CommentVO> list = dao.listPage(bno, cri);
		// 해당 게시글에 작성된 전체 댓글 수
		int totalCount = dao.totalCount(bno);
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setDisplayPageNum(5);
		pm.setTotalCount(totalCount);
		
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pm",pm);
		return map;
	}

	@Override
	public void modifyComment(CommentVO vo) throws Exception {
		dao.modifyComment(vo);
	}

	@Override
	public void removeComment(int cno) throws Exception {
		dao.removeComment(cno);
	}

}
