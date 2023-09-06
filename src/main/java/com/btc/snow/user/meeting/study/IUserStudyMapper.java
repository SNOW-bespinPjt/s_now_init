package com.btc.snow.user.meeting.study;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserStudyMapper {


//    public List<UserBookDto> selectSearchBooks(String title);

    int insertStudy(UserStudyDto userStudyDto);

    UserStudyDto studyDetail(int no);

    List<UserStudyDto> coinByStudy(String id);
}
