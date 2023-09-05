package com.btc.snow.user.attendance;


import com.btc.snow.include.SubmitDto;
import com.btc.snow.include.page.Criteria;
import com.btc.snow.include.page.PageMakerDto;
import com.btc.snow.user.member.UserMemberDto;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class UserAttendanceService implements IUserAttendanceService {

    @Autowired
    UserAttendanceMapper userAttendanceMapper;


    public Object qrCreate(UserMemberDto userMemberDto) throws WriterException {
        log.info("Service qrCreate() called");


        int width = 200;
        int height = 200;


        // do update confirm?u_m_no=1
        String url = "http://localhost:8090/user/attendence/confirm?u_id=" + userMemberDto.getId();

        BitMatrix encode = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);
        Map<String, Object> map = new HashMap<>();


        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(encode, "PNG", out);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(out.toByteArray());


        } catch (Exception e) {
            log.error(e);
        }
        return "404";
    }

    @Override
    public Object qrChackConfirm(String u_id) {
        log.info("Service qrChackConfirm() called");
        LocalTime currTime = LocalTime.now();
        LocalTime morningTime = LocalTime.of(9, 10);
        LocalTime noonTime = LocalTime.of(14, 10);
        int result = 0;

        Map<Object, Object> map = new HashMap<>();
        map.put("u_id", u_id);


        if (currTime.isBefore(LocalTime.NOON)) {
            map.put("tstatus", 0);

            int validResult = userAttendanceMapper.isValidStatus(map);

            log.info("validResult : " + validResult);

            if (validResult > 0) {
                if (currTime.isAfter(morningTime)) {
                    map.put("i", 1);
                    map.put("i1", 0);
                    result = userAttendanceMapper.qrCheckConfrim(map);


                } else {
                    log.info("hey~~~: " + map.get("u_id"));
                    map.put("i", 0);
                    map.put("i1", 0);
                    result = userAttendanceMapper.qrCheckConfrim(map);

                }
            }
        } else {


            if (currTime.isAfter(noonTime)) {
                map.put("i", 1);
                map.put("i1", 1);
                result = userAttendanceMapper.qrCheckConfrim(map);

                log.info("current time is After noonTime !! ");


            } else {
                map.put("i", 0);
                map.put("i1", 1);
                result = userAttendanceMapper.qrCheckConfrim(map);

            }
        }


        return result;
    }


    @Override
    public Object selectUserforAttendence(String u_id) {
        log.info("selectUserforAttendence : ");
        log.info(" u_id : " + u_id);
        LocalTime currentTime = LocalTime.now();

        LocalTime morningTime = LocalTime.of(9, 0);
        LocalTime lastMorningTime = LocalTime.of(12, 0);
        LocalTime noonTime = LocalTime.of(14, 0);
        LocalTime lastNoonTime = LocalTime.of(18, 0);

        Map<Object, Object> map = new HashMap<>();

        if ((currentTime.isAfter(morningTime) && currentTime.isBefore(lastMorningTime))) {
            map.put("u_id", u_id);
            map.put("tstatus", 0);
            return userAttendanceMapper.selectAttendenceStatus(map);

        } else if ((currentTime.isAfter(noonTime) && currentTime.isBefore(lastNoonTime))) {
            map.put("u_id", u_id);
            map.put("tstatus", 1);
            return userAttendanceMapper.selectAttendenceStatus(map);

        }
        return null;

    }

    @Override
    public List<UserAttendanceDto> selectAllUserforAttendence(String u_id) {
        log.info("selectAllUserforAttendence : ");
        log.info(" u_id : " + u_id);
        List<UserAttendanceDto> userAttendanceDtos;
        Map<String, Object> map = new HashMap<>();
        map.put("u_id", u_id);


        userAttendanceDtos = userAttendanceMapper.selectAllUserforAttendence(map);

        log.info("userAttendenceDtos {}", userAttendanceDtos);

        if (userAttendanceDtos == null) {
            log.info("usetrAttendenceDtos null!!");

            return null;

        } else {


            return userAttendanceDtos;

        }


    }


    @Override
    public UserAttendanceDto selectValidSubmitAttendence(HttpSession session) {
        log.info("selectValidSubmitAttendence()!!");
        Map<String, Object> map = new HashMap<>();

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        map.put("ustatus", 0);
        map.put("u_no", loginedUserDto.getId());


        return userAttendanceMapper.selectValidAttDto(map);
    }

    @Override
    public List<UserAttendanceDto> selectAbsentAttendence(String Id) {
        log.info("selectAbsentAttendence!!");


        return userAttendanceMapper.selectAbsentAttendence(Id);
    }

    @Override
    public List<UserAttendanceDto> selectACKAttendence(String id) {
        log.info("selectACKAttendence!!");

        return userAttendanceMapper.selectACKAttendence(id);
    }

    @Override
    public List<UserAttendanceDto> selectTardyAttendence(String id) {
        log.info("selectTardyAttendence!!");

        return userAttendanceMapper.selectTardyAttendence(id);
    }

    @Override
    public Object submitDocument(SubmitDto submitDto) {
        log.info("submitDocument(SubmitDto submitDto)");
        Map<String, Object> map = new HashMap<>();

        int result = userAttendanceMapper.updateDocumentToSubmit(submitDto);
//        SubmitDto selectSubmitDto = userAttendanceMapper.selectUpdateStatus(submitDto.getU_id());
        map.put("result", result);
        if (result <= 0) {
            log.info("submitDocument FAIL");
        } else {
            log.info("submitDocument SUCCESS");
            result = userAttendanceMapper.updateAttendenceUstatus(submitDto);
            map.put("updateAttendence", result);
            if (result <= 0) {
                log.info("updateAttendenceUstatus Fail!!");
            }
            if (result > 0) {
                log.info("updateAttendenceUstatus Success!!");
            }

        }


//        map.put("status", status);

        return map;
    }

    @Override
    public PageMakerDto listAttendence(String uId, int pageNum, int amount) {
        log.info("listAttendence(String uId, int pageNum, int amount)");

        Criteria criteria = new Criteria(pageNum, amount);
        int totalCnt = userAttendanceMapper.getTotalCnt(uId);

        return new PageMakerDto(criteria, totalCnt);
    }


    //2주 전 데이터 ustatus update (1 -> 업데이트 불가능)
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void updateAttendenceUstatus() {
        log.info("updateAttendenceUstatus()!!");

        userAttendanceMapper.updateUstatus();
    }

    //오후 12시   오후 6시 최종 출결 체크
    //1. 12시 체크
    @Scheduled(cron = "0 0 12 * * 5", zone = "Asia/Seoul")
    public void updateAttendenceBy() {
        log.info("updateAttendence()");


        userAttendanceMapper.updateAttendenceBy();
    }


//    @Override
//    public SubmitDto selectAttendanceSubmit(String id) {
//
//        SubmitDto submitDto = userAttendanceMapper.selectUpdateStatus(id);
//
//
//        return null;
//    }
}
