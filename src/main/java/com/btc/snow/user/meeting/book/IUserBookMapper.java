package com.btc.snow.user.meeting.book;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserBookMapper {
    void scheduledUpdateBook(List<UserBookDto.Item> itemList);

    List<UserBookDto> selectSearchBooks(String title);

//    void scheduledUpdateBook(UserBookDto.Item itemList);
}
