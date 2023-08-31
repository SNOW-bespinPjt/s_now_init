package com.btc.snow.admin.assignment;

import com.btc.snow.admin.member.AdminMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AdminAssignmentService implements IAdminAssignmentService {

    @Autowired
    IAdminAssignmentMB iAdminAssignmentMB;

    // 상수
    final static public int ASSIGNMENT_SUCCESS = 1;     //성공
    final static public int ASSIGNMENT_FAIL = 0;        //실패

    // 과제 리스트
    @Override
    public List<AdminAssignmentDto> listupAssignment() {
        log.info("[AdminAssignmentService] listupAssignment()");

        return iAdminAssignmentMB.selectAssignments();
    }

    // 과제 등록
    @Override
    public int RegistrationConfirm(AdminAssignmentDto adminAssignmentDto) {
        log.info("[AdminAssignmentService] RegistrationConfirm()");

        int result = iAdminAssignmentMB.insertAssignment(adminAssignmentDto);

        if (result > 0) {
            return ASSIGNMENT_SUCCESS;

        } else {
            return ASSIGNMENT_FAIL;

        }

    }

}
