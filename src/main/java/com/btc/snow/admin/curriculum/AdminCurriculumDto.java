package com.btc.snow.admin.curriculum;

import lombok.Data;


@Data
public class AdminCurriculumDto {

    private int no;
    private String admin_id;
    private Integer book_no;
    private String title;
    private String content;
    private String category;
    private String std_div;
    private Integer hour;
    private String file_name;
    private String start_date;
    private String end_date;
    private String reg_date;
    private String mod_date;

}
