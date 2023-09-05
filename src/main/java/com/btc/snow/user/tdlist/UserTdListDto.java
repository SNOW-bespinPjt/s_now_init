package com.btc.snow.user.tdlist;

import lombok.Data;

@Data
public class UserTdListDto {

    private int no;
    private String user_id;
    private String content;
    private String tag;
    private int is_finish;
    private String start_date;
    private String end_date;
    private String reg_date;
    private String mod_date;

}
