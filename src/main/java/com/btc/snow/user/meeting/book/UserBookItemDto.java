package com.btc.snow.user.meeting.book;

import lombok.Data;

@Data
public class UserBookItemDto {
    private String title;
    private String author;
    private String cover;
    private String isbn13;
    private String publisher;
    private String description;
}

