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

    /*
     * SHOW DETAIL CURRICULUM INFO USING NO
     */
    public Map<String, Object> showDetailCurriculum(int no);

    /*
     * MODIFY CURRICULUM CONFIRM
     */
    public int modifyCurriculumConfirm(AdminCurriculumDto adminCurriculumDto);

    /*
     * DELETE CURRICULUM CONFIRM
     */
    public int deleteCurriculumConfirm(int no);

    /*
     * SEARCH CURRICULUM CONFIRM USING TITLE WORD
     */
    public Map<String, Object> searchTitleConfirm(String searchTitle, AdminMemberDto loginedAdminDto);

}
