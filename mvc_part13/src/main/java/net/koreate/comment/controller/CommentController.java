package net.koreate.comment.controller;

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

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Inject
	CommentService cs;
	
	// 댓글 목록 출력
	// int bno, int page
	// comments/bno/page
	// comments/1/1
	@GetMapping("/{bno}/{page}")
	public ResponseEntity<Map<String,Object>> listPage(@PathVariable("bno") int bno, @PathVariable("page") int page){
		ResponseEntity<Map<String,Object>> entity = null;
		try {
			Map<String, Object> map = cs.listPage(bno, page);
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 댓글 작성 요청 - JSON
	// {bno : bno, commentText : commentText, uno : uno}
	@PostMapping("/add")
	public ResponseEntity<String> addComment(@RequestBody CommentVO vo) throws Exception{
		ResponseEntity<String> entity = null;
		System.out.println(vo);
		try {
			cs.addComment(vo);
			entity = new ResponseEntity<>("GOOD",HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 댓글 수정 요청 처리
	@PatchMapping("/mod")
	// {commentText : text , cno : cno}
	public ResponseEntity<String> update(@RequestBody CommentVO vo) throws Exception{
		ResponseEntity<String> entity = null;
		System.out.println("CommentVO 수정이다 : "+vo);
		try {
			cs.modifyComment(vo);
			entity = new ResponseEntity<>("GOOD",HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@DeleteMapping(value="/remove/{cno}", produces="text/plain;charset=utf-8")
	public ResponseEntity<String> delete(@PathVariable("cno") int cno) throws Exception{
		ResponseEntity<String> entity = null;
		try {
			cs.removeComment(cno);
			entity = new ResponseEntity<>("ㅂㅂ ㅠㅠ",HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
}
