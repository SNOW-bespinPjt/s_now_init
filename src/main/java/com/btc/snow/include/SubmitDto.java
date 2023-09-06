package com.btc.snow.include;

import lombok.Data;

@Data
public class SubmitDto {
    int no;
    int a_no;
    int status;
    String content;
    String file;
    String reg_date;
    String mod_date;
    String u_id;

    String name;
    String mail;
}
