package com.btc.snow.user.attendance;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserAttendanceDto {
    int no;
    String u_id;
    int astatus;
    int tstatus;
    String reg_date;
    String mod_date;
    MultipartFile file;

}
