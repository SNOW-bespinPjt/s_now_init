package com.btc.snow.user.mypage;

import com.btc.snow.include.StudyPromiseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MyPageService implements IMyPageService {
    @Autowired
    IMyapgeMapper iMyapgeMapper;

    @Override
    public StudyPromiseDto selectScedule(String id) {
        log.info("selectScedule(String id)");

        return iMyapgeMapper.selectSceduleByUser(id);
    }
}
