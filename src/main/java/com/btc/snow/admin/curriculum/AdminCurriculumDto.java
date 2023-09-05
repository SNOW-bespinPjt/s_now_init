package com.btc.snow.admin.curriculum;

import lombok.Data;


@Data
public class AdminCurriculumDto {

    private int no;
    private String admin_id;
    private int book_no;
    private String title;
    private String content;
    private String category;
    private String std_div;
    private int isedu;
    private int hour;
    private String file;
    private int grade;
    private String start_date;
    private String end_date;
    private String reg_date;
    private String mod_date;

}
