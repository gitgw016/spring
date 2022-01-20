package net.koreate.mvc;

import java.util.Set;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;

import net.koreate.mvc.sboard.controller.SearchBoardController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/context/root-context.xml", "classpath:/context/servlet-context.xml"})
@WebAppConfiguration
public class MockMvcTest {

	@Inject
	private WebApplicationContext wc;
	// MockMvc - 모조 객체
	// 웹 애플리케이션을 애플리케이션 서버에 배포하지 않고 스프링 MVC의 동작을 재현할 수 있는 클래스
	private MockMvc mvc;
	
	@Inject
	SearchBoardController board;
	
	@Before // Test method가 실행되기 전에 호출되는 메소드
	public void setUp() throws Exception{
		// mvc = MockMvcBuilders.webAppContextSetup(wc).build();
		mvc = MockMvcBuilders.standaloneSetup(board).build();
		System.out.println("-------------------- test 시작 ----------------------");
	}
	
	//@Test
	public void testInsert() throws Exception{
		System.out.println("testInsert ㄱㄱ");
		// /sboard/register post
		ResultActions ra = mvc.perform(MockMvcRequestBuilders.post("/sboard/register").param("title", "배밍구같은").param("content", "민구는 안선다").param("writer", "배민구"));
		MvcResult result = ra.andReturn();
		FlashMap flash = result.getFlashMap();
		System.out.println("flash : "+flash);
		ModelAndView mav = result.getModelAndView();
		System.out.println(mav);
	}
	
	@Test
	public void testRead() throws Exception{
		System.out.println("testRead ㄱㄱ");
		// get /sboard/listPage?page=1&perPageNum=15
		ModelAndView mav = mvc.perform(MockMvcRequestBuilders.get("/sboard/listPage").param("page", "1").param("perPageNum", "15")).andReturn().getModelAndView();
		System.out.println("model : "+mav.getModel());
		System.out.println("view : "+mav.getViewName());
		System.out.println("--------------- search ---------------");
		mav = mvc.perform(MockMvcRequestBuilders.get("/sboard/listPage").param("page", "1").param("perPageNum", "10").param("searchType", "title").param("keyword", "밍구")
						).andReturn().getModelAndView();
		Set<String> keySet = mav.getModelMap().keySet();
		for(String key : keySet) {
			System.out.printf("key : %s , value : %s %n", key,mav.getModelMap().get(key));
		}
	}
	
	@Test
	public void testReadPage() throws Exception{
		System.out.println("testReadPage ㄱㄱ");
		ModelAndView mav = mvc.perform(MockMvcRequestBuilders
				/* .get("/sboard/readPage") */
				.get("/sboard/readDetail").param("bno", "1600").param("page", "1").param("perPageNum", "10").param("searchType", "writer").param("keyword", "배민구")).andReturn().getModelAndView();
		System.out.println(mav.getModel());
		System.out.println(mav.getViewName());
	}
	
	@Test
	public void testModify() throws Exception{
		System.out.println("testModify ㄱㄱ");
		ResultActions ra = mvc.perform(MockMvcRequestBuilders.post("/sboard/modifyPage").param("bno", "1600").param("title", "밍구밍구").param("content", "밍구왔쪄").param("writer", "배밍쿠").param("page", "1").param("perPageNum", "20").param("searchType", "writer").param("keyword", "배민구"));
		MvcResult result = ra.andReturn();
		ModelAndView mav = result.getModelAndView();
		FlashMap flash = result.getFlashMap();
		System.out.println("flash : "+flash.get("result"));
		System.out.println(mav.getModel());
		System.out.println(mav.getViewName());
	}
	
	@Test
	public void testRemove() throws Exception{
		System.out.println("testRemove ㄱㄱ");
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/sboard/removePage").param("bno", "1598")).andReturn();
		System.out.println(result.getFlashMap());
		System.out.println(result.getModelAndView());
	}
	
	@After
	public void after() {
		System.out.println("-------------------- test 끝 ------------------------");
	}
	
}
