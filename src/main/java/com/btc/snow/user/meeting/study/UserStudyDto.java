package com.btc.snow.user.meeting.study;

import lombok.Data;

@Data
public class UserStudyDto {

    private int no;
    private String user_id;
    private int book_no;
    private String title;
    private String body;
    private int hit;
    private int total;
    private String img;
    private String start_date;
    private String end_date;
    private String reg_date;
    private String mod_date;
}
