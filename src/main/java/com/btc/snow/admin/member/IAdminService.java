package com.btc.snow.admin.member;

import java.util.List;

public interface IAdminService {

    int createAccountConfirm(AdminMemberDto adminMemberDto);

    AdminMemberDto loginConfirm(AdminMemberDto adminMemberDto);

    int modifyAccountConfirm(AdminMemberDto adminMemberDto);

    AdminMemberDto getLoginedAdminMember(int a_m_no);

    List<AdminMemberDto> listupAdmin();

    int setAdminApproval(int a_m_no);

    int findPasswordConfirm(AdminMemberDto adminMemberDto);

    String createNewPassword();

    void sendNewPasswordByMail(String toMailAddr, String newPassword);

    int SignOutConfirm(int a_m_no);
}
