package com.btc.snow.admin.curriculum;

import com.btc.snow.admin.member.AdminMemberDto;
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
    private final int ERROR_AT_DB = -1;

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
            log.info("[createCurriculumConfirm] INPUT NEW CURRICULUM FAIL!!");
            return ERROR_AT_DB;

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

        List<AdminCurriculumDto> adminCurriculumDtos = iAdminCurriculumDaoMB.selectCurriculumById(loginedAdminDto);
        msgMap.put("adminCurriculumDtos", adminCurriculumDtos);

        return msgMap;

    }


}
