package net.koreate.validator;


import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/spring/root-context.xml"})
public class SendMailTest {

	@Autowired
	@Qualifier(value="mailSender")
	JavaMailSender mailSender;
	
	@Test
	public void sendTest() throws Exception{
		MimeMessage message = mailSender.createMimeMessage();
		// multipart 메세지를 사용하겠다는 의미
		// MimeMessageHelper messageHeler = new MimeMessageHelper(message,true,"utf-8");
		
		// 일반 텍스트 메세지
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,"utf-8");
		messageHelper.setFrom("gwxkas0106@gmail.com");
		messageHelper.setTo("gwgwii@naver.com");
		messageHelper.setSubject("이야");
		// true - html을 사용하겠다
		messageHelper.setText("<h1>왔다</h1>",true);
		mailSender.send(message);
		System.out.println("보냈다");
	}
}
