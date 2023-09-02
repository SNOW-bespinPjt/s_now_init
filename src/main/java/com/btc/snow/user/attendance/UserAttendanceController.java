package com.btc.snow.user.attendance;


import com.btc.snow.user.member.UserMemberDto;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;


@Controller
@RequestMapping("/user")
@Log4j2
public class UserAttendanceController {

    @Autowired
    UserAttendanceService userAttendanceService;


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


}
