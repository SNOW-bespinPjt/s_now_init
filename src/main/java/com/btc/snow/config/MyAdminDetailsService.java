package com.btc.snow.config;


import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.admin.member.IAdminDaoMB;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MyAdminDetailsService implements UserDetailsService {

    @Autowired
    private IAdminDaoMB iAdminDaoMB;

    public MyAdminDetailsService(IAdminDaoMB iAdminDaoMB) {
        this.iAdminDaoMB = iAdminDaoMB;

    }

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        log.info("loadUserByUsername()");

        log.info("adminname: " + adminName);

        AdminMemberDto adminMemberDto = new AdminMemberDto();
        adminMemberDto.setId(adminName);

        AdminMemberDto selectedAdminMemberDto = iAdminDaoMB.selectAdminForLogin(adminMemberDto.getId());

        return User.builder()
                .username(selectedAdminMemberDto.getId())
                .password(selectedAdminMemberDto.getPw())
                .roles("ADMIN")
                .build();

    }

}