package com.btc.snow.user.member;

import com.btc.snow.user.config.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

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
		boolean isUser = iUserMemberDaoMB.isUser(userMemberDto.getU_m_id());

		if (!isUser) {

			userMemberDto.setU_m_pw(passwordEncoder.encode(userMemberDto.getU_m_pw()));			// 비밀번호 암호화 작업
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
		if(passwordEncoder.matches(userMemberDto.getU_m_pw(),loginedUserDto.getU_m_pw())) {
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
		if(result > 0)
			return iUserMemberDaoMB.getLatestAccountInfo(userMemberDto);
		else
			return null;
	}

	@Override
	public int userDeleteConfirm(int u_m_no) {
		log.info("[UserMemberService] userDeleteConfirm()");

		return iUserMemberDaoMB.deleteUser(u_m_no);
	}

	@Override
	public int findPasswordConfirm(UserMemberDto userMemberDto) throws MessagingException {
		log.info("[UserMemberService] findPasswordConfirm()");

		String newPassword = createNewPassword();

		if(newPassword != null) {
			emailService.sendMail(userMemberDto, newPassword);
			return 1;
		}

		return 0;
    }

	private String createNewPassword() {
		System.out.println("[AdminMemberService] createNewPassword()");

		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
				'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y','z'
		};

		/*
		 * String -> 실제로는 ["H", "E", "L", "L", "O"] 이렇게 저장됨 근데 만약에 뒤에 값이 달라지면 뒤에 늘어나는게 아니라, 주소값을 바꾸고 hello를 복사해서 가져와서 붙임
		 * 하지만 StringBuffer는 바로 뒤에 붙이고 뺴고 하기 때문에 메모리 소모가 덜함 그래서 빠르다.
		 */

		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());

		int index = 0;
		int length =  chars.length;
		for (int i = 0; i < 8; i++) {

			index = secureRandom.nextInt(length);

			if (index % 2 == 0)
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
		}

		System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());

		return stringBuffer.toString();
	}


}
