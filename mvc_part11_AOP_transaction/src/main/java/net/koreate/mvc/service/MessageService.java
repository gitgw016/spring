package net.koreate.mvc.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.koreate.mvc.dao.MessageDAO;
import net.koreate.mvc.dao.UserDAO;
import net.koreate.mvc.vo.MessageVO;
import net.koreate.mvc.vo.UserVO;

@Service
@Slf4j
public class MessageService {
	
	@Inject
	UserDAO ud;
	
	@Inject
	MessageDAO md;
	
	// message 삽입
	@Transactional
	public void addMessage(MessageVO vo) throws Exception{
		log.debug("add Message Service ㄱㄱ");
		
		// 발신자 포인트 10 증가
		UserVO user = new UserVO();
		user.setUid(vo.getSender());
		user.setUpoint(10);
		ud.updatePoint(user);
		// message를 등록
		md.create(vo);
		log.debug("add Message Service ㅌㅌ");
	}

	public List<MessageVO> list() throws Exception {
		return md.list();
	}

	public MessageVO readMessage(UserVO user, int mno) throws Exception{
		// 게시글 정보 mno
		MessageVO message = md.readMessage(mno);
		if(message.getOpendate() != null) {
			throw new NullPointerException("이미 읽었다");
		}
		// 메세지 opentdate 수정
		md.updateMessage(mno);
		// 사용자 upoint 증가
		user.setUpoint(5);
		ud.updatePoint(user);
		return md.readMessage(mno);
	}
}
