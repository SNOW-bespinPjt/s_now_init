package com.btc.snow.admin.curriculum;


import com.btc.snow.admin.member.AdminMemberDto;

import java.util.List;

public interface IAdminCurriculumDaoMB {

    // INPUT NEW CURRICULUM
    public int inputNewCurriculum(AdminCurriculumDto adminCurriculumDto);

    // SHOW ALL CURRICULUM WHERE ADMIN_ID
    public List<AdminCurriculumDto> selectCurriculumById(AdminMemberDto loginedAdminDto);

    // SHOW ALL ADMIN CURRICULUM
    public List<AdminCurriculumDto> selectAdminCurriculum();

}
