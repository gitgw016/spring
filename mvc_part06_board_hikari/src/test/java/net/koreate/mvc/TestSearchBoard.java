package net.koreate.mvc;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
import net.koreate.mvc.sboard.dao.SearchBoardDAO;
import net.koreate.mvc.sboard.vo.SearchBoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/context/root-context.xml"})
@Log4j
public class TestSearchBoard {
	
	@Inject
	SearchBoardDAO dao;

	@Test
	public void test() throws Exception{
		SearchBoardVO vo = new SearchBoardVO();
		vo.setTitle("배밍구 제목");
		vo.setContent("밍구같은 내용");
		vo.setWriter("배민구");
		int result = dao.create(vo);
		log.info("result : "+result);
		log.warn("result : "+result);
		log.error("result : "+result);
		log.debug("result : "+result);
		
	}
}
