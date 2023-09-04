package com.btc.snow.admin.curriculum;


import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.user.meeting.book.UserBookItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminCurriculumDaoMB {

    // INPUT NEW CURRICULUM
    public int inputNewCurriculum(AdminCurriculumDto adminCurriculumDto);

    // SHOW ALL CURRICULUM WHERE ADMIN_ID
    public List<AdminCurriculumDto> selectCurriculumById(AdminMemberDto loginedAdminDto);

    // SHOW ALL ADMIN CURRICULUM
    public List<AdminCurriculumDto> selectAdminCurriculum();

    // SHOW DETAIL CURRICULUM USING NO
    public AdminCurriculumDto showDetailByNo(int no);

    // UPDATE CURRICULUM INFO
    public int updateCurriculum(AdminCurriculumDto adminCurriculumDto);

    // GET ADMIN NAME USING ADMIN_ID
    public AdminMemberDto selectAdminName(String adminId);

    // GET BOOK COVER USING BOOK_NO
    public UserBookItemDto selectBookCover(int bookNo);

    // DELETE CURRICULUM CONIFRM
    public int removeCurriculum(int no);

    // SEARCH TITLE CURRICULUM
    public List<AdminCurriculumDto> searchTitleByWord(Map<String, Object> msgMap);

}
