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

//        UserStudyDto userStudyDto = new UserStudyDto();
//
//        LocalDate startDate = LocalDate.parse(userStudyDto.getStart_date());
//        LocalDate today = LocalDate.now();
//
//        System.out.println("+++++++++++++++++++++++++" + startDate);
//
//        int dateResult = startDate.compareTo(today);
//
//        if (dateResult <= 0) {
//            iUserStudyMapper.updateStatus(dateResult);
//        }


        iUserStudyMapper.updateStatus();


        Map<String, Object> map = new HashMap<>();


        List<UserStudyDto> userStudyDtos = iUserStudyMapper.selectStudy(map);
//        int totalCnt = iUserStudyMapper.getTotalCntOfSubmit();


        map.put("userStudyDtos", userStudyDtos);


        return map;
    }

    public int studyAttend(int studyNo, String id) {
        log.info("studyAttend()");

        HashMap<String, Object> map = new HashMap<>();

        map.put("studyNo", studyNo);
        map.put("id", id);

        int result = iUserStudyMapper.studyAttend(map);

        return result;

    }

    public int removeButton(int studyNo, String id) {
        log.info("removeButton()");

        HashMap<String, Object> map = new HashMap<>();

        log.info("studyNo==={}", studyNo);
        log.info("id==={}", id);

        map.put("studyNo", studyNo);
        map.put("id", id);

        int result = iUserStudyMapper.removeButton(map);

        return result;

    }

}
