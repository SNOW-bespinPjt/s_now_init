package com.btc.snow.user.assignment;

import com.btc.snow.admin.assignment.AdminAssignmentDto;
import com.btc.snow.admin.assignment.StatisticsDto;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<UserAssignmentDto> listAssignment(HttpSession session) {
        log.info("[UserAssignmentService] listAssignment()");

        // 로그인 회원 번호 찾기
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        int user_no = loginedUserDto.getNo();

        // is_activation = 1 인 모든 과제 리스트 불러오기
        List<UserAssignmentDto> userAssignmentDtos = iUserAssignmentMB.selectAssignments();

        // 사용자와 관련된 과제 목록을 저장할 리스트 생성
        List<UserAssignmentDto> relevantUserAssignmentDtos = new ArrayList<>();

        // 모든 과목 목록을 순회하면서 사용자와 관련된 과제만 필터링
        for (UserAssignmentDto dto : userAssignmentDtos) {
            int group_id = dto.getNo();

            // is_activation = 1 인 과제 중에서 현재 로그인한 사용자와 관련된 과제 필터링
            List<UserAssignmentDto> userSpecificAssignments = iUserAssignmentMB.selectAssignmentListWithSameId(group_id, user_no);

            if (!userSpecificAssignments.isEmpty()) {
                // 사용자와 관련된 과제가 존재할 경우, relevantUserAssignmentDtos에 추가
                UserAssignmentDto relevantAssignment = new UserAssignmentDto();

                // 필요한 컬럼 값을 복사
                relevantAssignment.setNo(dto.getNo());
                relevantAssignment.setAdmin_no(dto.getAdmin_no());
                relevantAssignment.setUser_no(dto.getUser_no());
                relevantAssignment.setTitle(dto.getTitle());
                relevantAssignment.setBody(dto.getBody());
                relevantAssignment.setGroup_id(dto.getGroup_id());
                relevantAssignment.setEnd_date(dto.getEnd_date());
                relevantAssignment.setMod_date(dto.getMod_date());

                // userSpecificAssignments의 값이 없는 컬럼을 업데이트 : group_id, user_no, is_submit, mod_date
                if (userSpecificAssignments.get(0).getGroup_id() != 0) {
                    relevantAssignment.setGroup_id(userSpecificAssignments.get(0).getGroup_id());
                }
                if (userSpecificAssignments.get(0).getUser_no() != 0) {
                    relevantAssignment.setUser_no(userSpecificAssignments.get(0).getUser_no());
                }
                if (userSpecificAssignments.get(0).getIs_submit() != 0) {
                    relevantAssignment.setIs_submit(userSpecificAssignments.get(0).getIs_submit());
                }
                if (userSpecificAssignments.get(0).getEnd_date() != null) {
                    relevantAssignment.setEnd_date(userSpecificAssignments.get(0).getEnd_date());
                }
                if (userSpecificAssignments.get(0).getMod_date() != null) {
                    relevantAssignment.setMod_date(userSpecificAssignments.get(0).getMod_date());
                }

                // relevantUserAssignmentDtos에 추가
                relevantUserAssignmentDtos.add(relevantAssignment);
            }
        }

        return relevantUserAssignmentDtos;
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

    // 과제 점수
// 과제 점수
    @Override
    public Map<String, Object> myPoint(int user_no) {
        log.info("[UserAssignmentService] myPoint()");

        Map<String, Object> resultMap = new HashMap<>();

        List<UserAssignmentDto> pointDtos = iUserAssignmentMB.selectMyPoint(user_no);

        int minPoint = Integer.MAX_VALUE;
        int maxPoint = Integer.MIN_VALUE;
        int totalPoint = 0;
        double averagePoint = 0.0;
        String averagePointToString = null;

        if (!pointDtos.isEmpty()) {
            for (UserAssignmentDto dto : pointDtos) {
                int point = dto.getPoint();

                if (point < minPoint) {
                    minPoint = point;
                }
                if (point > maxPoint) {
                    maxPoint = point;
                }
                totalPoint += point;
            }

            averagePoint = (double) totalPoint / pointDtos.size();

            // averagePoint 소수점 제거
            averagePointToString = String.format("%.2f", averagePoint);
        }

        // 통계 데이터를 Map에 추가
        resultMap.put("minPoint", minPoint);
        resultMap.put("maxPoint", maxPoint);
        resultMap.put("averagePoint", averagePointToString);

        // UserAssignmentDto 리스트를 Map에 추가
        resultMap.put("pointDtos", pointDtos);

        return resultMap;
    }


    // 나의 과제
    @Override
    public List<UserAssignmentDto> myAssignment(int user_no) {
        log.info("[UserAssignmentService] myAssignment()");

        // 로그인 멤버의 과제목록 가져오는 Dtos
        List<UserAssignmentDto> userAssignments = iUserAssignmentMB.selectMyAssignment(user_no);

        // 중복된 과제를 방지하기 위한 Set 사용
        // Set을 사용하면 중복 검사와 중복 제거를 간결하게 처리할 수 있으며, 코드가 간단하고 이해하기 쉬워서 사용
        // 중복 방지, 빠른 검색, 하지만 순서 저장은 안함 - 저장된 순서 순회는 불가
        // HashSet: HashSet은 Set 인터페이스를 구현한 클래스 중 하나로, 해시 테이블을 사용하여 요소를 저장
        Set<String> assignmentTitles = new HashSet<>();

        for (UserAssignmentDto dto : userAssignments) {
            int no = dto.getGroup_id();
            List<UserAssignmentDto> selectAssignmentForMyPage = iUserAssignmentMB.selectAssignmentForMyPage(no);

            for (UserAssignmentDto assignment : selectAssignmentForMyPage) {
                // 중복된 제목이 아닌 경우에만 추가
                if (assignmentTitles.add(assignment.getTitle())) {
                    dto.setTitle(assignment.getTitle());
                }
                if (assignmentTitles.add(assignment.getFile_user())) {
                    dto.setFile_user(assignment.getFile_user());
                }
                if (assignmentTitles.add(assignment.getFile_user_name())) {
                    dto.setFile_user_name(assignment.getFile_user_name());
                }

            }
        }

        return userAssignments;
    }

}
