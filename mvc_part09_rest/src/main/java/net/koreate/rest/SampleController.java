package net.koreate.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import net.koreate.vo.SampleVO;

@Controller
public class SampleController {

	@GetMapping("/testJSON")
	@ResponseBody
	public String toJSON(Model model) {
		model.addAttribute("Hello Mingu!!");
		return "JSON";
	}
	
	@GetMapping("/sendSampleVO")
	@ResponseBody
	public String sendSampleVO(Model model) {
		SampleVO vo = new SampleVO();
		vo.setName("배민구");
		vo.setAge(5000);
		model.addAttribute("sample",vo);
		return "JSON";
	}
	
	@GetMapping("/getSample")
	@ResponseBody
	public SampleVO getSample(SampleVO sample) {
		System.out.println("getSample GET ㄱㄱ");
		System.out.println(sample);
		return sample;
	}
	
	@PostMapping("/getSample")
	@ResponseBody
	public List<SampleVO> getSamplePOST(SampleVO sample){
		System.out.println("getSample post ㄱㄱ : "+sample);
		List<SampleVO> list = new ArrayList<>();
		list.add(sample);
		for(int i=0; i<10; i++) {
			SampleVO s = new SampleVO();
			s.setAge(i);
			s.setName("temp"+i);
			list.add(s);
		}
		return list;
	}
	
	//@RequestMapping(value="/getSample", method=RequestMethod.PUT)
	@PutMapping("/getSample")
	@ResponseBody
	public SampleVO getSamplePUT(HttpServletRequest request, SampleVO vo) {
		System.out.println("getSample Put ㄱㄱ : "+vo);
		System.out.println(request.getContentType());
		System.out.println(request.getMethod());
		return vo;
	}
	
	@PatchMapping("/getSample")
	@ResponseBody
	public SampleVO getSamplePatch(SampleVO vo) {
		System.out.println("getSamplePatch ㄱㄱ : "+vo);
		return vo;
	}
	
	@DeleteMapping("/getSample")
	@ResponseBody
	public SampleVO getSampleDelete(SampleVO vo) {
		System.out.println("getSampleDelete ㄱㄱ : "+vo);
		return vo;
	}
	
	@PutMapping("/testPUT")
	@ResponseBody
	public SampleVO testPUT(@RequestBody SampleVO vo) {
		System.out.println("testPUT ㄱㄱ : "+vo);
		return vo;
	}
}
