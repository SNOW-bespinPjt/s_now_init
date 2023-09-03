package com.btc.snow.user.assignment;

import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class UserAssignmentService implements IUserAssignmentService {

    @Autowired
    IUserAssignmentMB iUserAssignmentMB;

    // 상수 : 공통상수 빼기
    final static public int ASSIGNMENT_SUCCESS = 1;     //성공
    final static public int ASSIGNMENT_FAIL = 0;        //실패

    // 과제 리스트
    @Override
    public List<UserAssignmentDto> listAssignment() {
        log.info("[UserAssignmentService] listAssignment()");

        return iUserAssignmentMB.selectAssignments();
    }

    // 과제 등록
    @Override
    public int RegistrationConfirm(UserAssignmentDto userAssignmentDto) {
        log.info("[UserAssignmentService] RegistrationConfirm()");

        int result = iUserAssignmentMB.insertAssignment(userAssignmentDto);

        if (result > 0) {
            return ASSIGNMENT_SUCCESS;

        } else {
            return ASSIGNMENT_FAIL;

        }
    }


    // 과제 상세페이지
    @Override
    public int getIsSubmit(int group_id, int user_no) {
        log.info("[UserAssignmentService] getAssignment()");

        boolean isSubmit = iUserAssignmentMB.selectIsSubmitNum(group_id, user_no);

        if (isSubmit) {
            return ASSIGNMENT_SUCCESS;

        } else {
            return ASSIGNMENT_FAIL;
        }

    }
    @Override
    public UserAssignmentDto getAssignment(int no) {
        log.info("[UserAssignmentService] getAssignment()");

        return iUserAssignmentMB.selectAssignment(no);
    }

    // 과제 수정
    @Override
    public int modifyAssignmentConfirm(UserAssignmentDto userAssignmentDto) {
        log.info("[UserAssignmentService] modifyAssignmentConfirm()");

        return iUserAssignmentMB.updateAssignment(userAssignmentDto);
    }

    // 과제 삭제
    @Override
    public int deleteAssignmentConfirm(int no) {
        log.info("[UserAssignmentService] deleteAssignmentConfirm()");

        return iUserAssignmentMB.deleteAssignment(no);
    }
}
