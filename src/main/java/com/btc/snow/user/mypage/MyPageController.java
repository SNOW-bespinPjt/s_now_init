package com.btc.snow.user.mypage;


import com.btc.snow.include.StudyPromiseDto;
import com.btc.snow.include.page.PageDefine;
import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.assignment.UserAssignmentService;
import com.btc.snow.user.attendance.UserAttendanceDto;
import com.btc.snow.user.attendance.UserAttendanceService;
import com.btc.snow.user.member.UserMemberDto;
import com.btc.snow.user.member.UserMemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
@Log4j2
public class MyPageController {

    @Autowired
    UserAttendanceService userAttendanceService;

    @Autowired
    UserAssignmentService userAssignmentService;

    @Autowired
    MyPageService myPageService;

    @Autowired
    UploadFileServiceForMypage uploadFileServiceForMypage;

    @Autowired
    UserMemberService userMemberService;

    @GetMapping(value = {"", "/"})
    public Object home(HttpSession httpSession,
                       @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                       @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_AMOUNT) int amount) {
        HttpHeaders headers = new HttpHeaders();
        log.info("home()!!!");
        UserMemberDto userMemberDto = (UserMemberDto) httpSession.getAttribute("loginedUserDto");

        String u_id = userMemberDto.getId();
        ModelAndView modelAndView = new ModelAndView();
        List<UserAttendanceDto> userAttendanceDtos = null;

        userAttendanceDtos = userAttendanceService.selectAllUserforAttendence(u_id);
//        PageMakerDto pageMakerDto = userAttendanceService.listAttendence(u_id, pageNum, amount);


        double ratio = 0;

        if (userAttendanceDtos != null) {
            ratio = ((double) userAttendanceDtos.size() / (double) 214) * 100.0;
        }

        assert userAttendanceDtos != null;
        log.info("dtos.size() {} ", userAttendanceDtos.size());
        log.info("ratio {}", Math.round(ratio));

        modelAndView.addObject("userAttendanceDtos", userAttendanceDtos);
        modelAndView.addObject("ratio", Math.round(ratio));
//        modelAndView.addObject("pageMakerDto", pageMakerDto);


        // --------------------- 나의 과제 ------------------------
        int user_no = userMemberDto.getNo();
        String id = userMemberDto.getId();

        // 과제 점수
        Map<String, Object> pointDtos = userAssignmentService.myPoint(user_no);

        modelAndView.addObject("pointDtos", pointDtos);

        // 과제 리스트
        List<UserAssignmentDto> userAssignmentDtos = userAssignmentService.myAssignment(user_no);

        modelAndView.addObject("userAssignmentDtos", userAssignmentDtos);
        modelAndView.addObject("id", id);

        modelAndView.setViewName("/user/mypage/home");

        return modelAndView;
    }

    @GetMapping("/attendance")
    public Object mypageAttendance() {
        log.info("Controller mypageAttendance() !!");

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/user/mypage/attendence_home");

        return modelAndView;
    }

    @GetMapping("/attendence/valid")
    public Object attendenceValid() {
        log.info("attendenceValid !!");

        ModelAndView modelAndView = new ModelAndView();


        return null;
    }

    @PostMapping("/schedule")
    @ResponseBody
    public Object goToSchedule(HttpSession session) {
        log.info(" goToSchedule() []");

        List<StudyPromiseDto> studyPromiseDtos = null;
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        studyPromiseDtos = myPageService.selectScedule(loginedUserDto.getId());

        log.info("loginedUserDto {}", loginedUserDto.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("studyPromiseDtos", studyPromiseDtos);
        map.put("loginedUserDto", loginedUserDto);

        return map;
    }
  

    @GetMapping("/approval")
    @ResponseBody
    public ResponseEntity<Object> approvalStatus(@RequestParam("no") int no) {
        log.info("approvalStatus()");
        int result = myPageService.updateStatus(no);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/mypage"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

    }


    @PostMapping("/userimg")
    @ResponseBody
    public Object userImgUpload(@RequestParam("file") MultipartFile file, HttpSession session) {
        log.info("file {}", file);
        UserMemberDto userMemberDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        log.info("uploadFile()!!");

        String savedFileName = uploadFileServiceForMypage.upload(file, userMemberDto.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("savedFileName", savedFileName);


        if (savedFileName != null) {
            userMemberDto.setImg(savedFileName);

            userMemberService.uploadUserImg(userMemberDto);
            return map;
        } else {
            return "업로드 실패";
        }

    }


    @GetMapping("/studyhome")
    public Object gotoStudyHome(HttpSession session) {
        log.info(" gotoStudyHome()");
        List<StudyPromiseDto> studyPromiseDtos = null;
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        studyPromiseDtos = myPageService.selectScedule(loginedUserDto.getId());
        ModelAndView modelAndView = new ModelAndView();


        log.info("loginedUserDto {}", loginedUserDto.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("studyPromiseDtos", studyPromiseDtos);
        map.put("loginedUserDto", loginedUserDto);

        modelAndView.addObject("studyAndLoginDto", map);
        modelAndView.setViewName("/user/mypage/schedule/studyHome");


        return modelAndView;

    }
}
