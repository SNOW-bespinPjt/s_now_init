package com.btc.snow.admin.member;

import java.util.List;

public interface IAdminService {

    // 회원가입
    int createAccountConfirm(AdminMemberDto adminMemberDto);

    // 로그인
    AdminMemberDto loginConfirm(AdminMemberDto adminMemberDto);

    // 회원 정보수정
    int modifyAccountConfirm(AdminMemberDto adminMemberDto);

    // 현재 관리자 확인
    AdminMemberDto getLoginedAdminMember(int no);

    // 관리자 리스트
    List<AdminMemberDto> listupAdmin();

    // 관리자 권한 승인
    int setAdminApproval(int no);

    // 아이디 찾기
    int findIdConfirm(AdminMemberDto adminMemberDto);

    // 찾은 아이디 메일 발송
    void sendIdByMail(AdminMemberDto adminMemberDto);

    // 비밀번호 찾기
    int findPasswordConfirm(AdminMemberDto adminMemberDto);

    // 새 패스워드 만들기
    String createNewPassword();

    // 새 패스워드 메일 발송
    void sendNewPasswordByMail(String toMailAddr, String newPassword);

    // 회원탈퇴
    int SignOutConfirm(int no);

}
