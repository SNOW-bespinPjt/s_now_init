package com.btc.snow.admin.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class AdminMemberService implements IAdminService {

    @Autowired
    IAdminDaoMB iAdminDaoMB;

    @Autowired
    JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
    final static public int ADMIN_SUCCESS = 1;
    final static public int ADMIN_FAIL = -1;

    // 회원가입
    @Override
    public int createAccountConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberService] createAccountForm()");

        log.info("---->" + adminMemberDto.getA_m_id());

        log.info("isAdminMember()");
        boolean isMember = iAdminDaoMB.isAdminMember(adminMemberDto.getA_m_id());

        int a_m_approval = 0;
        if (adminMemberDto.getA_m_id().equals("super admin")) {
            a_m_approval = 1;

        }

        if (!isMember) {
            adminMemberDto.setA_m_pw(passwordEncoder.encode(adminMemberDto.getA_m_pw()));
            adminMemberDto.setA_m_approval(a_m_approval);
            log.info("insertAdminAccount()");

            int result = iAdminDaoMB.insertAdminAccount(adminMemberDto);

            if (result > 0) {
				return ADMIN_SUCCESS;

			} else {
				return ADMIN_FAIL;

			}

        } else {
            return ADMIN_ACCOUNT_ALREADY_EXIST;

        }
    }
    
    // 로그인
    @Override
    public AdminMemberDto loginConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberService] loginConfirm()");

        AdminMemberDto loginedAdminDto = iAdminDaoMB.selectAdminForLogin(adminMemberDto.getA_m_id());

        if (loginedAdminDto != null && passwordEncoder.matches(adminMemberDto.getA_m_pw(), loginedAdminDto.getA_m_pw())) {
            log.info("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!!");

			return loginedAdminDto;

        } else {
            log.info("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");

			return null;

        }

    }

    // 수정
    @Override
    public int modifyAccountConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberService] adminMemberDto()");

        adminMemberDto.setA_m_pw(passwordEncoder.encode(adminMemberDto.getA_m_pw()));

        return iAdminDaoMB.updateAdminAccount(adminMemberDto);

    }

    // 관리자 확인
    @Override
    public AdminMemberDto getLoginedAdminMember(int a_m_no) {
        log.info("[AdminMemberService] getLoginedAdminMember()");

        return iAdminDaoMB.selectLoginedAdmin(a_m_no);

    }

    // 관리자 리스트
    @Override
    public List<AdminMemberDto> listupAdmin() {
        log.info("[AdminMemberService] listupAdmin()");

        return iAdminDaoMB.selectAdmins();

    }

    // 관리자 권한 설정
    @Override
    public int setAdminApproval(int a_m_no) {
        log.info("[AdminMemberService] setAdminApproval()");

        int result = iAdminDaoMB.updateAdminApproval(a_m_no);

        if (result > 0) {
			log.info("ADMIN MEMBER APPROVAL SUCCESS!!");

		} else {
			log.info("ADMIN MEMBER APPROVAL FAIl!!");
		}

        return result;
    }

    // 비밀번호 찾기
    @Override
    public int findPasswordConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberService] findPasswordConfirm()");

        Map<String, Object> map = new HashMap<>();
        map.put("a_m_id", adminMemberDto.getA_m_id());
        map.put("a_m_name", adminMemberDto.getA_m_name());
        map.put("a_m_mail", adminMemberDto.getA_m_mail());

        AdminMemberDto selectedAdminMemberDto = iAdminDaoMB.selectAdminForFindPassword(map);

        int result = 0;

        if (selectedAdminMemberDto != null) {

            String newPassword = createNewPassword();

            result = iAdminDaoMB.updatePassword(adminMemberDto.getA_m_id(), passwordEncoder.encode(newPassword));

            if (result > 0) {
                sendNewPasswordByMail(adminMemberDto.getA_m_mail(), newPassword);

            }

        }

        return result;

    }

    // 임시 비밀번호 만들기
    @Override
    public String createNewPassword() {
        log.info("[AdminMemberService] createNewPassword()");

        char[] chars = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuffer stringBuffer = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(new Date().getTime());

        int index = 0;
        int length = chars.length;
        for (int i = 0; i < 8; i++) {
            index = secureRandom.nextInt(length);

            if (index % 2 == 0)
            {
                stringBuffer.append(String.valueOf(chars[index]).toUpperCase());

            } else {
                stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
                
            }

        }

        log.info("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());

        return stringBuffer.toString();

    }

    // 새 비밀번호 메일 전송
    @Override
    public void sendNewPasswordByMail(String toMailAddr, String newPassword) {
        log.info("[AdminMemberService] sendNewPasswordByMail()");

        final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(jakarta.mail.internet.MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo("iolagvi28@gmail.com");    // 수신자 메일 주소
                mimeMessageHelper.setSubject("[TEST] 새 비밀번호 안내 메일입니다.");
                mimeMessageHelper.setText("새 비밀번호: " + newPassword);
            }

        };

        javaMailSenderImpl.send(mimeMessagePreparator);

    }

    // 회원탈퇴
    @Override
    public int SignOutConfirm(int a_m_no) {
        log.info("[AdminMemberService] SignOutConfirm()");

        int result = iAdminDaoMB.deleteAdmin(a_m_no);

        switch (result) {
            case ADMIN_FAIL:
                System.out.println("[AdminMemberService] DELETE_FAIL_AT_DATABASE");

                break;

            case ADMIN_SUCCESS:
                System.out.println("[AdminMemberService] DELETE_SUCCESS_AT_DATABASE");

                break;
        }

        return result;

    }
}
