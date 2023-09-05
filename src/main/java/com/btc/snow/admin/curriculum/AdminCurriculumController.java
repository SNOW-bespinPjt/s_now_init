package com.btc.snow.admin.curriculum;


import com.btc.snow.admin.member.AdminMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * MOVE CURRICULUM FORM
     */
    @GetMapping("")
    public String curriculumForm() {
        log.info("[AdminCurriculumController] curriculumForm()");

        String nextPage = "admin/curriculum/curriculum_form";

        return nextPage;

    }

    /*
     * MOVE CREATE CURRICULUM FORM
     */
    @GetMapping("/create_curriculum_form")
    public String createCurriculumForm() {
        log.info("[AdminCurriculumController] createCurriculumForm()");

        String nextPage = "admin/curriculum/create_curriculum_form";

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
    //path 변수에 넣는게 좋다.
    @PostMapping("/show_all_curriculum")
    @ResponseBody
    public Map<String, Object> showAllCurriculum(HttpSession session) {
        log.info("[AdminCurriculumController] showAllCurriculum()");

        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");

        return adminCurriculumService.showAllCurriculum(loginedAdminDto);

    }

    /*
     * SHOW DETAIL CURRICULUM
     */
    @GetMapping("/show_detail_curriculum")
    public String showDetailCurriculum(@RequestParam("no") int no, Model model) {
        log.info("[AdminCurriculumController] showDetailCurriculum()");

        String nextPage = "admin/curriculum/curriculum_detail_form";

        model.addAttribute("msgMap", adminCurriculumService.showDetailCurriculum(no));

        return nextPage;

    }

    /*
     * MODIFY CURRICULUM FORM
     */
    @GetMapping("/modify_curriculum_form")
    public String modifyCurriculumForm() {
        log.info("[AdminCurriculumController] modifyCurriculumForm()");

        String nextPage = "";

        return nextPage;

    }

    /*
     * MODIFY CURRICULUM CONFIRM
     */
    @PostMapping("/modify_curriculum_confirm")
    public int modifyCurriculumConfirm(AdminCurriculumDto adminCurriculumDto) {
        log.info("[AdminCurriculumController] modifyCurriculumConfirm()");

        return adminCurriculumService.modifyCurriculumConfirm(adminCurriculumDto);

    }

    /*
     * DELETE CURRICULUM CONFIRM
     */
    @GetMapping("/delete_curriculum_confirm")
    public String deleteCurriculumConfirm(@RequestParam("no") int no) {
        log.info("[AdminCurriculumController] deleteCurriculumConfirm()");

        int result = adminCurriculumService.deleteCurriculumConfirm(no);

        String nextPage = "admin/curriculum/curriculum_form";

        if (result > 0) {
            return nextPage;

        }

        nextPage = "admin/curriculum/curriculum_detail_form";
        return nextPage;

    }

    /*
     * SEARCH TITLE WORD
     */
    @PostMapping("/search_title_confirm")
    @ResponseBody
    public Map<String, Object> searchTitleConfirm(@RequestParam("search_title") String search_title, HttpSession session) {
        log.info("[AdminCurriculumController] searchTitleConfirm()");

        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");

        return adminCurriculumService.searchTitleConfirm(search_title, loginedAdminDto);

    }

}
