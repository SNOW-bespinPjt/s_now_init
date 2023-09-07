package com.btc.snow.user.curriculum;


import com.btc.snow.admin.curriculum.AdminCurriculumDto;
import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.user.meeting.book.UserBookItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserCurriculumDaoMB {

    // SHOW ALL ADMIN CURRICULUM
    public List<AdminCurriculumDto> selectAdminCurriculum();

    // SHOW DETAIL CURRICULUM USING NO
    public AdminCurriculumDto showDetailByNo(int no);

    // GET ADMIN NAME USING ADMIN_ID
    public AdminMemberDto selectAdminName(String adminId);

    // GET BOOK COVER USING BOOK_NO
    public UserBookItemDto selectBookCover(int bookNo);

    // SEARCH TITLE CURRICULUM
    public List<AdminCurriculumDto> searchTitleByWord(Map<String, Object> msgMap);

    // EVALUATE CURRICULUM GRADE
//    public void evaluateCurriculum(UserHistoryPointDto userHistoryPointDto);

}
