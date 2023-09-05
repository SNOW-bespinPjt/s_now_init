package com.btc.snow.user.meeting.meal;

import lombok.Data;

@Data
public class UserMealDto {

    private int no;
    private String user_id;
    private String title;
    private String body;
    private int hit;
    private int total;
    private String img;
    private String date;
    private String reg_date;
    private String mod_date;

}
