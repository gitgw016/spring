package net.koreate.mvc.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.koreate.mvc.service.MessageService;
import net.koreate.mvc.vo.MessageVO;
import net.koreate.mvc.vo.UserVO;

@RestController
@RequestMapping("/messages")
public class MessageController {
	
	@Inject
	MessageService ms;

	@PostMapping("/add")
	public ResponseEntity<String> addMessage(MessageVO vo){
		ResponseEntity<String> entity = null;
		System.out.println(vo);
		try {
			ms.addMessage(vo);
			entity = new ResponseEntity<>("성공 ^^",HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			entity = new ResponseEntity<>("실패 ㅠ",header,HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@GetMapping("/list")
	public List<MessageVO> readList() throws Exception{
		return ms.list();
	}
	
	@PatchMapping("/read/{mno}")
	public ResponseEntity<Object> readMessage(@PathVariable("mno") int mno, @RequestBody UserVO user){
		ResponseEntity<Object> entity = null;
		
		try {
			MessageVO message = ms.readMessage(user,mno);
			entity = new ResponseEntity<>(message,HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			entity = new ResponseEntity<>(e.getMessage(),header,HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
