package com.btc.snow.user.attendance;



import com.google.zxing.WriterException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/user")
@Log4j2
public class UserAttendanceController {

    @Autowired
    UserAttendanceService userAttendanceService;


    @GetMapping("/attendance")
    @ResponseBody
    public  Object qrCreate() throws WriterException {
       log.info("qrCreate() called");

        userAttendanceService.qrCreate();

        return  null;
    }


    @PostMapping("/attendance/confirm")
    @ResponseBody
    public Object qrCheckConfrim(@RequestParam ("u_m_no") int u_m_no){

        log.info("qrCheckConfrim() called");



        return userAttendanceService.qrChackConfirm(u_m_no);
    }


}
