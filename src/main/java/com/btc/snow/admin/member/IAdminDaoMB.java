package com.btc.snow.admin.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminDaoMB {
    boolean isAdminMember(String id);

    int insertAdminAccount(AdminMemberDto adminMemberDto);

    AdminMemberDto selectAdminForLogin(String id);

    int updateAdminAccount(AdminMemberDto adminMemberDto);

    AdminMemberDto selectLoginedAdmin(int no);

    List<AdminMemberDto> selectAdmins();

    int updateAdminApproval(int no);

    AdminMemberDto selectAdminForFindPassword(Map<String, Object> map);

    int updatePassword(String id, String newPassword);

    int deleteAdmin(int no);
}
