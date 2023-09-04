package com.btc.snow.user.meeting.study;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserStudyMapper {


//    public List<UserBookDto> selectSearchBooks(String title);

    int insertStudy(UserStudyDto userStudyDto);

    UserStudyDto studyDetail(int no);
}
