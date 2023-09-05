package com.btc.snow.user.meeting.book;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/book")
@Log4j2
public class UserBookController {

    @Autowired
    UserBookService userBookService;

    @GetMapping("/search_book")
    @ResponseBody
    public Object searchBook(@RequestParam("title") String title) {
        log.info("searchBook()");

        Map<String, Object> map = userBookService.searchBook(title);

        return map;

    }


}
