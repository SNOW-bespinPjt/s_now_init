package com.btc.snow.user.tdlist;


import com.btc.snow.user.member.UserMemberDto;

import java.util.List;

public interface IUserTdListService {

    // SELECT TODOLIST CONFIRM
    public List<UserTdListDto> selectTdListConfirm(UserMemberDto userMemberDto);

    // CREATE TODOLIST CONFIRM
    public int createTdListConfirm(UserTdListDto userTdListDto);

    // MODIFY TODOLIST CONFIRM
    public int modifyTdListConfirm(UserTdListDto userTdListDto);

    // MODIFY ISFINISH CONFIRM
    public int modifyIsFinishConfirm(int className);

    // DELETE TODOLIST CONFIRM
    public int deleteTdListConfirm(int className);

    // SEARCH TAG CONFIRM
    public List<UserTdListDto> searchTagConfirm(String searchWord, String userId);

    // SEARCH FINISH CONFIRM
    public List<UserTdListDto> searchFinishConfirm(String user_id);

}
