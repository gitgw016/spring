package net.koreate.user.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.user.vo.BanIPVO;

public interface BanIPDAO {

	// ip로 등록된 ban_ip 검색
	@Select("SELECT * FROM ban_ip WHERE ip = #{ip}")
	BanIPVO getBanIPVO(String ip) throws Exception;
	// ban_ip 등록
	@Insert("INSERT INTO ban_ip(ip) VALUES(#{ip})")
	void signInFail(String ip) throws Exception;
	// 반복 실패 카운트 등록
	@Update("UPDATE ban_ip SET cnt = cnt + 1 , bandate = now() WHERE ip = #{ip}")
	void updateBanIPCnt(String ip) throws Exception;
	// 로그인 성공시 ban_ip 삭제
	@Delete("DELETE FROM ban_ip WHERE ip = #{ip}")
	void removeBanIp(String ip) throws Exception;
}
