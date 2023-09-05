package com.btc.snow.user.meeting.study;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class UserStudyService implements IUserStudyService {


    @Autowired
    IUserStudyMapper iUserStudyMapper;


    public int writeStudy(UserStudyDto userStudyDto) {
        log.info("writeStudy()");

        int result = iUserStudyMapper.insertStudy(userStudyDto);

        if (result <= 0) {
            log.info("writeStudy() FAIL");

        } else {
            log.info("writeStudy() SUCCESS");

        }
        return result;
    }

    public UserStudyDto studyDetail(int no) {
        log.info("studyDetail()");

        return iUserStudyMapper.studyDetail(no);
    }

    public void updateHit(int no) {
        log.info("updateHit()");

        iUserStudyMapper.updateHit(no);
    }

    public Map<String, Object> studyList() {
        log.info("studyList()");

        Map<String, Object> map = new HashMap<>();

        List<UserStudyDto> userStudyDtos = iUserStudyMapper.selectStudy();

        map.put("userStudyDtos", userStudyDtos);

        return map;
    }
}
