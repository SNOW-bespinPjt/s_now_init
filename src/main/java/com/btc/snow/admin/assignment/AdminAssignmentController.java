package com.btc.snow.admin.assignment;

import com.btc.snow.admin.member.AdminMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("/admin/assignment")
@Controller
public class AdminAssignmentController {

    @Autowired
    AdminAssignmentService adminAssignmentService;

    @Autowired
    UploadFileService uploadFileService;

    String nextPage = null;
    String msg = "";

    // alert


    /*
     * 과제 리스트(과제 페이지)
     */
    @GetMapping("/list") //수정 필요 ~ : 공부 -> 적용
    public ModelAndView List() {
        log.info("[AdminAssignmentController] List()");

        nextPage = "admin/assignment/listup_assignments";

        List<AdminAssignmentDto> adminAssignmentDtos = adminAssignmentService.listupAssignment();

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

        nextPage = "admin/assignment/registration_form";

    }

    @PostMapping("/registration_confirm")
    public String RegistrationConfirm(AdminAssignmentDto adminAssignmentDto,
                                      HttpSession session,
                                      @RequestParam("file_admin") MultipartFile file,
                                      Model model) {
        log.info("[AdminAssignmentController] RegistrationConfirm()");

        nextPage = "redirect:/admin/assignment/list";
        msg = "과제등록에 성공하였습니다";

        // 세션
        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");
        int admin_no = loginedAdminDto.getNo();
        log.info("과제 등록 admin_no --->" + admin_no);
        adminAssignmentDto.setAdmin_no(admin_no);

        // 파일 저장
        String saveFileName = uploadFileService.upload(file);

        if (saveFileName != null) {
            adminAssignmentDto.setFile(saveFileName);
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
    public Object SetAdminActive(@RequestParam("no") int no){
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
    public String getAssignment(HttpSession session,
                                Model model,
                                AdminAssignmentDto adminAssignmentDto) {
        System.out.println("[OrganizerController] getAssignment()");

        // 세션 확인
        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");
        if (loginedAdminDto == null) {
            return "redirect:/";

        }

        adminAssignmentDto = adminAssignmentService.getAssignment(adminAssignmentDto);

        model.addAttribute("adminAssignmentDto", adminAssignmentDto);

        return nextPage;
    }


    
    
    /*
     * 과제 수정
     */

    
    
    /*
     * 과제 삭제(본인만)
     */



    // -------------------------------------------------------------------------------------------
    /*
     * 과제 제출 학생 리스트
     */


    /*
     * 과제 다운로드
     */


    /*
     * 과제 점수 등록
     */


    /*
     * 과제 점수 수정
     */


    /*
     * 과제 미제출 학생 메일 보내기
     */

    
}
