package com.btc.snow.admin.curriculum;


import com.btc.snow.admin.member.AdminMemberDto;

import java.util.Map;

public interface IAdminCurriculumService {

    /*
     * CREATE CURRICULUM CONFIRM
     */
    public int createCurriculumConfirm(AdminCurriculumDto adminCurriculumDto);

    /*
     * SHOW ALL CURRICULUM WITH ADMIN_ID
     */
    public Map<String, Object> showAllCurriculum(AdminMemberDto loginedAdminDto);

}
