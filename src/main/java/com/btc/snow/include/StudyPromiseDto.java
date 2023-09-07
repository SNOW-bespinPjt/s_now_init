package com.btc.snow.include;

import lombok.Data;

@Data
public class StudyPromiseDto {
    int no;
    String status;
    String member_id;
    String user_id;
    int study_no;
    String reg_date;
    String mod_date;
    String study_title;


    private String img;

}
