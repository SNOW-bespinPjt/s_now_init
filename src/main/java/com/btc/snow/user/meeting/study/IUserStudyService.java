package com.btc.snow.user.meeting.study;

public interface IUserStudyService {

    public int writeStudy(UserStudyDto userStudyDto);

    public UserStudyDto studyDetail(int no);


}
