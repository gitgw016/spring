package net.koreate.validator.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ValidationMemberVO {
	
	private int u_no;				// 회원번호
	private String u_profile;		// 프로필 사진
	private String u_id;			// 아이디(이메일)
	private String u_pw;			// 비밀번호
	private String u_name;			// 사용자 이름
	private String u_birth;			// 생년월일
	private String u_post;			// 주소 우편번호
	private String u_addr;			// 주소
	private String u_addr_detail;	// 상세 주소
	private String u_phone;			// 전화번호
	private String u_info;			// 개인정보 이용동의
	private int u_point;			// 적립 포인트
	private Date u_date;			// 회원 가입일
	private Date u_vist;			// 최종 방문일
	private String u_withdraw;		// 회원 탈퇴 여부
}
