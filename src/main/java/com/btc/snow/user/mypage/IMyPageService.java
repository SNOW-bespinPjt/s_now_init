package com.btc.snow.user.mypage;

import com.btc.snow.include.StudyPromiseDto;

import java.util.List;

public interface IMyPageService {


    List<StudyPromiseDto> selectScedule(String id);

    int updateStatus(int no);
}
