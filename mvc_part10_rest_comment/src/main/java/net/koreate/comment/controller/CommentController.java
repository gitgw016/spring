package net.koreate.comment.controller;

import java.util.List;

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
}
