package com.btc.snow.admin.assignment;

import com.btc.snow.admin.member.AdminMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Log4j2
@RequestMapping("/admin/assignment")
@Controller
public class AdminAssignmentController {

    @Autowired
    AdminAssignmentService adminAssignmentService;

    @Autowired
    UploadFileService uploadFileService;

    String nextPage = null;

    // alert


    /*
     * 과제 리스트(과제 페이지)
     */
    @GetMapping("/list")
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
                                      @RequestParam("file_admin") MultipartFile file){
        log.info("[AdminAssignmentController] RegistrationConfirm()");

        nextPage = "redirect:/admin/assignment/list";

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

                nextPage = "redirect:/";

        } else {

            nextPage = "redirect:/";

        }

        return nextPage;

    }


}
