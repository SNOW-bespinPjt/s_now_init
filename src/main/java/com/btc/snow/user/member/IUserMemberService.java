package com.btc.snow.user.member;

import jakarta.mail.MessagingException;

import java.util.List;

public interface IUserMemberService {

    /*
     * CREATE ACCOUNT CONFIRM
     */
    public int createAccountConfirm(UserMemberDto memberDto);

    /*
     USER LOGIN CONFIRM
    */
    public UserMemberDto userLoginConfirm(UserMemberDto userMemberDto);

    /*
     USER MODIFY CONFIRM
    */
    public UserMemberDto userModifyConfirm(UserMemberDto userMemberDto);

    /*
     USER DELETE CONFIRM
    */
    public int userDeleteConfirm(int no);

    /*
     FIND PASSWORD CONFIRM
     */
    public int findPasswordConfirm(UserMemberDto userMemberDto) throws MessagingException;

    int uploadUserImg(UserMemberDto userMemberDto);

    // BTC 코인 순위
    List<UserMemberDto> coinRanking();
}
