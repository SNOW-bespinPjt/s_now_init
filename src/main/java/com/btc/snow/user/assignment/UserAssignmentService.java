package com.btc.snow.user.assignment;

import com.btc.snow.admin.assignment.AdminAssignmentDto;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Object listAssignment(HttpSession session) {
        log.info("[UserAssignmentService] listAssignment()");

        Map<String, Object> map = new HashMap<>();

        // 로그인 회원 번호 찾기
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();

        // is_activation = 1 인 모든 과제 리스트 불러오기
        List<UserAssignmentDto> allUserAssignmentDtos = iUserAssignmentMB.selectAssignments();

        // 사용자와 관련된 과제 목록을 저장할 리스트 생성
        List<UserAssignmentDto> relevantUserAssignmentDtos = new ArrayList<>();

        // 모든 과 목록을 순회하면서 사용자와 관련된 과제만 필터링
        for (UserAssignmentDto dto : allUserAssignmentDtos) {
            int group_id = dto.getNo();

            // is_activation = 1 인 과제 중에서 현재 로그인한 사용자와 관련된 과제 필터링
            List<UserAssignmentDto> userSpecificAssignments = iUserAssignmentMB.selectAssignmentListWithSameId(group_id, user_no);

            if (!userSpecificAssignments.isEmpty()) {
                // 사용자와 관련된 과제가 존재할 경우, relevantUserAssignmentDtos에 추가
                relevantUserAssignmentDtos.addAll(userSpecificAssignments);
            }
        }

        map.put("allUserAssignmentDtos", allUserAssignmentDtos);
        map.put("relevantUserAssignmentDtos", relevantUserAssignmentDtos);

        return map;
    }

    // 과제 등록
    @Override
    public int RegistrationConfirm(UserAssignmentDto userAssignmentDto) {
        log.info("[UserAssignmentService] RegistrationConfirm()");

        int result = iUserAssignmentMB.submitAssignment(userAssignmentDto);

        if (result > 0) {
            return ASSIGNMENT_SUCCESS;

        } else {
            return ASSIGNMENT_FAIL;

        }
    }

    // 과제 상세페이지
    @Override
    public AdminAssignmentDto getDetail(int no) {
        log.info("[UserAssignmentService] getDetail()");

        return iUserAssignmentMB.selectDetail(no);
    }

    @Override
    public UserAssignmentDto getUserAssignmentInfo(int user_no, int no) {
        log.info("[UserAssignmentService] getUserAssignmentInfo()");

        return iUserAssignmentMB.selectAssignmentInfo(user_no, no);
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
