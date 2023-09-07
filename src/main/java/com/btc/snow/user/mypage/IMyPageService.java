package com.btc.snow.user.mypage;

import java.util.Map;

public interface IMyPageService {


    Map<String, Object> selectScedule(String id, int pageNum, int amount);

    int updateStatus(int no);
}
