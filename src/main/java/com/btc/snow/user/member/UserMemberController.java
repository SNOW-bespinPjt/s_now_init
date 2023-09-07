package com.btc.snow.user.member;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
@RequestMapping("/user/member")
public class UserMemberController {

    @Autowired
    UserMemberService userMemberService;

    /*
     * CREATE ACCOUT FORM
     */
    @GetMapping("/create_account_form")
    public String createAccountForm() {
        log.info("[UserMemberController] createAccountForm()");

        String nextPage = "user/member/create_account_form";

        return nextPage;

    }

    /*
     * CREATE ACCOUNT CONFIRM
     */
    @PostMapping("/create_account_confirm")
    public Object createAccountConfirm(UserMemberDto userMemberDto) {
        log.info("[UserMemberController] createAccountConfirm()");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/member/member_login_form");

        int result = userMemberService.createAccountConfirm(userMemberDto);

        if (result <= UserMemberService.INSERT_FAIL_AT_DATABASE) {
            modelAndView.setViewName("404");

        }

        return modelAndView;

    }

    /*
     USER LOGIN FORM
     */
    @GetMapping("/user_login_form")
    public String userLoginForm() {
        log.info("[UserMemberController] userLoginForm()");

        String nextPage = "user/member/member_login_form";

        return nextPage;

    }

    /*
     USER LOGIN CONFIRM
    */
//    @PostMapping("/user_login_confirm")
//    @ResponseBody
//    public ResponseEntity<Object> userLoginConfirm(UserMemberDto userMemberDto, HttpSession session) {
//        log.info("[UserMemberController] userLoginConfirm()");
//
//
//        HttpHeaders headers = new HttpHeaders();
//
//
//        UserMemberDto loginedUserDto = userMemberService.userLoginConfirm(userMemberDto);
//
//        if (loginedUserDto != null) {
//            log.info("Login Success!!");
//
//            session.setAttribute("loginedUserDto", loginedUserDto);
//            session.setMaxInactiveInterval(60 * 30);
//
//            headers.setLocation(URI.create("/"));
//
//            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//        } else {
//
//            log.info("Login Fail!!");
//
//            headers.setLocation(URI.create("404"));
//
//            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//        }
//
//
//    }

    /*
       USER MODIFY FORM
   */
    @GetMapping("/user_modify_form")
    public String userModifyForm() {
        log.info("[UserMemberController] userModifyForm()");

        String nextPage = "/user/member/member_modify_form";

        return nextPage;

    }

    /*
     USER MODIFY CONFIRM
    */
    @PostMapping("/user_modify_confirm")
    public String userModifyConfirm(UserMemberDto userMemberDto, HttpSession session) {
        log.info("[UserMemberController] userModifyConfirm()");

        String nextPage = "/user/member/member_modify_success";

        UserMemberDto loginedUserDto = userMemberService.userModifyConfirm(userMemberDto);
        if (loginedUserDto != null) {
            session.setAttribute("loginedUserDto", loginedUserDto);
            session.setMaxInactiveInterval(60 * 30);

        } else {
            nextPage = "/user/member/member_modify_fail";
        }

        return nextPage;

    }

//    /*
//    LOGOUT CONFIRM
//     */
//    @GetMapping("/user_logout_confirm")
//    public String userLogoutConfirm(HttpSession session) {
//        log.info("[UserMemberController] user_logout_confirm()");
//
//        String nextPage = "redirect:/";
//
//        session.removeAttribute("loginedUserDto");
//
//        return nextPage;
//    }

//		session.removeAttribute("loginedUserDto");
//
//		return nextPage;
//	}

    /*
     * USER DELETE CONFIRM
     */
    @GetMapping("/user_delete_confirm")
    public String userDeleteConfirm(HttpSession session) {
        log.info("[UserMemberController] userDeleteConfirm()");

        String nextPage = "redirect:/";

        UserMemberDto loginedUserDto =
                (UserMemberDto) session.getAttribute("loginedUserDto");

        int result = userMemberService.userDeleteConfirm(loginedUserDto.getNo());

        if (result > 0) {
            session.removeAttribute("loginedUserDto");

        } else {
            nextPage = "member/member_delete_fail";

        }

        return nextPage;

    }

    /*
     * FIND PASSWORD FORM
     */
    @GetMapping("/find_password_form")
    public String findPasswordForm() {
        log.info("[UserMemberController] findPasswordForm()");

        String nextPage = "user/member/find_password_form";

        return nextPage;

    }

    /*
     * FIND PASSWORD CONFIRM
     */
    @PostMapping("/find_password_confirm")
    public String findPasswordConfirm(UserMemberDto userMemberDto) throws MessagingException {
        log.info("[UserMemberController] findPasswordConfirm()");

        String nextPage = "user/member/find_password_success";

        int result = userMemberService.findPasswordConfirm(userMemberDto);

        if (result <= 0) {
            nextPage = "admin/member/find_password_fail";

        }

        return nextPage;

    }

    /*
     * FIND ID FORM
     */
//   @GetMapping("/find_id_form")
//   public String findIdForm() {
//      log.info("[UserMemberController] findIdForm()");
//
//      String nextPage = "user/member/find_id_form";
//
//      return nextPage;
//
//   }

    /*
     * FIND ID CONFIRM
     */
//   @PostMapping("/find_id_confirm")
//   public String findIdConfirm(UserMemberDto userMemberDto) throws MessagingException {
//      log.info("[UserMemberController] findIdConfirm()");
//
//      String nextPage = "user/member/find_id_success";
//
//      int result = userMemberService.findIdConfirm(userMemberDto);
//
//      if(result <= 0)
//         nextPage = "admin/member/find_id_fail";
//
//      return nextPage;
//
//   }

}