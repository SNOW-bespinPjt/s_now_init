package com.btc.snow.user.meeting.study;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String writeStudy(UserStudyDto userStudyDto, @RequestParam("file") MultipartFile file) {
        log.info("writeStudy()");

        String nextPage = "redirect:/user/meeting/list";
        log.info("========={}", userStudyDto.getBook_no());

        String saveFileName = studyUploadFileService.upload(file);
        log.info("saveFileName {}", saveFileName);


        if (saveFileName != null) {
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


}
