package com.btc.snow.user.meeting.study;

import java.util.Map;

public interface IUserStudyService {

    public int writeStudy(UserStudyDto userStudyDto);

    public UserStudyDto studyDetail(int no);

    public void updateHit(int no);

    public Map<String, Object> studyList(int pageNum, int amount);

    public void studyAttend(int studyNo, String id);


}
