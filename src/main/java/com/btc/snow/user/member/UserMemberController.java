package com.btc.snow.user.member;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String createAccountConfirm(UserMemberDto userMemberDto) {
		log.info("[UserMemberController] createAccountConfirm()");

		String nextPage = "user/member/create_account_success";
		
		int result = userMemberService.createAccountConfirm(userMemberDto);
		
		if (result <= UserMemberService.INSERT_FAIL_AT_DATABASE)
			nextPage = "user/member/create_account_fail";
			
		return nextPage;
		
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
	@PostMapping("/user_login_confirm")
	public String userLoginConfirm(UserMemberDto userMemberDto, HttpSession session) {
		log.info("[UserMemberController] userLoginConfirm()");

		String nextPage = "home";

		UserMemberDto loginedUserDto = userMemberService.userLoginConfirm(userMemberDto);

		if(loginedUserDto != null) {
			session.setAttribute("loginedUserDto", loginedUserDto);
			session.setMaxInactiveInterval(60 * 30);

		}

		return nextPage;

	}

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

	/*
    LOGOUT CONFIRM
     */
	@GetMapping("/user_logout_confirm")
	public String userLogoutConfirm(HttpSession session) {
		log.info("[UserMemberController] user_logout_confirm()");

		String nextPage = "redirect:/";

		session.removeAttribute("loginedUserDto");

		return nextPage;
	}

	/*
	 * USER DELETE CONFIRM
	 */
	@GetMapping("/user_delete_confirm")
	public String userDeleteConfirm(HttpSession session) {
		log.info("[UserMemberController] userDeleteConfirm()");

		String nextPage = "redirect:/";

		UserMemberDto loginedUserDto =
				(UserMemberDto) session.getAttribute("loginedUserDto");

		int result = userMemberService.userDeleteConfirm(loginedUserDto.getU_m_no());

		if (result > 0) {
			session.removeAttribute("loginedUserDto");

		} else {
			nextPage = "member/member_delete_fail";
		}


		return nextPage;
	}




//	/*
//	 * 비밀번호 찾기
//	 */
//	@GetMapping("/findPasswordForm")
//	public String findPasswordForm() {
//		System.out.println("[AdminMemberControlle r] findPasswordForm()");
//
//		String nextPage = "user/member/find_password_form";
//
//		return nextPage;
//	}

	/*
	 * 비밀번호 찾기 확인
	 */
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(UserMemberDto userMemberDto) throws MessagingException {
		System.out.println("[AdminMemberController] findPasswordConfirm()");

		String nextPage = "user/member/find_password_success";

		int result = userMemberService.findPasswordConfirm(userMemberDto);

		if(result <= 0)
			nextPage = "admin/member/find_password_fail";

		return nextPage;

	}
	

	
}
