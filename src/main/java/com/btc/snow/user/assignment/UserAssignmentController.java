package com.btc.snow.user.assignment;

import com.btc.snow.admin.assignment.AdminAssignmentDto;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/user/assignment")
public class UserAssignmentController {

    @Autowired
    UserAssignmentService userAssignmentService;

    @Autowired
    UploadFileServiceForUser uploadFileServiceForUser;

    String nextPage = null;

    /*
     * 과제 리스트(과제 페이지)
     */
    @GetMapping("/") //수정 필요 ~ : 공부 -> 적용
    public String List(HttpSession session, Model model) {
        log.info("[UserAssignmentController] List()");

        nextPage = "user/assignment/list_assignments";

        // USER 허용 과제 리스트 불러오기
        List<UserAssignmentDto> userAssignmentDtos = userAssignmentService.listAssignment(session);

        model.addAttribute("userAssignmentDtos", userAssignmentDtos);

        return nextPage;
    }

    /*
     * 과제 등록
     */
    @PostMapping("/registration_confirm")
    public String RegistrationConfirm(UserAssignmentDto userAssignmentDto,
                                      HttpSession session,
                                      @RequestParam("file") MultipartFile file) {
        log.info("[UserAssignmentController] RegistrationConfirm()");

        nextPage = "redirect:/user/assignment/";

        // 세션 > user_no 저장
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();
        log.info("과제 등록 user_no --->" + user_no);
        userAssignmentDto.setUser_no(user_no);

        // group_id 저장
        int group_id = userAssignmentDto.getNo();
        log.info("과제 수정 group_id --->" + group_id);
        userAssignmentDto.setGroup_id(group_id);

        // file_name 저장
        userAssignmentDto.setFile_user_name(file.getOriginalFilename());
        log.info("userAssignmentDto : " + userAssignmentDto.getFile_user_name());

        // file 저장
        String saveFileName = uploadFileServiceForUser.upload(file, loginedUserDto.getId());

        if (saveFileName != null) {
            userAssignmentDto.setFile_user(saveFileName); // 파일 저장이 되면 그걸 넣어주기
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
    public ModelAndView getAssignment(@RequestParam("no") int no, HttpSession session) {
        log.info("[UserAssignmentController] getAssignment()");

        nextPage = "user/assignment/detail_assignment";

        // no에 맞는 페이지 가져오기
        AdminAssignmentDto adminAssignmentDto = userAssignmentService.getDetail(no);

        // 회원에 맞는 정보 가져오기
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();
        UserAssignmentDto userAssignmentInfo = userAssignmentService.getUserAssignmentInfo(user_no, no);

        Map<String, Object> msg = new HashMap<>();
        msg.put("adminAssignmentDto", adminAssignmentDto);
        msg.put("userAssignmentInfo", userAssignmentInfo);

        ModelAndView mv = new ModelAndView(nextPage);
        mv.addObject("msg", msg);

        return mv;
    }


    /*
     * 과제 수정
     */
    @PostMapping("/assignment_modify_confirm")
    public String modifyAssignmentConfirm(@RequestParam("file") MultipartFile file,
                                          UserAssignmentDto userAssignmentDto,
                                          HttpSession session) {
        log.info("[UserAssignmentController] modifyAssignmentConfirm()");

        nextPage = "redirect:/user/assignment/";

        // 세션 > user_no 저장
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();
        log.info("과제 수정 user_no --->" + user_no);
        userAssignmentDto.setUser_no(user_no);

        // group_id 저장
        int group_id = userAssignmentDto.getNo();
        log.info("과제 수정 group_id --->" + group_id);
        userAssignmentDto.setGroup_id(group_id);

        // file_name 저장
        userAssignmentDto.setFile_user_name(file.getOriginalFilename());
        log.info("userAssignmentDto : " + userAssignmentDto.getFile_user_name());

        // SAVE FILE
        if (!file.getOriginalFilename().equals("")) {
            String savedFileName = uploadFileServiceForUser.upload(file, loginedUserDto.getId());
            if (savedFileName != null)
                userAssignmentDto.setFile_user(savedFileName);
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
        log.info("[UserAssignmentController] deleteAssignmentConfirm()");

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


        return null;
    }

}
