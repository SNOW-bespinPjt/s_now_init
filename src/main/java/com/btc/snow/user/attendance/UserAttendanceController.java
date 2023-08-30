package com.btc.snow.user.attendance;


import com.btc.snow.user.member.UserMemberDto;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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


    @GetMapping("/test")
    public String test() {


        return "/user/member/toast_ui";
    }


    @PostMapping("/attendance/confirm")
    @ResponseBody
    public Object qrCheckConfrim(@RequestParam("u_m_no") int u_m_no) {

        log.info("qrCheckConfrim() called");


        return userAttendanceService.qrChackConfirm(u_m_no);
    }


}
