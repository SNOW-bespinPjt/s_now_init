package com.btc.snow.user.coin;

import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.attendance.UserAttendanceDto;
import com.btc.snow.user.member.UserMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class UserCoinSchedulerService {

    @Autowired
    IUserCoinSchedulerMB iUserCoinSchedulerMB;

    //스케쥴러 실행중인지 확인할 전역변수 설정
    public static boolean onScheduled = false;

    @Scheduled(cron = "0 0 2 * * *") // 모두가 자는 새벽 3시.. 체크를 합니다 ->
    public void scheduledUpdateCoin() {
        log.info("[UserCoinScheduler] scheduledUpdateCoin()");
        log.info(new Date() + " : BTC coin 스케줄러 실행");
        //스케쥴러 실행되면 true 변경
        onScheduled = true;

        List<UserMemberDto> userMemberDtos = iUserCoinSchedulerMB.selectAllUser();

        for (UserMemberDto userMemberDto : userMemberDtos) {
            int user_no = userMemberDto.getNo();
            String id = userMemberDto.getId();

            int coinByMorningAttendance = 0;
            int coinByAfternoonAttendance = 0;
            int coinAttendance = coinByMorningAttendance + coinByAfternoonAttendance;
            int coinByStudy = 0;
            int coinByAssignment = 0;

            List<UserAttendanceDto> userAttendanceDtos = iUserCoinSchedulerMB.coinByAttendance(id);

            if (userAttendanceDtos != null) {
                for (UserAttendanceDto userAttendanceDto : userAttendanceDtos) {
                    // 출석 상태에 따라 출석 점수 설정
                    if (userAttendanceDto.getAstatus() == 0 && userAttendanceDto.getTstatus() == 0) {
                        coinByMorningAttendance = 1;

                    }
                    if (userAttendanceDto.getAstatus() == 0 && userAttendanceDto.getTstatus() == 1) {
                        coinByAfternoonAttendance = 1;

                    }
                }

            }

            // 스터디 개설 시 2점 부여
            int userStudyCnt = iUserCoinSchedulerMB.coinByStudy(id);
            if (userStudyCnt > 0) {
                coinByStudy = 2;

            }

            List<UserAssignmentDto> userAssignmentDtos = iUserCoinSchedulerMB.coinByAssignment(user_no);

            // 과제 : 0점 0, 1-4점 1, 5-8점 3, 9-10점 5
            if (userAssignmentDtos != null) {
                for (UserAssignmentDto userAssignmentDto : userAssignmentDtos) {
                    int coinByAssignments = userAssignmentDto.getPoint() == 0 ? 0 :
                            userAssignmentDto.getPoint() < 5 ? 1 :
                                    userAssignmentDto.getPoint() < 9 ? 3 : 5;

                    coinByAssignment += coinByAssignments;

                }
            }

            // member의 coin값 가져오기
            int oriCoin = iUserCoinSchedulerMB.selectCoin(id);

            // coin값 업데이트
            int totalCoin = oriCoin + coinAttendance + coinByStudy + coinByAssignment;

            int result = iUserCoinSchedulerMB.updateCoin(id, totalCoin);
            log.info("User ID: {}, coinAttendance: {}, coinByStudy: {}, coinByAssignment: {}, OriCoin: {}, TotalCoin: {}, Result: {}", id, coinAttendance, coinByStudy, coinByAssignment, oriCoin, totalCoin, result);

            //스케쥴러 끝나면 false로 변경
            onScheduled = false;
        }
    }
}
