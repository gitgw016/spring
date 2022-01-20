package net.koreate.lombok;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class LombokTest {

	@Test
	public void lombok() {
		UserVO user1 = new UserVO();
		user1.setUid("mingu");
		user1.setUpw("bae");
		System.out.println(user1.toString());
		
		UserVO user2 = new UserVO("minjae","bae");
		System.out.println(user1.equals(user2));
		UserVO user3 = new UserVO("mingu","bae");
		System.out.println(user1.equals(user3));
		
		UserVO user4 = UserVO.builder().uid("mangyu").upw("bae").uname("배만규").regdate(new Date()).friendList(new ArrayList<>()).build();
		List<String> list = new ArrayList<>();
		list.add("배민구");
		list.add("오정만");
		list.add("손성열");
		list.add("호오홍");
		user4.setFriendList(list);
		user4.getFriendList().add("성식스");
		System.out.println(user4);
		
		UserVO user5 = UserVO.builder().uno(1).uid("jungman").upw("bae").uname("배정만").regdate(new Date()).list("배민구").list("배민재").list("손성열").build();
		System.out.println(user5);
		
		UserVO user6 = new UserVO(null,null);
		System.out.println(user6);
	}
}
