package com.btc.snow.user.meeting.book;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/book")
@Log4j2
public class UserBookController {

    @Autowired
    UserBookService userBookService;

    @GetMapping("/search_book")
    @ResponseBody
    public Map<String, Object> searchBook(@RequestParam("title") String title) {
        log.info("searchBook()");

        Map<String, Object> map = userBookService.searchBook(title);


        return map;

    }


}
