package com.btc.snow.admin.curriculum;


import com.btc.snow.admin.member.AdminMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Log4j2
@Controller
@RequestMapping("/admin/curriculum")
public class AdminCurriculumController {

    // 생성자 주입
    private AdminCurriculumService adminCurriculumService;

    public AdminCurriculumController(AdminCurriculumService adminCurriculumService) {
        this.adminCurriculumService = adminCurriculumService;

    }

    /*
     * MOVE CREATE CURRICULUM FORM
     */
    @GetMapping("/create_curriculum_form")
    public String createCurriculumForm() {
        log.info("[AdminCurriculumController] createCurriculumForm()");

        String nextPage = "admin/curriculum";

        return nextPage;

    }

    /*
     * CREATE CURRICULUM CONFIRM
     */
    @PostMapping("/create_curriculum_confirm")
    @ResponseBody
    public String createCurriculumConfirm(AdminCurriculumDto adminCurriculumDto, Model model, HttpSession session) {
        log.info("[AdminCurriculumController] createCurriculumConfirm()");

        String nextPage = "admin/curriculum";

        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");

        adminCurriculumDto.setAdmin_id(loginedAdminDto.getId());

        // result를 보내 응답 알람 띄우기 START
        int result = adminCurriculumService.createCurriculumConfirm(adminCurriculumDto);

        model.addAttribute("result", result);
        return nextPage;

        // result를 보내 응답 알람 띄우기 END

    }

    /*
     * SHOW ALL CURRICULUM WHERE ADMIN_ID or SUPER ADMIN
     */
    @GetMapping("/show_all_curriculum")
    @ResponseBody
    public Map<String, Object> showAllCurriculum(HttpSession session) {
        log.info("[AdminCurriculumController] showAllCurriculum()");

        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");

        return adminCurriculumService.showAllCurriculum(loginedAdminDto);

    }


}
