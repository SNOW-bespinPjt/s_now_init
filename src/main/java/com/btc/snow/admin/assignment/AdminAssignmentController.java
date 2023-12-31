package com.btc.snow.admin.assignment;

import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin/assignment")
public class AdminAssignmentController {

    @Autowired
    AdminAssignmentService adminAssignmentService;

    @Autowired
    UploadFileServiceForAdmin uploadFileServiceForAdmin;

    String nextPage = null;

    // alert
    String msg = "";

    /*
     * 과제 리스트(과제 페이지)
     */
    @GetMapping("/") //수정 필요 ~ : 공부 -> 적용
    public ModelAndView List() {
        log.info("[AdminAssignmentController] List()");

        nextPage = "/admin/assignment/list_assignments";

        List<AdminAssignmentDto> adminAssignmentDtos = adminAssignmentService.listAssignment();

        ModelAndView mv = new ModelAndView();
        mv.setViewName(nextPage);
        mv.addObject("adminAssignmentDtos", adminAssignmentDtos);

        return mv;

    }

    /*
     * 과제 등록
     */
    @GetMapping("/registration_form")
    public void RegistrationForm() {
        log.info("[AdminAssignmentController] RegistrationForm()");

        nextPage = "/admin/assignment/registration_form";
    }

    @PostMapping("/registration_confirm")
    public String RegistrationConfirm(AdminAssignmentDto adminAssignmentDto,
                                      HttpSession session,
                                      @RequestParam("file") MultipartFile file,
                                      Model model) {
        log.info("[AdminAssignmentController] RegistrationConfirm()");

        nextPage = "/redirect:/admin/assignment/";
        msg = "과제등록에 성공하였습니다";

        // 세션
        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");
        int admin_no = loginedAdminDto.getNo();
        log.info("과제 등록 admin_no --->" + admin_no);
        adminAssignmentDto.setAdmin_no(admin_no);

        adminAssignmentDto.setFile_admin_name(file.getOriginalFilename());
        log.info("adminAssignmentDto : " + adminAssignmentDto.getFile_admin_name());

        // 파일 저장
        String saveFileName = uploadFileServiceForAdmin.upload(file);

        if (saveFileName != null) {
            adminAssignmentDto.setFile_admin(saveFileName); // 파일 저장이 되면 그걸 넣어주기
            int result = adminAssignmentService.RegistrationConfirm(adminAssignmentDto);

            if (result <= 0)
                nextPage = "/";
            msg = "과제등록에 실패하였습니다";


        } else {
            nextPage = "/";
            msg = "과제등록에 실패하였습니다";
        }

        model.addAttribute("msg");
        return nextPage;

    }

    /*
     * 과제 활성화
     */
    @GetMapping("set_admin_active")
    @ResponseBody
    public Object SetAdminActive(@RequestParam("no") int no) {
        log.info("[AdminAssignmentController] SetAdminActive()");
        log.info("no: " + no);

        Map<String, Object> map = new HashMap<>();

        int result = adminAssignmentService.SetAdminActive(no);

        if (result > 0) {
            map.put("result", result);

        } else {
            map.put("error", "Invalid credentials");
        }

        return map;
    }

    /*
     * 과제 상세페이지
     */
    @GetMapping("/get_assignment")
    public ModelAndView getAssignment(@RequestParam("no") int no) {
        log.info("[AdminAssignmentController] getAssignment()");

        nextPage = "/admin/assignment/detail_assignment";

        AdminAssignmentDto adminAssignmentDto = adminAssignmentService.getAssignment(no);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(nextPage);
        mv.addObject("adminAssignmentDto", adminAssignmentDto);

        return mv;
    }


    /*
     * 과제 수정
     */
    @GetMapping("/assignment_modify_form")
    public String AssignmentModifyForm(
            @RequestParam("no") int no,
            Model model
    ) {
        log.info("[AdminAssignmentController] AssignmentModifyForm()");

        nextPage = "/admin/assignment/assignment_modify_form";

        AdminAssignmentDto adminAssignmentDto = adminAssignmentService.getAssignment(no);
        model.addAttribute("adminAssignmentDto", adminAssignmentDto);

        return nextPage;

    }

    @PostMapping("/assignment_modify_confirm")
    public String modifyAssignmentConfirm(@RequestParam("file") MultipartFile file,
                                          AdminAssignmentDto adminAssignmentDto) {
        log.info("[AdminAssignmentController] modifyAssignmentConfirm()");

//        nextPage = "/redirect:/admin/assignment/assignment_modify_form?no=" + adminAssignmentDto.getNo();
        nextPage = "/redirect:/admin/assignment/";

        adminAssignmentDto.setFile_admin_name(file.getOriginalFilename());
        log.info("adminAssignmentDto : " + adminAssignmentDto.getFile_admin_name());

        // SAVE FILE
        if (!file.getOriginalFilename().equals("")) {
            String savedFileName = uploadFileServiceForAdmin.upload(file);
            if (savedFileName != null)
                adminAssignmentDto.setFile_admin(savedFileName);
        }

        int result = adminAssignmentService.modifyAssignmentConfirm(adminAssignmentDto);

        if (result <= 0) {
            log.info("[AdminAssignmentController] ASSIGNMENT MODIFY FAIL");

        }

        return nextPage;
    }


    /*
     * 과제 삭제
     */
    @GetMapping("/delete_assignment_confirm")
    public String deleteAssignmentConfirm(@RequestParam("no") int no) {
        log.info("[AdminAssignmentController] deleteAssignmentConfirm()");

        nextPage = "/redirect:/admin/assignment/";

        int result = adminAssignmentService.deleteAssignmentConfirm(no);

        if (result <= 0) {
            log.info("[AdminAssignmentController] ASSIGNMENT DELETE FAIL");

        }

        return nextPage;

    }


    // -------------------------------------------------------------------------------------------

    /*
     * 과제 제출 학생 리스트
     */
    @GetMapping("/check")
    public ModelAndView checkAssignmentList(@RequestParam("no") int no) {
        log.info("[AdminAssignmentController] checkAssignmentList()");
        log.info("[AdminAssignmentController] no() --> ", no);

        nextPage = "/admin/assignment/check_assignments";

        List<UserMemberDto> userMemberDtos = adminAssignmentService.getUserList();
        List<UserAssignmentDto> userAssignmentDtos = adminAssignmentService.checkAssignmentList(no);

        List<CombinedDto> combinedDtos = new ArrayList<>();

        // UserMemberDto와 UserAssignmentDto를 조합
        for (UserAssignmentDto assignmentDto : userAssignmentDtos) {
            for (UserMemberDto memberDto : userMemberDtos) {
                if (assignmentDto.getUser_no() == memberDto.getNo()) {
                    CombinedDto combinedDto = new CombinedDto();
                    combinedDto.setAssignmentDto(assignmentDto);
                    combinedDto.setMemberDto(memberDto);
                    combinedDtos.add(combinedDto);
                }
            }
        }

        ModelAndView mv = new ModelAndView(nextPage);
        mv.addObject("combinedDtos", combinedDtos);

        return mv;
    }


    /*
     * 과제 점수 등록
     */
    @PostMapping("/assignment_input_point")
    public String AssignmentInputPoint(UserAssignmentDto userAssignmentDto) {
        log.info("[UserAssignmentController] AssignmentInputPoint()");

        nextPage = "/redirect:/admin/assignment/check?no=" + userAssignmentDto.getGroup_id();

        int result = adminAssignmentService.AssignmentInputPoint(userAssignmentDto);
        if (result > 0) {
            log.info("[UserAssignmentController] SUCCESS INPUT POINT");
        } else {
            log.info("[UserAssignmentController] FAIL INPUT POINT");
        }

        return nextPage;
    }

}
