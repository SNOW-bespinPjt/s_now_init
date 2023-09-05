package com.btc.snow.admin.assignment;

import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.user.assignment.IUserAssignmentMB;
import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.member.IUserMemberDaoMB;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class AdminAssignmentService implements IAdminAssignmentService {

    @Autowired
    IAdminAssignmentMB iAdminAssignmentMB;

    @Autowired
    IUserMemberDaoMB iUserMemberDaoMB;

    @Autowired
    IUserAssignmentMB iUserAssignmentMB;

    // 상수 : 공통상수 빼기
    final static public int ASSIGNMENT_SUCCESS = 1;     // 성공
    final static public int ASSIGNMENT_FAIL = 0;        // 실패

    // 과제 리스트
    @Override
    public List<AdminAssignmentDto> listAssignment() {
        log.info("[AdminAssignmentService] listAssignment()");

        return iAdminAssignmentMB.selectAssignments();
    }

    // 과제 등록
    @Override
    @Transactional
    public int RegistrationConfirm(AdminAssignmentDto adminAssignmentDto) {
        log.info("[AdminAssignmentService] RegistrationConfirm()");

        // 과제 등록 쿼리 실행
        int result = iAdminAssignmentMB.insertAssignment(adminAssignmentDto);

        if (result > 0) {
            // 과제 등록 후 생성된 no 값을 가져오기
            int assignmentNo = iAdminAssignmentMB.getGeneratedNo(adminAssignmentDto);

            // 모든 사용자의 정보 가져오기
            List<UserMemberDto> users = iUserAssignmentMB.getAllUsers();

            // 각 사용자별로 과제 등록
            for (UserMemberDto user : users) {
                UserAssignmentDto userAssignmentDto = new UserAssignmentDto();
                userAssignmentDto.setUser_no(user.getNo());
                userAssignmentDto.setGroup_id(assignmentNo);

                // 사용자별 과제 등록 쿼리 실행
                iUserAssignmentMB.insertAssignment(userAssignmentDto);
            }

            return ASSIGNMENT_SUCCESS;

        } else {
            return ASSIGNMENT_FAIL;
        }
    }

    // 과제 활성화
    @Override
    public int SetAdminActive(int no) {
        log.info("[AdminAssignmentService] SetAdminActive()");

        // 활성화 번호 찾기
        AdminAssignmentDto selectActivationNum = iAdminAssignmentMB.selectActivationNum(no);
        int is_activation = selectActivationNum.getIs_activation();
        log.info("is_activation --> " + is_activation);

        int result = 0;
        if (is_activation == 0) {
            // 비활성화를 활성화로
            result = iAdminAssignmentMB.updateAdminActive(no);
        } else {
            // 활성화를 비활성화로
            result = iAdminAssignmentMB.updateAdminInactive(no);
        }

        if (result > 0) {
            log.info("ADMIN ASSIGNMENT ACTIVE SUCCESS!!");
        } else {
            log.info("ADMIN ASSIGNMENT ACTIVE FAIL!!");
        }

        return result;
    }

    // 과제 상세페이지
    @Override
    public AdminAssignmentDto getAssignment(int no) {
        log.info("[AdminAssignmentService] getAssignment()");

        return iAdminAssignmentMB.selectAssignment(no);
    }

    // 과제 수정
    @Override
    public int modifyAssignmentConfirm(AdminAssignmentDto adminAssignmentDto) {
        log.info("[AdminAssignmentService] modifyAssignmentConfirm()");

        return iAdminAssignmentMB.updateAssignment(adminAssignmentDto);
    }

    // 과제 삭제
    @Override
    public int deleteAssignmentConfirm(int no) {
        log.info("[AdminAssignmentService] deleteAssignmentConfirm()");

        return iAdminAssignmentMB.deleteAssignment(no);
    }

    // 과제 제출 학생 리스트
    @Override
    public List<UserMemberDto> getUserList() {
        log.info("[AdminAssignmentService] getUserList()");

        return iAdminAssignmentMB.selectUsers();
    }

    @Override
    public List<UserAssignmentDto> checkAssignmentList(int no) {
        log.info("[AdminAssignmentService] checkAssignmentList()");

        return iAdminAssignmentMB.selectSubmitUser(no);
    }

    // 과제 점수 등록
    @Override
    public int AssignmentInputPoint(UserAssignmentDto userAssignmentDto) {
        log.info("[AdminAssignmentService] AssignmentInputPoint()");

        int result = iAdminAssignmentMB.updatePoint(userAssignmentDto);
        if (result > 0) {
            return ASSIGNMENT_SUCCESS;
        } else {
            return ASSIGNMENT_FAIL;
        }
    }

    // 과제 비활성화 스케줄러
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void updateAssignmentActivation() {
        log.info("[AdminAssignmentService] updateAssignmentActivation()");

        // 타입 맞추기 : 변환할 수 없는 타입인 'LocalDate' 및 'String'의 객체 사이에 있습니다
        // LocalDate today = LocalDate.now();

        // 현재 날짜
        LocalDate today = LocalDate.now();

        // 포맷터를 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDate를 문자열로 변
        String dateString = today.format(formatter);
        log.info("현재 날짜 문자열: " + dateString);

        // 모든 과제 리스트
        List<AdminAssignmentDto> assignments = iAdminAssignmentMB.selectAssignments();

        for (AdminAssignmentDto assignment : assignments) {
            if (assignment.getEnd_date() != null && dateString.equals(assignment.getEnd_date())) {
                // today 무조건 할당될 수 있는 값 : assignment.getEnd_date() 조회되어져 가져오는 값 >> 할당되는게 보장되어 있는 값이 있어야 뒤에 null 값이 있어도 return
                // end_date가 오늘 이전이면 is_activation을 0으로 설정
                assignment.setIs_activation(0);
                iAdminAssignmentMB.updateAssignmentActivation(assignment);
            }
        }

        // 과제 미제출 학생 메일 보내기

    }
}
