package com.btc.snow.admin.member;

import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

    @Autowired
    AdminMemberService adminMemberService;

    /*
     * 회원가입
     */
    @GetMapping("/create_account_form")
    public String createAccountForm() {
        log.info("[AdminMemberController] createAccountForm()");

        String nextPage = "admin/member/create_account_form";

        return nextPage;

    }

    /*
     * 회원가입승인
     */
    @PostMapping("/create_account_confirm")
    public String createAccountConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberController] createAccountConfirm()");

        String nextPage = "admin/member/success";

        int result = adminMemberService.createAccountConfirm(adminMemberDto);
        if (result <= 0)
            nextPage = "admin/member/fail";

        return nextPage;
    }

    /*
     * 로그인
     */
    @GetMapping("/member_login_form")
    public String loginForm() {
        log.info("[AdminMemberController] loginForm()");

        String nextPage = "admin/member/member_login_form";

        return nextPage;

    }

    /*
     * 로그인승인
     */
    @PostMapping("/member_login_confirm")
    public String loginConfirm(AdminMemberDto adminMemberDto, HttpSession session, Model model) {
        log.info("[AdminMemberController] loginConfirm()");

        String nextPage = "redirect:/admin";

        AdminMemberDto loginedAdminDto = adminMemberService.loginConfirm(adminMemberDto);

        if (loginedAdminDto == null) {
            nextPage = "admin/member/fail";

        } else {
            session.setAttribute("loginedAdminDto", loginedAdminDto);
            session.setMaxInactiveInterval(60 * 30);

            log.info("로그인한 ID : " + loginedAdminDto.getId());
        }

        return nextPage;

    }

    /*
     * 비밀번호 찾기
     */
    @GetMapping("/find_password_form")
    public String findPasswordForm() {
        log.info("[AdminMemberController] findPasswordForm()");

        String nextPage = "admin/member/find_password_form";

        return nextPage;

    }

    /*
     * 비밀번화 찾기 확인
     */
    @PostMapping("/find_password_confirm")
    public String findPasswordConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberController] findPasswordConfirm()");

        String nextPage = "admin/member/success";

        int result = adminMemberService.findPasswordConfirm(adminMemberDto);
        if (result <= 0)
            nextPage = "admin/member/fail";

        return nextPage;

    }

    /*
     * 아이디 찾기
     */
    @GetMapping("/find_id_form")
    public String findIdForm() {
        log.info("[AdminMemberController] findPasswordForm()");

        String nextPage = "admin/member/find_id_form";

        return nextPage;

    }

    /*
     * 아이디 찾기 확인
     */
    @PostMapping("/find_id_confirm")
    public String findIdConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberController] findPasswordConfirm()");

        String nextPage = "admin/member/success";

        int result = adminMemberService.findIdConfirm(adminMemberDto);
        if (result <= 0)
            nextPage = "admin/member/fail";

        return nextPage;

    }

    /*
     * 로그아웃 확인
     */
    @GetMapping("/member_logout_confirm")
    public String logoutConfirm(HttpSession session) {
        log.info("[AdminMemberController] logoutConfirm()");

        String nextPage = "redirect:/admin";

        session.removeAttribute("loginedAdminDto");

        return nextPage;

    }

    /*
     * 계정 수정
     */
    @GetMapping("/member_modify_form")
    public String modifyAccountForm(HttpSession session) {
        log.info("[AdminMemberController] modifyAccountForm()");

        String nextPage = "admin/member/member_modify_form";

        AdminMemberDto loginedAdminDto =
                (AdminMemberDto) session.getAttribute("loginedAdminDto");
        if (loginedAdminDto == null)
            nextPage = "redirect:/admin/member/loginForm";

        return nextPage;
    }

    /*
     * 계정수정확인
     */
    @PostMapping("/member_modify_confirm")
    public String modifyAccountConfirm(AdminMemberDto adminMemberDto, HttpSession session) {
        log.info("[AdminMemberController] modifyAccountConfirm()");

        String nextPage = "admin/member/success";

        log.info("modift pw -------------->" + adminMemberDto.getPw());
        log.info("modift name -------------->" + adminMemberDto.getName());

        int result = adminMemberService.modifyAccountConfirm(adminMemberDto);

        if (result > 0) {
            AdminMemberDto loginedAdminDto =
                    adminMemberService.getLoginedAdminMember(adminMemberDto.getNo());

            session.setAttribute("loginedAdminDto", loginedAdminDto);
            session.setMaxInactiveInterval(60 * 30);

        } else {
            nextPage = "admin/member/fail";

        }

        return nextPage;

    }

    /*
     * 관리자 리스트
     */
    @GetMapping("/list_admins")
    public ModelAndView listupAdmin() {
        log.info("[AdminMemberController] listupAdmin()");

        String nextPage = "admin/member/list_admins";

        List<AdminMemberDto> adminMemberDtos = adminMemberService.listupAdmin();

        ModelAndView mv = new ModelAndView();                               // 1)객체 생성
        mv.setViewName(nextPage);                                           // 2)뷰 설정
        mv.addObject("adminMemberDtos", adminMemberDtos);       // 3) 데이터 추가

        return mv;                                                          // 4) MV 반환

    }

    /*
     * 학생 리스트
     */
    @GetMapping("/list_users")
    public ModelAndView listupUser() {
        log.info("[AdminMemberController] listupUser()");

        String nextPage = "admin/member/list_users";

        List<UserMemberDto> userMemberDtos = adminMemberService.listupUser();

        ModelAndView mv = new ModelAndView();                               // 1)객체 생성
        mv.setViewName(nextPage);                                           // 2)뷰 설정
        mv.addObject("userMemberDtos", userMemberDtos);       // 3) 데이터 추가

        return mv;                                                          // 4) MV 반환

    }

    /*
     * 학생 디테일 페이지
     */
    @GetMapping("/user_detail")
    public ModelAndView UserDetail(@RequestParam("no") int no) {
        log.info("[AdminMemberController] UserDetail()");

        String nextPage = "admin/member/user_detail";

        UserMemberDto userMemberDto = adminMemberService.UserDetail(no);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(nextPage);
        mv.addObject("userMemberDto", userMemberDto);

        return mv;

    }
    
    
    /*
     * 관리자 승인 처리
     */
    @GetMapping("/set_admin_approval")
    @ResponseBody
    public Object setAdminApproval(@RequestParam("no") int no) {
        log.info("[AdminMemberController] setAdminApproval()");

        log.info("no: " + no);

        Map<String, Object> map = new HashMap<>();

        int result = adminMemberService.setAdminApproval(no);

        if (result > 0) {
            map.put("result", result);

        } else {
            map.put("error", "Invalid credentials");
        }

        return map;
    }


    /*
     * 회원 탈퇴
     */
    @PostMapping("/member_signout_confirm")
    public Object SignOutConfirm(HttpSession session) {
        log.info("[AdminController] SignOutConfirm()");

        String nextPage = "admin/member/modify_account_form";

        Map<String, Object> map = new HashMap<>();

        AdminMemberDto loginedAdminDto = (AdminMemberDto) session.getAttribute("loginedAdminDto");

        if (loginedAdminDto != null) {
            int status = adminMemberService.SignOutConfirm(loginedAdminDto.getNo());
            if (status > 0) {
                log.info("[AdminController] SIGNOUT SUCCESS!!");
                session.removeAttribute("loginedAdminDto");

                nextPage = "admin/member/success";

            } else {
                log.info("[AdminController] SIGNOUT FAIL!!");

                nextPage = "admin/member/fail";
            }

        }
        return nextPage;
    }

}
