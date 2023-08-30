package com.btc.snow.admin.member;

import lombok.Data;

@Data
public class AdminMemberDto {

	private int a_m_no; //'관리자 번호',
	private int a_m_approval; // '관리자 승인(미승인:0, 승인:1)', default 0
	private String a_m_id; // '관리자 아이디',
	private String a_m_pw; // '관리자 비밀번호',
	private String a_m_name; // '관리자 이름' ,
	private String a_m_mail; // '관리자 이메일',
	private String a_m_phone; // '관리자 번호',
	private String a_m_position; // '관리자 직책',
	private String a_m_reg_date; // '회원 가입일',
	private String a_m_mod_date; // '회원 수정일',
}
