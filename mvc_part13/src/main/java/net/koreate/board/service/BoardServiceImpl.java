package net.koreate.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.koreate.board.dao.BoardDAO;
import net.koreate.board.vo.BoardVO;
import net.koreate.comment.dao.CommentDAO;
import net.koreate.common.utils.Criteria;
import net.koreate.common.utils.PageMaker;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	BoardDAO dao;
	
	@Inject
	CommentDAO commentDAO;

	@Transactional /* 두개 이상의 데이터베이스 연산 작업에서 하나라도 오류 발생시 나머지를 callback */
	@Override
	public void regist(BoardVO vo) throws Exception {
		// title,content,uno INSERT
		dao.regist(vo);
		// origin을 bno와 동기화
		dao.updateOrigin();
		// 업로드 된 파일 정보 저장
		List<String> files = vo.getFiles();
		if(files == null) {
			return;
		}
		for(String fullName : files) {
			dao.addAttach(fullName);
		}
	}
	
	@Override
	public List<BoardVO> listReply(Criteria cri) throws Exception {
		List<BoardVO> list = dao.listReply(cri);
		// 코멘트 갯수 추가
		for(BoardVO board : list) {
			int count = commentDAO.totalCount(board.getBno());
			board.setCommentCnts(count);
		}
		return list;
	}

	@Override
	public PageMaker getPageMaker(Criteria cri) throws Exception {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setDisplayPageNum(5);
		int totalCount = dao.listCount(cri);
		pageMaker.setTotalCount(totalCount);
		return pageMaker;
	}

	@Override
	public void updatecnt(int bno) throws Exception {
		dao.updateCnt(bno);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		BoardVO vo = dao.read(bno);
		List<String> fileList = dao.getAttach(bno);
		vo.setFiles(fileList);
		System.out.println(vo);
		return vo;
	}

	@Transactional
	@Override
	public void replyRegister(BoardVO vo) throws Exception {
		// 기존 글들의 seq 정렬 순서 값이 원본글 보다
		// 큰 값을 가진 글이 존재하면 1씩 증가한 값으로 수정
		dao.updateReply(vo);
		// 원본글 의 origin 값 , depth+1 , seq + 1한 값으로
		// 답변글 INSERT
		vo.setDepth(vo.getDepth()+1);
		vo.setSeq(vo.getSeq()+1);
		System.out.println("등록된 답변글 정보 : "+vo);
		dao.replyRegister(vo);
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		// re_tbl_board 정보 수정
		dao.modify(vo);
		
		// 기존 첨부파일 정보 삭제
		dao.deleteAttach(vo.getBno());
		
		List<String> fileList = vo.getFiles();
		if(fileList == null || fileList.isEmpty()) {
			return;
		}
		
		for(String fullName : fileList) {
			dao.replaceAttach(vo.getBno(), fullName);
		}
	}

	@Override
	public void remove(int bno) throws Exception {
		// 첨부파일 목록 삭제
		dao.deleteAttach(bno);
		// 해당 게시글의 댓글 정보 삭제
		commentDAO.deleteComment(bno);
		// 게시글 삭제
		dao.remove(bno);
		
		
	}

}
