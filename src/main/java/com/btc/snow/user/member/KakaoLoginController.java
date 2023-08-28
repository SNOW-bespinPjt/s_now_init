package com.btc.snow.user.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/kakao")
public class KakaoLoginController {

    @GetMapping
    public String kakaoLogin() {
        return "kakaoLogin";
    }
}
