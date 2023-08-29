package com.btc.snow.user.study;

import lombok.Data;

@Data
public class UserStudyDto {

    private int s_no;
    private String u_m_id;
    private String b_isbn;
    private String s_title;
    private String s_body;
    private int s_hit;
    private int s_total;
    private int s_attend;
    private String s_img;
    private String s_reg_date;
}
