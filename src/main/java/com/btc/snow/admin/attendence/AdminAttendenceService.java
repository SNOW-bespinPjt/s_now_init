package com.btc.snow.admin.attendence;


import com.btc.snow.include.SubmitDto;
import com.btc.snow.include.page.Criteria;
import com.btc.snow.include.page.PageMakerDto;
import com.btc.snow.user.attendance.UserAttendanceMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class AdminAttendenceService implements IAdminAttendenceService {
    @Autowired
    UserAttendanceMapper userAttendanceMapper;

    @Autowired
    IAdminAttendenceMapper iAdminAttendenceMapper;


    @Override
    public Map<String, Object> selectSubmitDto(int no, int pageNum, int amount) {
        log.info("selectSubmitDto(int no, int pageNum, int amount)");

        Criteria criteria = new Criteria(pageNum, amount);
        Map<String, Object> map = new HashMap<>();


        map.put("skip", criteria.getSkip());
        map.put("amount", criteria.getAmount());


        List<SubmitDto> submitDtos = iAdminAttendenceMapper.selectSubmitInfo(map);
        int totalCnt = iAdminAttendenceMapper.getTotalCntOfSubmit();
        log.info("submitDtos Value getA_no() {}", submitDtos.get(0).getA_no());
        log.info("submitDtos Value getNo() {}", submitDtos.get(0).getNo());
        log.info("submitDtos Value getFile() {}", submitDtos.get(0).getFile());
        log.info("submitDtos Value getU_id() {}", submitDtos.get(0).getU_id());

        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("submitDtos", submitDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }
}
