package net.koreate.mvc.sboard.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.koreate.mvc.common.service.BoardService;
import net.koreate.mvc.common.util.SearchCriteria;
import net.koreate.mvc.common.util.SearchPageMaker;
import net.koreate.mvc.sboard.vo.SearchBoardVO;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {

	@Inject
	BoardService<SearchBoardVO, SearchPageMaker, SearchCriteria> service;
	
	// /sboard/register method = get
	@GetMapping("register")
	public String register() throws Exception{
		return "sboard/register";
	}
	
	// /sboard/register method = post
	@PostMapping("register")
	public String register(SearchBoardVO vo,RedirectAttributes rttr) throws Exception{
		String result = service.register(vo);
		rttr.addFlashAttribute("result",result);
		return "redirect:/sboard/listPage";
	}
	
	// /sboard/listPage method=get
	@GetMapping("listPage")
	public ModelAndView listPage(SearchCriteria cri,ModelAndView mav) throws Exception{
		System.out.println("listPage : "+cri);
		mav.addAllObjects(service.getListModel(cri));
		mav.setViewName("sboard/listPage");
		return mav;
	}
	// /sboard/readPage method=get
	@GetMapping("readPage")
	public ModelAndView readPage(int bno, ModelAndView mav,RedirectAttributes rttr, SearchCriteria cri) throws Exception{
		// 조회수 증가
		service.updateViewCnt(bno);
		rttr.addAttribute("bno",bno);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		mav.setViewName("redirect:/sboard/readDetail");
		return mav;
	}
	
	// /sboard/readDetail method=get
	@GetMapping("readDetail")
	public ModelAndView readDetail(int bno, ModelAndView mav, @ModelAttribute("cri") SearchCriteria cri) throws Exception{
		SearchBoardVO vo = service.read(bno);
		mav.addObject("board",vo);
		mav.setViewName("sboard/readPage");
		return mav;
	}
	
	// /sboard/modifyPage method=get - 수정화면 출력
	@GetMapping("modifyPage")
	public ModelAndView modify(int bno, ModelAndView mav, @ModelAttribute("cri") SearchCriteria cri) throws Exception{
		mav.addObject(service.read(bno));
		mav.setViewName("sboard/modifyPage");
		return mav;
	}
	
	// /sboard/modifyPage method=post - 수정 요청 처리 -> listPㅁge
	@PostMapping("modifyPage")
	public ModelAndView modifyPage(SearchBoardVO board, SearchCriteria cri, ModelAndView mav, RedirectAttributes rttr) throws Exception{
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		String msg = service.modify(board);
		rttr.addFlashAttribute("result",msg);
		mav.setViewName("redirect:/sboard/listPage");
		return mav;
	}
	
	// /sboard/removePage method=post 삭제요청 처리 -> listPage
	@PostMapping("removePage")
	public String removePage(int bno, RedirectAttributes rttr, SearchCriteria cri) throws Exception{
		String msg = service.remove(bno);
		rttr.addFlashAttribute("result",msg);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		return "redirect:/sboard/listPage";
	}
}

