package com.btc.snow.user.assignment;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class UserAssignmentService implements IUserAssignmentService {

    @Autowired
    IUserAssignmentMB iUserAssignmentMB;

    // 과제 리스트
    @Override
    public List<UserAssignmentDto> listAssignment() {
        log.info("[UserAssignmentService] listAssignment()");

        return null;
    }

    // 과제 등록
    @Override
    public int RegistrationConfirm(UserAssignmentDto userAssignmentDto) {


        return 0;
    }

    // 과제 상세페이지
    @Override
    public UserAssignmentDto getAssignment(int no) {


        return null;
    }

    // 과제 수정
    @Override
    public int modifyAssignmentConfirm(UserAssignmentDto userAssignmentDto) {


        return 0;
    }

    // 과제 삭제
    @Override
    public int deleteAssignmentConfirm(int no) {


        return 0;
    }
}
