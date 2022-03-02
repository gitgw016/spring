package net.koreate.sec.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ValidationMemberVO {

	private int u_no;				// 회원번호
	private String u_profile;		// 프로필 이미지 경로
	private String u_id;			// 아이디-이메일
	private String u_pw;			// 비밀번호
	private String u_phone;			// 전화번호
	private String u_name;			// 이름
	private String u_birth;			// 생년월일
	private String u_post;			// 우편번호
	private String u_addr;			// 주소
	private String u_addr_detail;	// 상세주소
	private int u_point;			// 포인트
	private String u_info;			// 개인정보 이용동의
	private Date u_date;			// 회원가입일
	private Date u_visit_date;		// 최종방문일
	private String u_withdraw;		// 회원탈퇴 여부
	
	private List<AuthVO> authList;	// 권한 정보
}
