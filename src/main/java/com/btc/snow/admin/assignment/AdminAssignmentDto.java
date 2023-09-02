package com.btc.snow.admin.assignment;

import lombok.Data;

@Data
public class AdminAssignmentDto {
    private int no; //'과제 번호(as_no)',
    private int admin_no; //'관리자 번호(강사 admin_no)',
    private int user_no; //'유저 번호(교육생 user_no)',
    private int group_id; //'과제 그룹 아이디(as_no를 따와서 id값으로 설정)',
    private String title; // '과제 제목',
    private String body; // longtext '과제 본문',
    private String file; //'과제 파일',
    private String file_name; //'과제 파일 사용자 지정 이름',
    private int point; //default 0  '과제점수 점수',
    private int is_activation; //default 0  '과제 활성화(비활성:0, 활성:1)',
    private String start_date; //'과제 시작일',
    private String end_date; //'과제 종료일',
    private String reg_date; //'과제 등록일',
    private String mod_date; //'과제 수정일',
}
