package com.btc.snow.user.tdlist;


import com.btc.snow.user.member.UserMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserTdListService implements IUserTdListService {

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
    public int createTdListConfirm(UserTdListDto userTdListDto) {
        log.info("[UserTdListService] createTdListConfirm()");

        int result = iUserTdListDaoMB.insertTdList(userTdListDto);

        if (result > 0) {
            log.info("[UserTdListService] INSERT TODOLIST SUCCESS!!");

            return result;

        }

        log.info("[UserTdListService] INSERT TODOLIST FAIL!!");
        return result;

    }

    @Override
    public int modifyTdListConfirm(UserTdListDto userTdListDto) {
        log.info("[UserTdListService] modifyTdListConfirm()");

        int result = iUserTdListDaoMB.updateTdList(userTdListDto);

        if (result <= 0) {
            log.info("[UserTdListService] MODIFY TODOLIST SUCCESS!!");
            return result;

        } else {
            log.info("[UserTdListService] MODIFY TODOLIST FAIL!!");
            return result;

        }

    }

    @Override
    public int modifyIsFinishConfirm(int className) {
        int result = iUserTdListDaoMB.updateIsFinish(className);

        if (result > 0) {
            log.info("[UserTdListService]" + className + ".NO MODIFY IS_FINISH SUCCESS!!");

            return result;

        } else {
            log.info("[UserTdListService]" + className + ".NO MODIFY IS_FINISH FAIL!!");
            return result;

        }

    }

    @Override
    public int deleteTdListConfirm(int className) {
        log.info("[UserTdListService] deleteTdListConfirm()");

        int result = iUserTdListDaoMB.removeTdList(className);

        if (result <= 0) {
            log.info("[UserTdListService] DELETE TODOLIST SUCCESS!!");
            return result;

        } else {
            log.info("[UserTdListService] DELETE TODOLIST FAIL!!");
            return result;

        }
    }

    @Override
    public List<UserTdListDto> searchTagConfirm(String searchWord, String userId) {
        log.info("[UserTdListService] searchTagConfirm()");

        UserTdListDto userTdListDto = new UserTdListDto();

        userTdListDto.setUser_id(userId);
        userTdListDto.setTag(searchWord);

        List<UserTdListDto> userTdListDtos = iUserTdListDaoMB.selectListByTag(userTdListDto);

        if (userTdListDtos != null) {
            log.info("[UserTdListService] SEARCH TAG SUCCESS!!");
            return userTdListDtos;

        } else {
            log.info("[UserTdListService] SEARCH TAG FAIL!!");
            return null;

        }

    }
    
}
