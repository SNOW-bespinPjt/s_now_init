package com.btc.snow.user.meeting.study;

import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/user/study")
public class UserStudyController {

    @Autowired
    UserStudyService userStudyService;

    @Autowired
    StudyUploadFileService studyUploadFileService;

    @PostMapping("/write_study")
    public String writeStudy(UserStudyDto userStudyDto,
                             @RequestParam("file") MultipartFile file,
                             HttpSession session) {
        log.info("writeStudy()");

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        String nextPage = "redirect:/user/meeting/list";

        String saveFileName = studyUploadFileService.upload(file, loginedUserDto.getId());

        if (saveFileName != null) {

            userStudyDto.setUser_id(loginedUserDto.getId());
            userStudyDto.setImg(saveFileName);

            int result = userStudyService.writeStudy(userStudyDto);

            if (result <= 0) {
                log.info("스터디 등록 실패입니다.");
            }

        } else {

            log.info("스터디 등록에 실패입니다.");
        }
        return nextPage;
    }

    @GetMapping("/study_detail")
    public String studyDetail(Model model, @RequestParam("no") int no) {
        log.info("studyDetail()");

        String nextPage = "user/meeting/study_detail";

        UserStudyDto userStudyDto = userStudyService.studyDetail(no);

        userStudyService.updateHit(no);

        model.addAttribute("userStudyDto", userStudyDto);

        return nextPage;
    }

    @GetMapping("/study_list")
    public String studyList(Model model, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                            @RequestParam(value = "amount", required = false, defaultValue = "5") int amount, HttpSession session) {
        log.info("studyList()");

        String nextPage = "user/meeting/study_list";

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        model.addAttribute("loginedUserDto", loginedUserDto);

        Map<String, Object> studyMap = userStudyService.studyList(pageNum, amount);

        List<UserStudyDto> userStudyDtos = (List<UserStudyDto>) studyMap.get("userStudyDtos");


//        UserStudyDto userStudyDto = new UserStudyDto();
//
//        LocalDate startDate = LocalDate.parse(userStudyDto.getStart_date());
//        LocalDate today = LocalDate.now();
//
//        System.out.println("+++++++++++++++++++++++++" + startDate);
//
//        int dateResult = startDate.compareTo(today);
//
//        if (dateResult <= 0) {
//            // 오늘보다 이전인 경우
//            model.addAttribute("dateResult", 0);
//        } else {
//            // 오늘보다 이후인 경우
//            model.addAttribute("studyDateResult", 1);
//        }

        model.addAttribute("userStudyDtos", userStudyDtos);
        model.addAttribute("pageMakerDto", studyMap.get("pageMakerDto"));

        return nextPage;
    }

    @PostMapping("/study_attend")
    @ResponseBody
    public int studyAttend(@RequestParam("studyNo") int studyNo, HttpSession session) {
        log.info("studyAttend()");

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        int result = userStudyService.studyAttend(studyNo, loginedUserDto.getId());

        return result;
    }

    @PostMapping("/button_remove")
    @ResponseBody
    public int buttonRemove(@RequestParam("studyNo") int studyNo, HttpSession session) {
        log.info("buttonRemove()");

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        int result = userStudyService.removeButton(studyNo, loginedUserDto.getId());

        return result;
    }


}
