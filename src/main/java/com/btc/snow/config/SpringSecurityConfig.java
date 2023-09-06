package com.btc.snow.config;


import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.admin.member.IAdminDaoMB;
import com.btc.snow.user.member.IUserMemberDaoMB;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private IUserMemberDaoMB iUserMemberDaoMB;
    private IAdminDaoMB iAdminDaoMB;
    private MyUserDetailsService myUserDetailsService;
    private MyAdminDetailsService myAdminDetailsService;

    public SpringSecurityConfig(IUserMemberDaoMB iUserMemberDaoMB,
                                IAdminDaoMB iAdminDaoMB,
                                MyUserDetailsService myUserDetailsService,
                                MyAdminDetailsService myAdminDetailsService) {

        this.iUserMemberDaoMB = iUserMemberDaoMB;
        this.iAdminDaoMB = iAdminDaoMB;
        this.myUserDetailsService = myUserDetailsService;
        this.myAdminDetailsService = myAdminDetailsService;

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainForUser(HttpSecurity http) throws Exception {
        log.info("filterChain");

        http.csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()  // HTTP 요청 인증 설정
                        .requestMatchers("/css/**", "/error/**", "/img/**", "/js/**", "", "/",
                                "/user/member/create_account_form",
                                "/user/member/create_account_confirm",
                                "/UploadImg/**").permitAll()
                        .anyRequest().authenticated()  // 해당 경로 외의 요청은 모두 인증 필요
                )
                .formLogin(login -> login  // 로그인 시 폼(form)을 이용
                        .loginPage(
                                "/user/member/user_login_form"
                        )  // 로그인 시 폼(form) 주소 설정
                        .loginProcessingUrl("/user/member/user_login_confirm")
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .successHandler((request, response, authentication) -> {  // 로그인 성공 시(추가 구현 예정: 로그인 페이지로 오기 이전에 있던 페이지로 이동)
                            log.info("successHandler!!");

                            UserMemberDto userMemberDto = new UserMemberDto();
                            userMemberDto.setId(authentication.getName());
                            UserMemberDto loginedUserDto = iUserMemberDaoMB.selectUserForLogin(userMemberDto);

                            HttpSession session = request.getSession();
                            session.setAttribute("loginedUserDto", loginedUserDto);
                            session.setMaxInactiveInterval(60 * 30);

                            log.info("--> {}", authentication.isAuthenticated());

                            response.sendRedirect("/");

                        })
                        .failureHandler((request, response, exception) -> {      //로그인 실패 시(추가 구현 예정: 아이디 혹은 비밀번호를 다시 확인해주세요 알림)
                            log.info("failureHandler!!");
                            response.sendRedirect("/user/member/user_login_form");

                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/user/member/user_logout_confirm")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            log.info("logoutSuccessHandler!!");

                            HttpSession session = request.getSession();
                            session.invalidate();   //세션 데이터 삭제

                            response.sendRedirect("/");

                        })
                )
                .userDetailsService(myUserDetailsService)   //사용자 정보 들고옴
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);

        return http.build();

    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainForAdmin(HttpSecurity http) throws Exception {
        log.info("filterChainForAdmin");

        http.csrf().disable()
                .cors().disable()
                .securityMatcher("/admin/**")  // "/admin/**" 경로 보안 설정
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/css/**", "/error/**", "/img/**", "/js/**", "", "/",
                                "/admin/member/create_account_form", "/admin/member/create_account_confirm").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/admin/member/member_login_form")
                        .loginProcessingUrl("/admin/member/member_login_confirm")
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .successHandler((request, response, authentication) -> {
                            log.info("successHandler!!");

                            AdminMemberDto adminMemberDto = new AdminMemberDto();
                            adminMemberDto.setId(authentication.getName());
                            AdminMemberDto loginedAdminDto = iAdminDaoMB.selectAdminForLogin(adminMemberDto.getId());

                            HttpSession session = request.getSession();
                            session.setAttribute("loginedAdminDto", loginedAdminDto);
                            session.setMaxInactiveInterval(60 * 30);

                            log.info("--> {}", authentication.isAuthenticated());

                            response.sendRedirect("/admin/");

                        })
                        .failureHandler((request, response, exception) -> {
                            log.info("failureHandler!!");
                            response.sendRedirect("/admin/member/member_login_form");

                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/admin/member/member_logout_confirm")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            log.info("logoutSuccessHandler!!");

                            HttpSession session = request.getSession();
                            session.invalidate();

                            response.sendRedirect("/admin/");

                        })
                )
                .userDetailsService(myAdminDetailsService)
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);

        return http.build();

    }

}
