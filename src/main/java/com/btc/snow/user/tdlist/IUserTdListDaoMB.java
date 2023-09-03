package com.btc.snow.user.tdlist;


import com.btc.snow.user.member.UserMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserTdListDaoMB {

    /*
     * SHOW ALL TODOLIST
     */
    public List<UserTdListDto> showAllTdList(UserMemberDto userMemberDto);

    /*
     * INSERT NEW TODOLIST
     */
    public int insertTdList(UserTdListDto userTdListDto);

    /*
     * UPDATE TODOLIST
     */
    public int updateTdList(UserTdListDto userTdListDto);

    /*
     * UPDATE ISFINISH
     */
    public int updateIsFinish(int className);

    /*
     * DELETE TODOLIST
     */
    public int removeTdList(int className);


}
