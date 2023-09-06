package com.btc.snow.user.tdlist;


import com.btc.snow.user.member.UserMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserTdListService implements IUserTdListService {

    // CONFIG INFO START
    private final int CREATE_TODOLIST_SUCCESS_AT_DB = 1;
    private final int CREATE_TODOLIST_FAIL_AT_DB = 0;
    private final int CREATE_ERROR_AT_DB = -1;

    private final int MODIFY_TODOLIST_SUCCESS_AT_DB = 1;
    private final int MODIFY_TODOLIST_FAIL_AT_DB = 0;
    private final int MODIFY_ERROR_AT_DB = -1;

    private final int DELETE_TODOLIST_SUCCESS_AT_DB = 1;
    private final int DELETE_TODOLIST_FAIL_AT_DB = 0;
    private final int DELETE_ERROR_AT_DB = -1;

    // CONFIG INFO END

    private IUserTdListDaoMB iUserTdListDaoMB;

    public UserTdListService(IUserTdListDaoMB iUserTdListDaoMB) {
        this.iUserTdListDaoMB = iUserTdListDaoMB;

    }

    @Override
    public List<UserTdListDto> selectTdListConfirm(UserMemberDto userMemberDto) {
        log.info("[UserTdListService] selectTdListConfirm()");

        List<UserTdListDto> userTdListDtos = iUserTdListDaoMB.showAllTdList(userMemberDto);
        return userTdListDtos;

    }

    @Override
    public List<UserTdListDto> selectTdListInHome(UserMemberDto userMemberDto) {
        log.info("[UserTdListService] selectTdListInHome()");

        List<UserTdListDto> userTdListDtos = iUserTdListDaoMB.showAllTdListInHome(userMemberDto);
        return userTdListDtos;

    }

    @Override
    public int createTdListConfirm(UserTdListDto userTdListDto) {
        log.info("[UserTdListService] createTdListConfirm()");

        int result = iUserTdListDaoMB.insertTdList(userTdListDto);

        if (result > 0) {
            log.info("[UserTdListService] INSERT TODOLIST SUCCESS!!");

            return CREATE_TODOLIST_SUCCESS_AT_DB;

        } else if (result == 0) {
            log.info("[UserTdListService] INSERT TODOLIST FAIL!!");
            return CREATE_TODOLIST_FAIL_AT_DB;

        } else {
            log.info("[UserTdListService] CREATE ERROR AT DB!!");
            return CREATE_ERROR_AT_DB;

        }

    }

    @Override
    public int modifyTdListConfirm(UserTdListDto userTdListDto) {
        log.info("[UserTdListService] modifyTdListConfirm()");

        int result = iUserTdListDaoMB.updateTdList(userTdListDto);

        if (result > 0) {
            log.info("[UserTdListService] MODIFY TODOLIST SUCCESS!!");
            return MODIFY_TODOLIST_SUCCESS_AT_DB;

        } else if (result == 0) {
            log.info("[UserTdListService] MODIFY TODOLIST FAIL!!");
            return MODIFY_TODOLIST_FAIL_AT_DB;

        } else {
            log.info("[UserTdListService] MODIFY ERROR AT DB!!");
            return MODIFY_ERROR_AT_DB;

        }

    }

    @Override
    public int modifyIsFinishConfirm(int className) {
        int result = iUserTdListDaoMB.updateIsFinish(className);

        if (result > 0) {
            log.info("[UserTdListService]" + className + ".NO MODIFY IS_FINISH SUCCESS!!");

            return MODIFY_TODOLIST_SUCCESS_AT_DB;

        } else if (result == 0) {
            log.info("[UserTdListService]" + className + ".NO MODIFY IS_FINISH FAIL!!");
            return MODIFY_TODOLIST_FAIL_AT_DB;

        } else {
            log.info("[UserTdListService] MODIFY ERROR AT DB!!");
            return MODIFY_ERROR_AT_DB;
        }

    }

    @Override
    public int deleteTdListConfirm(int className) {
        log.info("[UserTdListService] deleteTdListConfirm()");

        int result = iUserTdListDaoMB.removeTdList(className);

        if (result <= 0) {
            log.info("[UserTdListService] DELETE TODOLIST SUCCESS!!");
            return DELETE_TODOLIST_SUCCESS_AT_DB;

        } else if (result == 0) {
            log.info("[UserTdListService] DELETE TODOLIST FAIL!!");
            return DELETE_TODOLIST_FAIL_AT_DB;

        } else {
            log.info("[UserTdListService] DELETE ERROR AT DB!!");
            return DELETE_ERROR_AT_DB;

        }

    }

    @Override
    public List<UserTdListDto> searchTagConfirm(String searchWord, String userId) {
        log.info("[UserTdListService] searchTagConfirm()");

        UserTdListDto userTdListDto = new UserTdListDto();

        userTdListDto.setUser_id(userId);
        userTdListDto.setTag(searchWord);
        userTdListDto.setContent(searchWord);

        List<UserTdListDto> userTdListDtos = iUserTdListDaoMB.selectListByTag(userTdListDto);

        if (userTdListDtos != null) {
            log.info("[UserTdListService] SEARCH TAG SUCCESS!!");
            return userTdListDtos;

        } else {
            log.info("[UserTdListService] SEARCH TAG FAIL!!");
            return null;

        }

    }

    @Override
    public List<UserTdListDto> searchFinishConfirm(String user_id) {
        log.info("[UserTdListService] searchFinishConfirm()");

        List<UserTdListDto> userTdListDtos = iUserTdListDaoMB.showFinishTdList(user_id);

        return userTdListDtos;

    }

}
