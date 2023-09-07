package com.btc.snow;


import com.btc.snow.user.attendance.UserAttendanceDto;
import com.btc.snow.user.attendance.UserAttendanceService;
import com.btc.snow.user.meeting.UserMeetingService;
import com.btc.snow.user.meeting.study.UserStudyDto;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@Log4j2
public class HomeController {

    @Autowired
    UserAttendanceService userAttendanceService;

    @Autowired
    UserMeetingService userMeetingService;

    @GetMapping(value = {""})
    public Object home(HttpSession session) {
        log.info("HomeController home()");
        UserMemberDto userMemberDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        //분기 포인트 로그인!
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");

        LocalTime currentTime = LocalTime.now();

        LocalTime morningTime = LocalTime.of(9, 0);
        LocalTime lastMorningTime = LocalTime.of(12, 0);
        LocalTime noonTime = LocalTime.of(14, 0);
        LocalTime lastNoonTime = LocalTime.of(18, 0);


        if ((currentTime.isAfter(morningTime) && currentTime.isBefore(lastMorningTime)) || (
                currentTime.isAfter(noonTime) && currentTime.isBefore(lastNoonTime)
        )) {


            if (userMemberDto != null) {
                UserAttendanceDto userAttendanceDto = (UserAttendanceDto) userAttendanceService.selectUserforAttendence(userMemberDto.getId());
//                SubmitDto submitDto = userAttendanceService.selectAttendanceSubmit(userMemberDto.getId());
                int result = userAttendanceService.isValidAttendence(userMemberDto.getId());

                if (result > 0) {
                    modelAndView.addObject("qrValidStatus", 0);
                }
                if (result < 0) {
                    modelAndView.addObject("qrValidStatus", 1);
                }


                if (userAttendanceDto == null) {
                    modelAndView.addObject("status", 0);

                } else {
                    modelAndView.addObject("status", 1);
                    modelAndView.addObject("userAttendanceDto", userAttendanceDto);
                }
            }


        } else {
            modelAndView.addObject("qrValidStatus", 0);
        }


        if (currentTime.isAfter(lastNoonTime)) {

            try {
                List<UserAttendanceDto> userAttendanceDtoList = (List<UserAttendanceDto>) userAttendanceService.selectUserAfterLastNoonTime(userMemberDto.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


        //스터디
        Map<String, Object> studyMap = userMeetingService.mainStudy();
        List<UserStudyDto> userStudyDtos = (List<UserStudyDto>) studyMap.get("userStudyDtos");

        modelAndView.addObject("userStudyDtos", userStudyDtos);


        return modelAndView;
    }


}
