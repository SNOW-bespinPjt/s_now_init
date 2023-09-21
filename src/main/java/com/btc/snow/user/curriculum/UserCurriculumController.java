package com.btc.snow.user.curriculum;


import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Log4j2
@Controller
@RequestMapping("/user/curriculum")
public class UserCurriculumController {

    // 생성자 주입
    private UserCurriculumService userCurriculumService;

    public UserCurriculumController(UserCurriculumService userCurriculumService) {
        this.userCurriculumService = userCurriculumService;

    }

    /*
     * MOVE CURRICULUM FORM
     */
    @GetMapping("")
    public String curriculumForm() {
        log.info("[UserCurriculumController] curriculumForm()");

        String nextPage = "/user/curriculum/user_curriculum_form";

        return nextPage;

    }

    /*
     * SHOW ALL CURRICULUM
     */
    @PostMapping("/show_all_curriculum")
    @ResponseBody
    public Map<String, Object> showAllCurriculum() {
        log.info("[UserCurriculumController] showAllCurriculum()");

        return userCurriculumService.showAllCurriculum();

    }

    /*
     * SHOW DETAIL CURRICULUM
     */
    @GetMapping("/show_detail_curriculum")
    public String showDetailCurriculum(@RequestParam("no") int no, Model model) {
        log.info("[UserCurriculumController] showDetailCurriculum()");

        String nextPage = "/user/curriculum/user_curriculum_detail_form";

        model.addAttribute("msgMap", userCurriculumService.showDetailCurriculum(no));

        return nextPage;

    }

    /*
     * SEARCH TITLE WORD
     */
    @PostMapping("/search_title_confirm")
    @ResponseBody
    public Map<String, Object> searchTitleConfirm(@RequestParam("search_title") String search_title, HttpSession session) {
        log.info("[UserCurriculumController] searchTitleConfirm()");

        return userCurriculumService.searchTitleConfirm(search_title);

    }

    /*
     * EVALUATE CURRICULUM GRADE
     */
//    @GetMapping("/evaluate_curriculum_grade/{curriculum_no}")
//    @ResponseBody
//    public void evaluateCurriculumGrade(
//            @PathVariable("curriculum_no") int curriculum_no,
//            HttpSession session) {
//        log.info("[UserCurriculumController] evaluateCurriculumGrade()");
//
//        AdminMemberDto loginedUserDto = (AdminMemberDto) session.getAttribute("loginedUserDto");
//
////        userCurriculumService.evaluateCurriculumConfirm(grade, curriculum_no, loginedUserDto);
//
//    }

}
