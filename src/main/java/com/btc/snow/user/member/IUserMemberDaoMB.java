package com.btc.snow.user.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMemberDaoMB {

    /*
     * CHECK DUPLICATE ID
     */
	public boolean isUser(String id);

    /*
     * INSERT NEW ACCOUNT
     */
	public int insertUserMember(UserMemberDto memberDto);

    /*
     * USER LOGIN CONFIRM
     */
    public UserMemberDto selectUserForLogin(UserMemberDto userMemberDto);

    /*
     * USER MODIFY CONFIRM
     */
    public int updateAccount(UserMemberDto userMemberDto);

    /*
     * GET USER Latest INFO
     */
    public UserMemberDto getLatestAccountInfo(UserMemberDto userMemberDto);

    /*
     * USER DELETE CONFIRM
     */
    public int deleteUser(int no);

    /*
     * UPDATE USER PW
     */
    public int updateUserPW(UserMemberDto userMemberDto);

}
