package com.btc.snow.user.meeting;

import lombok.Data;

@Data
public class UserStudyDto {

    private int no;
    private String u_id;
    private String title;
    private String body;
    private String img;
    private int b_no;
    private int total;
    private int hit;
    private String start_date;
    private String end_date;
    private String reg_date;
    private String mod_date;
}
