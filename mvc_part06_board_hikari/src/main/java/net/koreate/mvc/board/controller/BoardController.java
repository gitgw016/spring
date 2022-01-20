package net.koreate.mvc.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.koreate.mvc.board.vo.BoardVO;
import net.koreate.mvc.common.service.BoardService;
import net.koreate.mvc.common.util.Criteria;
import net.koreate.mvc.common.util.PageMaker;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Inject
	BoardService<BoardVO,PageMaker,Criteria> service;
	// /board/register method=get
	@GetMapping("/register")
	public void register() throws Exception{
		System.out.println("/board/register GET 왔수다");
	}
	
	// /board/register method=post	
	@PostMapping("/register")
	public String register(BoardVO boardVO/* , Model model */, RedirectAttributes rttr) throws Exception{
		System.out.println(boardVO);
		String result = service.register(boardVO);
		System.out.println(result);
		rttr.addFlashAttribute("result",result);
		/*
		 * Criteria cri = new Criteria();
		 * model.addAttribute("list",service.list(cri));
		 * model.addAttribute("pm",service.getPageMaker(cri));
		 * return "/board/listPage";
		 */
		return "redirect:/board/listPage";
	}
	// GET /board/listPage
	@GetMapping("/listPage")
	public void listPage(Criteria cri, Model model) throws Exception{
		System.out.println(cri);
		model.addAttribute("list",service.list(cri));
		model.addAttribute("pm",service.getPageMaker(cri));
	}
	
	// /board/readPage method=GET
	@GetMapping("/readPage")
	public String readPage(@RequestParam(name="bno") int bno, RedirectAttributes rttr, Criteria cri) throws Exception{
		System.out.println(cri);
		// viewcnt 조회수 증가
		service.updateViewCnt(bno);
		rttr.addAttribute("bno",bno);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		return "redirect:/board/readDetail";
	}
	
	// /board/readDetail method=GET
	@GetMapping("readDetail")
	public ModelAndView readDetail(ModelAndView mav, int bno, @ModelAttribute("cri") Criteria cri) throws Exception{
		System.out.println("readDetail : " + cri);
		BoardVO board = service.read(bno);
		mav.addObject("board",board);
		mav.addObject(board); // mav.addObject("boardVO",board);
		//mav.addObject("cri", cri);
		mav.setViewName("board/readPage");
		return mav;
	}
	
	// /board/modifyPage method=get
	@GetMapping("modifyPage")
	public void modifyPage(int bno,Model model,@ModelAttribute("cri")Criteria cri) throws Exception{
		model.addAttribute(service.read(bno)); // boardVO
	}
	
	// /board/modifyPage method=post
	@PostMapping("modifyPage")
	public String modifyPage(BoardVO board, RedirectAttributes rttr, Criteria cri) throws Exception{
		System.out.println(board);
		String message = service.modify(board);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("result",message);
		return "redirect:/board/listPage";
	}
	
	// /board/removePage method=post
	@PostMapping("removePage")
	public String remove(int bno, Criteria cri, RedirectAttributes rttr) throws Exception{
		String result = service.remove(bno);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("result",result);
		return "redirect:/board/listPage";
	}
}
