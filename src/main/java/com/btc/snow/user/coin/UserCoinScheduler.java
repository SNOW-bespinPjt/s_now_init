/*
package com.btc.snow.user.coin;

import com.btc.snow.user.assignment.IUserAssignmentMB;
import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.attendance.UserAttendanceDto;
import com.btc.snow.user.attendance.UserAttendanceMapper;
import com.btc.snow.user.meeting.study.IUserStudyMapper;
import com.btc.snow.user.meeting.study.UserStudyDto;
import com.btc.snow.user.member.IUserMemberDaoMB;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@EnableScheduling
@SpringBootApplication
public class UserCoinScheduler {

    @Autowired
    UserAttendanceMapper userAttendanceMapper;

    @Autowired
    IUserStudyMapper iUserStudyMapper;

    @Autowired
    IUserAssignmentMB iUserAssignmentMB;

    @Autowired
    IUserMemberDaoMB iUserMemberDaoMB;

    @Scheduled(cron = "59 59 23 * * *") // 매일 자정되기 전에 실행
    public void scheduledUpdateCoin(HttpSession session) {
        log.info("[UserCoinScheduler] scheduledUpdateCoin()");
        */
/*
        coin 정책
        출석 : 하루 최대 2점 (오전출석 1점, 오후출석 1점)
        스터디 : 개설 시 2점
        과제 : 점수에 따라 1, 3, 5점
        *//*


        // 현재 날짜
        LocalDate today = LocalDate.now();
        // 가져올 날짜
        String regDateWithoutTime = null;
        boolean areDatesEqual = false;

        // 포맷터를 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDate를 문자열로 변수
        String dateString = today.format(formatter);

        // 유저 정보 가져오기
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();
        String id = loginedUserDto.getId();

        // coin에 넣을 점수
        int coinByAttendenceTstatusis0 = 0;
        int coinByAttendenceTstatusis1 = 0;
        int coinByAttendence = coinByAttendenceTstatusis0 + coinByAttendenceTstatusis1;
        int coinByStudy = 0;
        int coinByAssignment = 0;
        int oriCoin = 0;
        int totalCoin = oriCoin + coinByAttendence + coinByStudy + coinByAssignment;
        int result = 0;

        // coinByAttendence값 구하기
        // 오늘 출석 리스트
        //
        UserAttendanceDto userAttendanceDto = userAttendanceMapper.coinByAttendence(id);
        // datetime > date
        regDateWithoutTime = userAttendanceDto.getReg_date().format(String.valueOf(formatter));
        areDatesEqual = regDateWithoutTime.equals(LocalDate.parse(dateString, formatter));

        // 오전 출석
        if (userAttendanceDto.getAstatus() == 0 && userAttendanceDto.getAstatus() == 0 && areDatesEqual) {
            coinByAttendenceTstatusis0 = 1;
        }
        // 오후 출석
        if (userAttendanceDto.getAstatus() == 0 && userAttendanceDto.getAstatus() == 1 && areDatesEqual) {
            coinByAttendenceTstatusis1 = 1;
        }

        // coinByStudy 구하기
        // 오늘 스터디 리스트
        List<UserStudyDto> userStudyDtos = iUserStudyMapper.coinByStudy(id);
        for (UserStudyDto userStudyDto : userStudyDtos) {
            // datetime > date
            regDateWithoutTime = userStudyDto.getReg_date().format(String.valueOf(formatter));
            areDatesEqual = userStudyDto.equals(LocalDate.parse(dateString, formatter));

            if (userStudyDto.getNo() != 0 && areDatesEqual) {
                coinByStudy = 2;

                break;
            }
        }

        // coinByAssignment 구하기
        // 오늘 제출 과제 리스트
        List<UserAssignmentDto> userAssignmentDtos = iUserAssignmentMB.coinByAssignment(user_no);
        for (UserAssignmentDto userAssignmentDto : userAssignmentDtos) {
            // datetime > date
            regDateWithoutTime = userAssignmentDto.getReg_date().format(String.valueOf(formatter));
            areDatesEqual = userAssignmentDto.equals(LocalDate.parse(dateString, formatter));

            if (userAssignmentDto.getIs_submit() == 1 && areDatesEqual) {
                coinByAssignment = userAssignmentDto.getPoint() == 0 ? 0 : userAssignmentDto.getPoint() < 5 ? 1 : userAssignmentDto.getPoint() < 9 ? 3 : 5;
            }
        }

        // 현재 coin값 구하기 = oriCoin
        oriCoin = iUserMemberDaoMB.selectCoin(id);

        // totalCoin 넣어주기
        result = iUserMemberDaoMB.updateCoin(id, totalCoin);

        // 결과 값 확인
        String msg = result > 0 ? "scheduledUpdateCoin 성공" : "scheduledUpdateCoin 실패";
        log.info(msg);
    }

}
*/
