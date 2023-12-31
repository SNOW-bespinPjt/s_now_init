package com.btc.snow.user.member;

import com.btc.snow.user.config.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class UserMemberService implements IUserMemberService {

    final static public int DATABASE_COMMUNICATION_TROUBLE = -1;
    final static public int INSERT_FAIL_AT_DATABASE = 0;
    final static public int INSERT_SUCCESS_AT_DATABASE = 1;
    final static public int INSERT_DUPLICATE_ID_AT_DATABASE = -2;

    @Autowired
    IUserMemberDaoMB iUserMemberDaoMB;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Override
    public int createAccountConfirm(UserMemberDto userMemberDto) {
        log.info("[UserMemberService] createAccountConfirm()");

        /*
         * 아이디 중복 여부 확인
         */
        boolean isUser = iUserMemberDaoMB.isUser(userMemberDto.getId());

        if (!isUser) {
            userMemberDto.setPw(passwordEncoder.encode(userMemberDto.getPw()));            // 비밀번호 암호화 작업
            int result = iUserMemberDaoMB.insertUserMember(userMemberDto);

            switch (result) {
                case DATABASE_COMMUNICATION_TROUBLE:
                    log.info("[UserMemberService] DATABASE COMMUNICATION TROUBLE");
                    break;

                case INSERT_FAIL_AT_DATABASE:
                    log.info("[UserMemberService] INSERT FAIL AT DATABASE");
                    break;

                case INSERT_SUCCESS_AT_DATABASE:
                    log.info("[UserMemberService] INSERT SUCCESS AT DATABASE");
                    break;

            }

            return result;

        } else {
            return INSERT_DUPLICATE_ID_AT_DATABASE;

        }

    }

    @Override
    public UserMemberDto userLoginConfirm(UserMemberDto userMemberDto) {
        log.info("[UserMemberService] memberLoginConfirm()");

        UserMemberDto loginedUserDto = iUserMemberDaoMB.selectUserForLogin(userMemberDto);
        if (passwordEncoder.matches(userMemberDto.getPw(), loginedUserDto.getPw())) {
            return loginedUserDto;

        } else {
            loginedUserDto = null;
            return loginedUserDto;

        }

    }

    @Override
    public UserMemberDto userModifyConfirm(UserMemberDto userMemberDto) {
        log.info("[UserMemberService] userModifyConfirm()");

        int result = iUserMemberDaoMB.updateAccount(userMemberDto);
        if (result > 0) {
            return iUserMemberDaoMB.getLatestAccountInfo(userMemberDto);

        } else {
            return null;

        }

    }

    @Override
    public int userDeleteConfirm(int no) {
        log.info("[UserMemberService] userDeleteConfirm()");

        return iUserMemberDaoMB.deleteUser(no);

    }

    @Override
    public int findPasswordConfirm(UserMemberDto userMemberDto) throws MessagingException {
        log.info("[UserMemberService] findPasswordConfirm()");

        String newPassword = createNewPassword();

        if (newPassword != null) {
            emailService.sendMail(userMemberDto, newPassword);

            userMemberDto.setPw(passwordEncoder.encode(newPassword));

            int result = iUserMemberDaoMB.updateUserPW(userMemberDto);

            if (result <= 0) {
                log.info("[UserMemberService] UPDATE USER PASSWORD FAIL");
                return result;

            }

            log.info("[UserMemberService] UPDATE USER PASSWORD SUCCESS");
            return result;

        }

        return 0;

    }

    @Override
    public int uploadUserImg(UserMemberDto userMemberDto) {
        log.info("uploadUserImg()!!!");

        return iUserMemberDaoMB.uploadUserImg(userMemberDto);
    }

    // BTC 코인 순위
    @Override
    public List<UserMemberDto> coinRanking() {
        log.info("[UserMemberService] coinRanking()");

        return iUserMemberDaoMB.selectCoinRanking();
    }

    private String createNewPassword() {
        log.info("[UserMemberService] createNewPassword()");

        char[] chars = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'
        };

        StringBuffer stringBuffer = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(new Date().getTime());

        int index = 0;
        int length = chars.length;
        for (int i = 0; i < 8; i++) {
            index = secureRandom.nextInt(length);

            if (index % 2 == 0) {
                stringBuffer.append(String.valueOf(chars[index]).toUpperCase());

            } else {
                stringBuffer.append(String.valueOf(chars[index]).toLowerCase());

            }

        }

        log.info("[UserMemberService] NEW PASSWORD: " + stringBuffer.toString());

        return stringBuffer.toString();

    }

}
