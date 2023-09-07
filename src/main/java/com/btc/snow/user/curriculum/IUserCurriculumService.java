package com.btc.snow.user.curriculum;


import java.util.Map;

public interface IUserCurriculumService {

    /*
     * SHOW ALL CURRICULUM
     */
    public Map<String, Object> showAllCurriculum();

    /*
     * SHOW DETAIL CURRICULUM INFO USING NO
     */
    public Map<String, Object> showDetailCurriculum(int no);

    /*
     * SEARCH CURRICULUM CONFIRM USING TITLE WORD
     */
    public Map<String, Object> searchTitleConfirm(String searchTitle);

    /*
     * EVALUATE CURRICULUM CONFIRM
     */
//    public void evaluateCurriculumConfirm(int grade, int curriculum_no, AdminMemberDto adminMemberDto);

}
