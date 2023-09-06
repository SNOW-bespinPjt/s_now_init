package com.btc.snow.admin.attendence;

import com.btc.snow.admin.member.AdminMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/admin/attendence")
public class AdminAttendenceController {
    @Autowired
    AdminAttendenceService adminAttendenceService;


    @GetMapping(value = {"", "/"})
    public Object submitDtoRequestEntity(HttpSession session, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                         @RequestParam(value = "amount", required = false, defaultValue = "5") int amount) {
        log.info("submitDtoRequestEntity()");

        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");
        ModelAndView modelAndView = new ModelAndView();

        Map<String, Object> map = adminAttendenceService.selectSubmitDto(loginedAdminDto.getNo(), pageNum, amount);


        modelAndView.setViewName("/admin/attendence/home");
        modelAndView.addObject("adminSubmitDtos", map.get("submitDtos"));
        modelAndView.addObject("pageMakerDto", map.get("pageMakerDto"));


        return modelAndView;
    }

    @PostMapping("/approve")
    @ResponseBody
    public Object approveStatusForSubmit(@RequestParam("no") int no) {
        log.info("approveStatusForSubmit()");
        log.info("no : ", no);
        int result = adminAttendenceService.updateStatusForSubmit(no);


        return result;
    }

    @PostMapping("/cancel")
    @ResponseBody
    public Object cancelStatusForSubmit(@RequestParam("no") int no) {
        log.info("cancelStatusForSubmit()");
        log.info("no : ", no);
        int result = adminAttendenceService.updateStatusForSubmitToCancle(no);


        return result;
    }
}
