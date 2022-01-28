package net.koreate.comment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.koreate.comment.service.CommentService;
import net.koreate.comment.vo.CommentVO;
import net.koreate.mvc.common.util.Criteria;
import net.koreate.mvc.common.util.PageMaker;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Inject
	CommentService cs;
	
	@PostMapping("")
	public ResponseEntity<String> addComment(@RequestBody CommentVO vo){
		System.out.println(vo);
		ResponseEntity<String> entity = null;
		try {
			String message = cs.addComment(vo);
			entity = new ResponseEntity<>(message,HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entity;
	}

	// comments/all/bno
	@GetMapping("/all/{bno}")
	public ResponseEntity<List<CommentVO>> list(@PathVariable(name="bno") int bno){
		ResponseEntity<List<CommentVO>> entity = null;
		System.out.println("bno : "+bno);
		try {
			List<CommentVO> list = cs.commentList(bno);
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 게시글 수정 요청 처리
	@PatchMapping("/{cno}")
	public ResponseEntity<String> update(@PathVariable(name="cno") int cno, @RequestBody CommentVO vo){
		System.out.println(cno);
		System.out.println(vo);
		vo.setCno(cno);
		System.out.println(vo);
		ResponseEntity<String> entity = null;
		
		try {
			String result = cs.updateComment(vo);
			entity = new ResponseEntity<>(result,HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 삭제 요청 처리
	@DeleteMapping("/{cno}")
	public ResponseEntity<String> delete(@PathVariable(name="cno") int cno){
		ResponseEntity<String> entity = null;
		try {
			String result = cs.deleteComment(cno);
			entity = new ResponseEntity<>(result,HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 페이징 처리된 리스트 정보
	// "comments/"+bno+"/"+page;
	@GetMapping("/{bno}/{page}")
	public ResponseEntity<Map<String,Object>> listPage(@PathVariable("bno") int bno, @PathVariable int page){
		ResponseEntity<Map<String,Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		Criteria cri = new Criteria(page,5);
		try {
			// 페이징 처ㅏ리된 게시글 목록
			List<CommentVO> list = cs.commentListPage(bno, cri);
			map.put("list", list);
			// 현재 페이지의 페이징 블럭 정보
			PageMaker pm = cs.getPageMaker(cri, bno);
			map.put("pm", pm);
			entity = new ResponseEntity<>(map,HttpStatus.OK);
			System.out.println(list);
			System.out.println(map);
		} catch (Exception e) {
			entity = new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}