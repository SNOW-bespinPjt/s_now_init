package com.btc.snow.admin.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminDaoMB {
    boolean isAdminMember(String a_m_id);

    int insertAdminAccount(AdminMemberDto adminMemberDto);

    AdminMemberDto selectAdminForLogin(String a_m_id);

    int updateAdminAccount(AdminMemberDto adminMemberDto);

    AdminMemberDto selectLoginedAdmin(int a_m_no);

    List<AdminMemberDto> selectAdmins();

    int updateAdminApproval(int a_m_no);

    AdminMemberDto selectAdminForFindPassword(Map<String, Object> map);

    int updatePassword(String a_m_id, String newPassword);

    int deleteAdmin(int a_m_no);
}
