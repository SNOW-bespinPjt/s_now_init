package com.btc.snow.user.mypage;

import com.btc.snow.include.StudyPromiseDto;
import com.btc.snow.include.page.Criteria;
import com.btc.snow.include.page.PageMakerDto;
import com.btc.snow.user.member.UserMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class MyPageService implements IMyPageService {
    @Autowired
    IMyapgeMapper iMyapgeMapper;

    @Override
    public Map<String, Object> selectScedule(String id, int pageNum, int amount) {
        log.info("selectScedule(String id)");
        Criteria criteria = new Criteria(pageNum, amount);
        Map<String, Object> map = new HashMap<>();
        List<UserMemberDto> userMemberDtos = null;
        map.put("skip", criteria.getSkip());
        map.put("amount", criteria.getAmount());
        map.put("id", id);

//        신청에 대한 dtos
        List<StudyPromiseDto> studyPromiseDtos = iMyapgeMapper.selectSceduleByUser(map);
        int totalCnt = iMyapgeMapper.getTotalSudyPromiseDtos(id);

        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("studyPromiseDtos", studyPromiseDtos);
        map.put("pageMakerDto", pageMakerDto);

//        요청에 대한 dtos

        List<StudyPromiseDto> studyPromiseDtosByMember = iMyapgeMapper.selectSceduleByMember(map);
        int totalCntByMember = iMyapgeMapper.getTotalSudyPromiseDtosByMember(id);

        PageMakerDto pageMakerDtoByMember = new PageMakerDto(criteria, totalCnt);

        map.put("studyPromiseDtosByMember", studyPromiseDtosByMember);
        map.put("pageMakerDtoByMember", pageMakerDtoByMember);


        log.info("total Cnt : {}", totalCntByMember);


        return map;
    }

    @Override
    public int updateStatus(int no) {
        log.info("updateStatus()");


        return iMyapgeMapper.updateStatus(no);
    }
}
