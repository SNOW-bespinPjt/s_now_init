package com.btc.snow.user.meeting.study;

import java.util.Map;

public interface IUserStudyService {

    public int writeStudy(UserStudyDto userStudyDto);

    public UserStudyDto studyDetail(int no);

    public void updateHit(int no);

    public Map<String, Object> studyList();

    public int studyAttend(int studyNo, String id);

    public int removeButton(int studyNo, String id);


}