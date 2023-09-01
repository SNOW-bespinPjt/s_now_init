package com.btc.snow.user.meeting.study;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserStudyService implements IUserStudyService {


    @Autowired
    IUserStudyMapper iUserStudyMapper;


}
