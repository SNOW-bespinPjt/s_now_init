package com.btc.snow.admin.assignment;

import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.member.UserMemberDto;
import lombok.Data;

@Data
public class CombinedDto {
    private UserAssignmentDto assignmentDto;
    private UserMemberDto memberDto;

}
