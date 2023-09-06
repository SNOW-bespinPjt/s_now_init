package com.btc.snow.user.curriculum;

import com.btc.snow.admin.curriculum.AdminCurriculumDto;
import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.user.meeting.book.UserBookItemDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@Service
public class UserCurriculumService implements IUserCurriculumService {

    private IUserCurriculumDaoMB iUserCurriculumDaoMB;

    public UserCurriculumService(IUserCurriculumDaoMB iUserCurriculumDaoMB) {
        this.iUserCurriculumDaoMB = iUserCurriculumDaoMB;

    }

    @Override
    public Map<String, Object> showAllCurriculum() {
        log.info("[UserCurriculumService] showAllCurriculum()");

        Map<String, Object> msgMap = new HashMap<>();

        List<AdminCurriculumDto> adminCurriculumDtos = iUserCurriculumDaoMB.selectAdminCurriculum();

        if (adminCurriculumDtos != null) {
            log.info("[selectAdminCurriculum] SHOW ALL CURRICULUM SUCCESS!!");
            msgMap.put("adminCurriculumDtos", adminCurriculumDtos);

            return msgMap;

        } else {
            log.info("[selectAdminCurriculum] SHOW ALL CURRICULUM FAIL!!");

            return null;

        }

    }

    @Override
    public Map<String, Object> showDetailCurriculum(int no) {
        log.info("[UserCurriculumService] showDetailCurriculum()");

        Map<String, Object> msgMap = new HashMap<>();

        AdminCurriculumDto adminCurriculumDto = iUserCurriculumDaoMB.showDetailByNo(no);

        if (adminCurriculumDto != null) {
            log.info("[showDetailCurriculum] SHOW DETAIL INFO SUCCESS!!");

            // 담당자 name(이름) 불러오기 START
            AdminMemberDto adminMemberDto = iUserCurriculumDaoMB.selectAdminName(adminCurriculumDto.getAdmin_id());
            log.info("adminMemberDto name" + adminMemberDto.getName());

            // 책 표지(사진) 불러오기 START
            UserBookItemDto userBookItemDto = iUserCurriculumDaoMB.selectBookCover(adminCurriculumDto.getBook_no());
            log.info("userBookDto cover" + userBookItemDto.getCover());

            msgMap.put("userBookItemDto", userBookItemDto);
            msgMap.put("adminMemberDto", adminMemberDto);
            msgMap.put("adminCurriculumDto", adminCurriculumDto);

            return msgMap;

        } else {
            log.info("[showDetailCurriculum] SHOW DETAIL INFO FAIL!!");

            return null;

        }

    }

    @Override
    public Map<String, Object> searchTitleConfirm(String searchTitle) {
        log.info("[UserCurriculumService] searchTitleConfirm()");

        Map<String, Object> msgMap = new HashMap<>();

        msgMap.put("searchTitle", searchTitle);

        List<AdminCurriculumDto> adminCurriculumDtos = iUserCurriculumDaoMB.searchTitleByWord(msgMap);

        if (adminCurriculumDtos != null) {
            log.info("[searchTitleConfirm] SEARCH TITLE SUCCESS!!");

            msgMap.put("adminCurriculumDtos", adminCurriculumDtos);
            return msgMap;

        }

        log.info("[searchTitleConfirm] SEARCH TITLE FAIL!!");
        return null;

    }

//    @Override
//    public void evaluateCurriculumConfirm(int grade, int curriculum_no, AdminMemberDto adminMemberDto) {
//        log.info("[UserCurriculumService] evaluateCurriculumConfirm()");
//
//        UserHistoryPointDto userHistoryPointDto = new UserHistoryPointDto();
//
//        userHistoryPointDto.setUser_id(adminMemberDto.getId());
//        userHistoryPointDto.setCurriculum_no(curriculum_no);
//        userHistoryPointDto.setGrade(grade);
//
//        iUserCurriculumDaoMB.evaluateCurriculum(userHistoryPointDto);
//
//    }

}
