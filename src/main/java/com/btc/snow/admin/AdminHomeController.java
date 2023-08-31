package com.btc.snow.admin;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminHomeController {

//   @Autowired
//   IMemberDaoMB iMemberDaoMB;
   
   @GetMapping(value = {"", "/"})
   public String home(@AuthenticationPrincipal User user, HttpSession session) {
      log.info("[HomeController] home()");

//      if (user != null){ //로그인이 겠지
//         MemberDto memberDto = new MemberDto();
//         memberDto.setM_id(user.getUsername());
//         memberDto.setM_pw(user.getPassword());
//
//         MemberDto loginedAdminDto = iMemberDaoMB.selectMemberForLogin(memberDto);  //이거에 맞는 거 줘봐
//         session.setAttribute("loginedAdminDto", loginedAdminDto);
//         session.setMaxInactiveInterval(60 * 30);
//
//      }

//      return "admin/home";
      return "admin/home";
   }
}