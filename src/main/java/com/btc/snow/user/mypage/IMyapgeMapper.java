package com.btc.snow.user.mypage;


import com.btc.snow.include.StudyPromiseDto;
import com.btc.snow.user.member.UserMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IMyapgeMapper {

    List<StudyPromiseDto> selectSceduleByUser(Map<String, Object> map);

    int updateStatus(int no);

    int getTotalSudyPromiseDtos(String id);

    List<StudyPromiseDto> selectSceduleByMember(Map<String, Object> map);

    int getTotalSudyPromiseDtosByMember(String id);

    List<UserMemberDto> selectUserId();

    List<UserMemberDto> selectMemberId();
}
