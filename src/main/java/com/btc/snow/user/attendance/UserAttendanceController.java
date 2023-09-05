package com.btc.snow.user.attendance;


import com.btc.snow.admin.assignment.UploadFileService;
import com.btc.snow.include.SubmitDto;
import com.btc.snow.user.member.UserMemberDto;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
@Log4j2
public class UserAttendanceController {

    @Autowired
    UserAttendanceService userAttendanceService;

    @Autowired
    UploadFileService uploadFileService;

    @PostMapping("/attendence/docs")
    @ResponseBody
    public Object uploadFile(UserAttendanceDto userAttendanceDto, HttpSession session) throws IOException, FirebaseAuthException {
        log.info("file {}", userAttendanceDto.getFile());
        Map<String, Object> resMap = new HashMap<>();
        log.info("uploadFile()!!");
        SubmitDto submitDto = new SubmitDto();
        submitDto.setA_no(userAttendanceDto.getNo());


        log.info("userAttendanceDto getNo{}", userAttendanceDto.getNo());

        String savedFileName = uploadFileService.upload(userAttendanceDto.getFile());
        UserMemberDto userMemberDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        log.info("userMemberDto {}", userMemberDto.getId());

        if (savedFileName != null) {
            submitDto.setU_id(userMemberDto.getId());
            submitDto.setFile(savedFileName);

            return userAttendanceService.submitDocument(submitDto);
        } else {
            return null;
        }


    }


    @GetMapping("/attendance")
    @ResponseBody
    public Object qrCreate(HttpSession session) throws WriterException {

        log.info("qrCreate() called");


        return userAttendanceService.qrCreate((UserMemberDto) session.getAttribute("loginedUserDto"));
    }


    @GetMapping("/attendence/confirm")
    @ResponseBody
    public ResponseEntity<Object> qrCheckConfrim(@RequestParam("u_id") String u_id) {

        log.info("qrCheckConfrim() called");
        log.info("qrCheckConfrim() u_id : " + u_id);
        int result = (Integer) userAttendanceService.qrChackConfirm(u_id);

        log.info("hey  : " + result);

        if (result <= 0) {
            log.info("confirm fail");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/"));

            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        } else {
            log.info("confirm success!!");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/"));

            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }


    }


    @GetMapping("/attendence/validSubmit")
    public Object selectValidSubmit(HttpSession session) {
        log.info("selectValidSubmit() !!!");

        ModelAndView modelAndView = new ModelAndView();
        UserAttendanceDto userAttendanceDto = userAttendanceService.selectValidSubmitAttendence(session);

        if (userAttendanceDto != null) {
            log.info("selectValidSubmit() successs");
            modelAndView.addObject("userAttendanceDto", userAttendanceDto);
        } else {
            log.info("selectValidSubmit() fail!!");
        }

        return modelAndView;
    }


    @PostMapping("/attendence/absent")
    @ResponseBody
    public Object selectAttendenceAbsent(HttpSession session) {
        log.info("selectAbsent()!!");

        return getObject(session, "absent");
    }

    @PostMapping("/attendence/ackattendence")
    @ResponseBody
    public Object selectAttendenceACK(HttpSession session) {
        log.info("selectAttendenceACK()");

        return getObject(session, "attendanceACK");
    }

    @PostMapping("/attendence/tardy")
    @ResponseBody
    public Object selectAttendenceTardy(HttpSession session) {
        log.info("selectAttendenceTardy()");

        return getObject(session, "tardy");
    }


    private Object getObject(HttpSession session, String request) {
        UserMemberDto userMemberDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        List<UserAttendanceDto> userAttendanceDtos = null;
        if (request.equals("absent")) {
            userAttendanceDtos = userAttendanceService.selectAbsentAttendence(userMemberDto.getId());

        }
        if (request.equals("attendanceACK")) {
            userAttendanceDtos = userAttendanceService.selectACKAttendence(userMemberDto.getId());

        }
        if (request.equals("tardy")) {
            userAttendanceDtos = userAttendanceService.selectTardyAttendence(userMemberDto.getId());

        }


        if (userAttendanceDtos != null) {
            log.info("selectCategory success");
            return userAttendanceDtos;

        } else {
            log.info("selectCategory fail!!");
            return null;
        }
    }

}
