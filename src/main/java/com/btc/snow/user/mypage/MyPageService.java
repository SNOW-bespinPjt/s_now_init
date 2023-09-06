package com.btc.snow.user.mypage;

import com.btc.snow.include.StudyPromiseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class MyPageService implements IMyPageService {
    @Autowired
    IMyapgeMapper iMyapgeMapper;

    @Override
    public List<StudyPromiseDto> selectScedule(String id) {
        log.info("selectScedule(String id)");


        return iMyapgeMapper.selectSceduleByUser(id);
    }

    @Override
    public int updateStatus(int no) {
        log.info("updateStatus()");


        return iMyapgeMapper.updateStatus(no);
    }
}
