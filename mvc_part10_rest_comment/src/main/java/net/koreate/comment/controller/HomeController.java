package net.koreate.comment.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.koreate.comment.vo.CommentVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping("test")
	public void test() {}
	
	@PostMapping("test")
	@ResponseBody
	public CommentVO test(String test, @RequestBody CommentVO comment)throws Exception{
		System.out.println(test);
		System.out.println(comment);
		if(test != null) {
			ObjectMapper mapper = new ObjectMapper();
			CommentVO vo = mapper.readValue(test, CommentVO.class);
			System.out.println(vo);
			String result = mapper.writeValueAsString(vo);
			System.out.println(result);
		}
		//CommentVO vo = new CommentVO();
		return comment;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
