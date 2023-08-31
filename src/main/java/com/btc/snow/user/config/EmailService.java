package com.btc.snow.user.config;


import com.btc.snow.user.member.UserMemberDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class EmailService {

    private JavaMailSender emailSender;

    public void sendMail(UserMemberDto userMemberDto, String newPassword) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        log.info(userMemberDto.getU_m_mail());
        //제목, 내용 설정
        helper.setSubject("SEND EMAIL CODE");
        helper.setText(newPassword, false);

        // 참조자 설정
        helper.setCc("hyunuk1459@gmail.com");

        // 발신자 설정(연동된 구글 계정으로 고정)
         helper.setFrom("hyunuk1459@gmail.com");

        //메일 전송(setTo 파라미터에 문자열 리스트를 넘기면 한번에 여러명에게 전송 가능)
        helper.setTo("hyunuk1459@gmail.com");
        emailSender.send(message);

    }

}


