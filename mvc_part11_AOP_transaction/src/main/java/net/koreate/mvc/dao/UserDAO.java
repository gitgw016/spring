package net.koreate.mvc.dao;

import org.apache.ibatis.annotations.Update;

import net.koreate.mvc.vo.UserVO;

public interface UserDAO {
	
	@Update("UPDATE tbl_user SET upoint = upoint + #{upoint} WHERE uid = #{uid}")
	void updatePoint(UserVO vo) throws Exception;
}
