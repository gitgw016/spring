package net.koreate.service;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

	public TestServiceImpl() {
		System.out.println("TestServiceImpl 왔다");
	}
	
	@Override
	public void testService(String message) {
		System.out.println(message+" test service");
	}

}
