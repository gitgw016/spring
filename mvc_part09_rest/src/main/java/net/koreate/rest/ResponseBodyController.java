package net.koreate.rest;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import net.koreate.vo.SampleVO;

@RestController
public class ResponseBodyController {

	@RequestMapping("/hello")
	public String hello() {
		return "This is message";
	}
	
	@PostMapping(value="xmlTest", consumes="application/x-www-form-urlencoded")
	public ResponseEntity<String> xmlTest(SampleVO vo) throws Exception{
		System.out.println(vo);
		HttpHeaders header = new HttpHeaders();
		// response.setContextType("text/xml;charset=utf-8");
		header.setContentType(new MediaType("text","xml",Charset.forName("utf-8")));
		
		// header.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML.toString());
		System.out.println(MediaType.TEXT_XML);
		System.out.println(MediaType.APPLICATION_JSON_VALUE);
		System.out.println(Charset.forName("utf-8"));
		ResponseEntity<String> entity = null;
		String xml = "<sample><name>"+vo.getName()+"</name><age>"+vo.getAge()+"</age></sample>";
		System.out.println(xml);
		entity = new ResponseEntity<String>(xml,header,HttpStatus.OK); // BAD_REQUEST NOT_FOUND ...
		return entity;
	}
	
	@PostMapping(value="xmlTest", consumes="application/json", produces = "text/xml;charset=utf-8")
	public String xmlTestJSON(@RequestBody SampleVO vo) throws Exception{
		ObjectMapper mapper = new XmlMapper();
		String xml = mapper.writeValueAsString(vo);
		System.out.println(xml);
		SampleVO re = mapper.readValue(xml, SampleVO.class);
		System.out.println(re);
		return xml;
	}
}
