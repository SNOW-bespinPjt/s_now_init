package com.btc.snow.user.meeting.study;

import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        System.out.println("+++++++++++++++++++" + userStudyDto.getTitle());
        System.out.println("+++++++++++++++++++" + userStudyDto.getBody());
        System.out.println("+++++++++++++++++++" + userStudyDto.getHit());

        model.addAttribute("userStudyDto", userStudyDto);

        return nextPage;
    }
}
