package com.btc.snow.user.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMemberDaoMB {

	// CHECK DUPLICATE ID
	public boolean isUser(String m_id);

	// INSERT NEW ACCOUNT
	public int insertUserMember(UserMemberDto memberDto);

	// USER LOGIN CONFIRM
	public UserMemberDto selectUserForLogin(UserMemberDto userMemberDto);

	// USER MODIFY CONFIRM
	public int updateAccount(UserMemberDto userMemberDto);

	// GET USER INFO
	public UserMemberDto getLatestAccountInfo(UserMemberDto userMemberDto);

	// USER DELETE CONFIRM
	public int deleteUser(int u_m_no);


	
}
