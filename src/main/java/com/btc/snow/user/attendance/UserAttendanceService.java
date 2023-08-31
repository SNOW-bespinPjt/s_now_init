package com.btc.snow.user.attendance;


import com.btc.snow.user.member.UserMemberDto;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.util.HashMap;
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
        String url = "http://localhost:8090/user/attendence/confirm?u_m_no=" + Integer.toString(userMemberDto.getU_m_no());

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
    public Object qrChackConfirm(int u_m_no) {

        log.info("Service qrChackConfirm() called");
        LocalTime currTime = LocalTime.now();
        LocalTime morningTime = LocalTime.of(9, 10);
        LocalTime noonTime = LocalTime.of(14, 10);
        int result = 0;

        Map<Object, Object> map = new HashMap<>();
        map.put("u_m_no", u_m_no);


        if (currTime.isBefore(LocalTime.NOON)) {
            if (currTime.isAfter(morningTime)) {
                map.put("i", 1);
                map.put("i1", 0);
                result = userAttendanceMapper.qrCheckConfrim(map);


            } else {
                log.info("hey~~~: " + map.get("u_m_no"));
                map.put("i", 0);
                map.put("i1", 0);
                result = userAttendanceMapper.qrCheckConfrim(map);

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

//    @Override
//    public Object selectUserforAttendence() {
//        userAttendanceMapper.select
//
//        return null;
//    }


}
