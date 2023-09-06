package com.btc.snow.admin.member;

import lombok.Data;

@Data
public class AdminMemberDto {

	private int no; //'관리자 번호',
	private int is_approval; // '관리자 승인(미승인:0, 승인:1)', default 0
	private String id; // '관리자 아이디',
	private String pw; // '관리자 비밀번호',
	private String name; // '관리자 이름' ,
	private String mail; // '관리자 이메일',
	private String phone; // '관리자 번호',
	private String position; // '관리자 직책',
	private String reg_date; // '회원 가입일',
	private String mod_date; // '회원 수정일',
}
