package com.btc.snow.config;


import com.btc.snow.user.member.IUserMemberDaoMB;
import com.btc.snow.user.member.UserMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MyUserDetailsService implements UserDetailsService {

    private IUserMemberDaoMB iUserMemberDaoMB;

    public MyUserDetailsService(IUserMemberDaoMB iUserMemberDaoMB) {
        this.iUserMemberDaoMB = iUserMemberDaoMB;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername()");

        UserMemberDto userMemberDto = new UserMemberDto();
        //Service에서 iUserMemberDaoMapper에 Dto로 넘겨주니 타입 일치화
        userMemberDto.setId(username);

        UserMemberDto selectedUserMemberDto = iUserMemberDaoMB.selectUserForLogin(userMemberDto);

        return User.builder()
                .username(selectedUserMemberDto.getId())
                .password(selectedUserMemberDto.getPw())
                .roles("USER")
                .build();

    }

}