package com.btc.snow.user.assignment;

import com.btc.snow.user.config.UploadFileService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/user/assignment")
public class UserAssignmentController {

    @Autowired
    UserAssignmentService userAssignmentService;

    @Autowired
    UploadFileService uploadFileService;

    String nextPage = null;

    /*
     * 과제 리스트(과제 페이지)
     */
    @GetMapping("/") //수정 필요 ~ : 공부 -> 적용
    public ModelAndView List() {
        log.info("[UserAssignmentController] List()");

        nextPage = "user/assignment/list_assignments";

        List<UserAssignmentDto> userAssignmentDtos = userAssignmentService.listAssignment();

        ModelAndView mv = new ModelAndView();
        mv.setViewName(nextPage);
        mv.addObject("userAssignmentDtos", userAssignmentDtos);

        return mv;

    }

    /*
     * 과제 등록
     */
    @GetMapping("/registration_form")
    public void RegistrationForm() {
        log.info("[UserAssignmentController] RegistrationForm()");

        nextPage = "user/assignment/registration_form";
    }

    @PostMapping("/registration_confirm")
    public String RegistrationConfirm(UserAssignmentDto userAssignmentDto,
                                      HttpSession session,
                                      @RequestParam("file_user") MultipartFile file) {
        log.info("[UserAssignmentController] RegistrationConfirm()");

        nextPage = "redirect:/user/assignment/";

        // 세션 > user_no 저장
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();
        log.info("과제 등록 user_no --->" + user_no);
        userAssignmentDto.setAdmin_no(user_no);

        // group_id 저장
        userAssignmentDto.setGroup_id(userAssignmentDto.getNo());

        // file_name 저장
        userAssignmentDto.setFile_name(file.getOriginalFilename());
        log.info("userAssignmentDto : " + userAssignmentDto.getFile_name());

        // file 저장
        String saveFileName = uploadFileService.upload(file, loginedUserDto.getId());

        if (saveFileName != null) {
            userAssignmentDto.setFile(saveFileName); // 파일 저장이 되면 그걸 넣어주기
            int result = userAssignmentService.RegistrationConfirm(userAssignmentDto);

            if (result <= 0) {
                nextPage = "/";

            }

        } else {
            nextPage = "/";

        }

        return nextPage;

    }

    /*
     * 과제 상세페이지
     */
    @GetMapping("/get_assignment")
    public ModelAndView getAssignment(@RequestParam("no") int no) {
        log.info("[UserAssignmentController] getAssignment()");

        nextPage = "user/assignment/detail_assignment";

        UserAssignmentDto userAssignmentDto = userAssignmentService.getAssignment(no);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(nextPage);
        mv.addObject("userAssignmentDto", userAssignmentDto);

        return mv;
    }


    /*
     * 과제 수정
     */
    @GetMapping("assignment_modify_form")
    public String AssignmentModifyForm(
            @RequestParam("no") int no,
            Model model
    ) {
        log.info("[UserAssignmentController] AssignmentModifyForm()");

        nextPage = "user/assignment/assignment_modify_form";

        UserAssignmentDto userAssignmentDto = userAssignmentService.getAssignment(no);
        model.addAttribute("userAssignmentDto", userAssignmentDto);

        return nextPage;

    }

    @PostMapping("/assignment_modify_confirm")
    public String modifyAssignmentConfirm(@RequestParam("file_admin") MultipartFile file,
                                          UserAssignmentDto userAssignmentDto,
                                          HttpSession session) {
        log.info("[UserAssignmentController] modifyAssignmentConfirm()");

        nextPage = "redirect:/user/assignment/";

        // 세션 > user_no 저장
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();
        log.info("과제 등록 user_no --->" + user_no);
        userAssignmentDto.setAdmin_no(user_no);

        // group_id 저장
        userAssignmentDto.setGroup_id(userAssignmentDto.getNo());

        // file_name 저장
        userAssignmentDto.setFile_name(file.getOriginalFilename());
        log.info("userAssignmentDto : " + userAssignmentDto.getFile_name());

        // SAVE FILE
        if (!file.getOriginalFilename().equals("")) {
            String savedFileName = uploadFileService.upload(file, loginedUserDto.getId());
            if (savedFileName != null)
                userAssignmentDto.setFile(savedFileName);
        }

        int result = userAssignmentService.modifyAssignmentConfirm(userAssignmentDto);

        if (result <= 0) {
            log.info("[UserAssignmentController] ASSIGNMENT MODIFY FAIL");

        }

        return nextPage;
    }


    /*
     * 과제 삭제
     */
    @GetMapping("/delete_assignment_confirm")
    public String deleteAssignmentConfirm(@RequestParam("no") int no) {
        System.out.println("[UserAssignmentController] deleteAssignmentConfirm()");

        String nextPage = "redirect:/user/assignment/";

        int result = userAssignmentService.deleteAssignmentConfirm(no);

        if (result <= 0) {
            log.info("[UserAssignmentController] ASSIGNMENT DELETE FAIL");

        }

        return nextPage;

    }

    /*
     * 나의 과제
     */
    @GetMapping("/my_assignment")
    public ModelAndView MyAssignment(@RequestParam("no") int no) {
        log.info("[UserAssignmentController] MyAssignment()");

        nextPage = "user/assignment/detail_assignment";

        UserAssignmentDto userAssignmentDto = userAssignmentService.getAssignment(no);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(nextPage);
        mv.addObject("userAssignmentDto", userAssignmentDto);

        return mv;
    }

}
