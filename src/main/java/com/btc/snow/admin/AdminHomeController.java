package com.btc.snow.admin;

import com.btc.snow.admin.member.AdminMemberService;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminHomeController {

   @Autowired
   AdminMemberService adminMemberService;
   
   @GetMapping(value = {"", "/"})
   public String home(Model model) {
      log.info("[HomeController] home()");

      // BTC 코인 순위
      List<UserMemberDto> userMemberDtos = adminMemberService.coinRanking();
      model.addAttribute("userMemberDtos", userMemberDtos);

      return "/admin/home";
   }
}