package com.btc.snow.user.meeting.study;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface IUserStudyMapper {


//    public List<UserBookDto> selectSearchBooks(String title);

    int insertStudy(UserStudyDto userStudyDto);

    UserStudyDto studyDetail(int no);

    void updateHit(int no);

    List<UserStudyDto> selectStudy(Map<String, Object> map);

    int getTotalCntOfSubmit();

    void updateStatus();

    int listCount();

    List<UserStudyDto> coinByStudy(String id);


    void studyAttend(HashMap<String, Object> map);
}
