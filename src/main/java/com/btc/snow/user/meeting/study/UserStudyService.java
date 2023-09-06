package com.btc.snow.user.meeting.study;

import com.btc.snow.include.page.Criteria;
import com.btc.snow.include.page.PageMakerDto;
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

    public Map<String, Object> studyList(int pageNum, int amount) {
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
        int listCount = iUserStudyMapper.listCount();


        Criteria criteria = new Criteria(pageNum, amount);
        Map<String, Object> map = new HashMap<>();

        map.put("skip", criteria.getSkip());
        map.put("amount", criteria.getAmount());


        List<UserStudyDto> userStudyDtos = iUserStudyMapper.selectStudy(map);
//        int totalCnt = iUserStudyMapper.getTotalCntOfSubmit();

        PageMakerDto pageMakerDto = new PageMakerDto(criteria, listCount);

        map.put("userStudyDtos", userStudyDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    public void studyAttend(int studyNo, String id) {
        log.info("studyAttend()");

        HashMap<String, Object> map = new HashMap<>();

        map.put("studyNo", studyNo);
        map.put("id", id);

        iUserStudyMapper.studyAttend(map);
    }
}
