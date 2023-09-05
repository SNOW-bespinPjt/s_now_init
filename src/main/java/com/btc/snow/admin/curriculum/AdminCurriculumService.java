package com.btc.snow.admin.curriculum;

import com.btc.snow.admin.member.AdminMemberDto;
import com.btc.snow.user.meeting.book.UserBookItemDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@Service
public class AdminCurriculumService implements IAdminCurriculumService {

    // CONFIG INFO START
    private final int CREATE_CURRICULUM_SUCCESS_AT_DB = 1;
    private final int CREATE_CURRICULUM_FAIL_AT_DB = 0;
    private final int CREATE_ERROR_AT_DB = -1;

    private final int MODIFY_CURRICULUM_SUCCESS_AT_DB = 1;
    private final int MODIFY_CURRICULUM_FAIL_AT_DB = 0;
    private final int MODIFY_ERROR_AT_DB = -1;

    private final int DELETE_CURRICULUM_SUCCESS_AT_DB = 1;
    private final int DELETE_CURRICULUM_FAIL_AT_DB = 0;
    private final int DELETE_ERROR_AT_DB = -1;

    // CONFIG INFO END

    private IAdminCurriculumDaoMB iAdminCurriculumDaoMB;

    public AdminCurriculumService(IAdminCurriculumDaoMB iAdminCurriculumDaoMB) {
        this.iAdminCurriculumDaoMB = iAdminCurriculumDaoMB;

    }

    @Override
    public int createCurriculumConfirm(AdminCurriculumDto adminCurriculumDto) {
        log.info("[AdminCurriculumService] createCurriculumConfirm()");

        int result = iAdminCurriculumDaoMB.inputNewCurriculum(adminCurriculumDto);

        if (result > 0) {
            log.info("[createCurriculumConfirm] INPUT NEW CURRICULUM SUCCESS!!");
            return CREATE_CURRICULUM_SUCCESS_AT_DB;

        } else if (result == 0) {
            log.info("[createCurriculumConfirm] INPUT NEW CURRICULUM FAIL!!");
            return CREATE_CURRICULUM_FAIL_AT_DB;

        } else {
            log.info("[createCurriculumConfirm] DB ERROR!!");
            return CREATE_ERROR_AT_DB;

        }

    }

    @Override
    public Map<String, Object> showAllCurriculum(AdminMemberDto loginedAdminDto) {
        log.info("[AdminCurriculumService] showAllCurriculum()");

        Map<String, Object> msgMap = new HashMap<>();

        // SHOW ALL ADMIN CURRICULUM NOT ANY LIMIT
        if (loginedAdminDto.getId().equals("super admin")) {
            List<AdminCurriculumDto> adminCurriculumDtos = iAdminCurriculumDaoMB.selectAdminCurriculum();

            if (adminCurriculumDtos != null) {
                log.info("[selectAdminCurriculum] SHOW ALL CURRICULUM WITH SUPER SUCCESS!!");
                msgMap.put("adminCurriculumDtos", adminCurriculumDtos);

                return msgMap;

            } else {
                log.info("[selectAdminCurriculum] SHOW ALL CURRICULUM WITH SUPER FAIL!!");

                return null;

            }

        }

        // SHOW ALL CURRICULUM WHERE ADMIN_ID
        List<AdminCurriculumDto> adminCurriculumDtos = iAdminCurriculumDaoMB.selectCurriculumById(loginedAdminDto);
        if (adminCurriculumDtos != null) {
            log.info("[selectAdminCurriculum] SHOW ALL CURRICULUM WITH ADMIN_ID SUCCESS!!");
            msgMap.put("adminCurriculumDtos", adminCurriculumDtos);

            return msgMap;

        } else {
            log.info("[selectAdminCurriculum] SHOW ALL CURRICULUM WITH ADMIN_ID FAIL!!");

            return null;

        }

    }

    @Override
    public Map<String, Object> showDetailCurriculum(int no) {
        log.info("[AdminCurriculumService] showDetailCurriculum()");

        Map<String, Object> msgMap = new HashMap<>();

        AdminCurriculumDto adminCurriculumDto = iAdminCurriculumDaoMB.showDetailByNo(no);

        if (adminCurriculumDto != null) {
            log.info("[showDetailCurriculum] SHOW DETAIL INFO SUCCESS!!");

            // 담당자 name(이름) 불러오기 START
            AdminMemberDto adminMemberDto = iAdminCurriculumDaoMB.selectAdminName(adminCurriculumDto.getAdmin_id());
            log.info("adminMemberDto name" + adminMemberDto.getName());

            // 책 표지(사진) 불러오기 START
            UserBookItemDto userBookItemDto = iAdminCurriculumDaoMB.selectBookCover(adminCurriculumDto.getBook_no());
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
    public int modifyCurriculumConfirm(AdminCurriculumDto adminCurriculumDto) {
        log.info("[AdminCurriculumService] modifyCurriculumConfirm()");

        int result = iAdminCurriculumDaoMB.updateCurriculum(adminCurriculumDto);

        if (result > 0) {
            log.info("[modifyCurriculumConfirm] MODIFY CURRICULUM SUCCESS!!");
            return MODIFY_CURRICULUM_SUCCESS_AT_DB;

        } else if (result == 0) {
            log.info("[modifyCurriculumConfirm] MODIFY CURRICULUM FAIL!!");
            return MODIFY_CURRICULUM_FAIL_AT_DB;

        } else {
            log.info("[modifyCurriculumConfirm] DB ERROR!!");
            return MODIFY_ERROR_AT_DB;

        }

    }

    @Override
    public int deleteCurriculumConfirm(int no) {
        log.info("[AdminCurriculumService] deleteCurriculumConfirm()");

        int result = iAdminCurriculumDaoMB.removeCurriculum(no);

        if (result > 0) {
            log.info("[deleteCurriculumConfirm] DELETE CURRICULUM SUCCESS!!");
            return DELETE_CURRICULUM_SUCCESS_AT_DB;

        } else if (result == 0) {
            log.info("[deleteCurriculumConfirm] DELETE CURRICULUM FAIL!!");
            return DELETE_CURRICULUM_FAIL_AT_DB;

        } else {
            log.info("[deleteCurriculumConfirm] DB ERROR!!");
            return DELETE_ERROR_AT_DB;

        }

    }

    @Override
    public Map<String, Object> searchTitleConfirm(String searchTitle, AdminMemberDto loginedAdminDto) {
        log.info("[AdminCurriculumService] searchTitleConfirm()");

        Map<String, Object> msgMap = new HashMap<>();

        msgMap.put("searchTitle", searchTitle);
        msgMap.put("admin_id", loginedAdminDto.getId());

        List<AdminCurriculumDto> adminCurriculumDtos = iAdminCurriculumDaoMB.searchTitleByWord(msgMap);

        if (adminCurriculumDtos != null) {
            log.info("[searchTitleConfirm] SEARCH TITLE SUCCESS!!");

            msgMap.put("adminCurriculumDtos", adminCurriculumDtos);
            return msgMap;

        }

        log.info("[searchTitleConfirm] SEARCH TITLE FAIL!!");
        return null;

    }

}
