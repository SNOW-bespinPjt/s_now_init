package com.btc.snow.admin.assignment;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequestMapping("/admin/assignment")
@Controller
public class AdminAssignmentController {

    @Autowired
    AdminAssignmentService adminAssignmentService;

    /*
    * EX
    */
    @GetMapping("/ex")
    public void EX(){
        log.info("[AdminAssignmentController] EX()");

    }


}
