package com.btc.snow.user.meeting.study;

import com.btc.snow.user.meeting.book.UserBookDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserStudyMapper {


    public List<UserBookDto> selectSearchBooks(String title);
}
