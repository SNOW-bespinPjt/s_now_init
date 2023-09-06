package com.btc.snow.user.tdlist;


import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/user/tdlist")
public class UserTdListController {

    /*
     * 생성자 주입은 생성자의 호출 시점에 1회 호출 되는 것이 보장, 그래서 주입받은 객체가 변하지 않거나, 반드시 객체의 주입이 필요한 경우에 강제하기 위해 사용
     * 생성자가 1개만 있을 경우에 @Autowired를 생략해도 주입이 가능하도록 한다.
     */
    private UserTdListService userTdListService;

    public UserTdListController(UserTdListService userTdListService) {
        this.userTdListService = userTdListService;

    }

    /*
     * SHOW TODOLIST AND CREATE
     */
    @GetMapping("/tdlist_form")
    public String selectTdListForm() {
        log.info("[UserTdListController] selectTdListForm()");

        String nextPage = "user/tdlist/todolist_form";

        return nextPage;

    }

    @PostMapping("/tdlist_confirm")
    @ResponseBody
    public Map<String, Object> selectTdListConfirm(HttpSession session) {
        log.info("[UserTdListController] selectTdListConFirm()");

        Map<String, Object> msgMap = new HashMap<>();

        // 이후 인터셉터와 시큐리티를 통해 loginedUserDto를 가져올 수 있을듯 그래서 추후 삭제 될 예정
        // AFTER REMOVE START
        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        // AFTER REMOVE END

        List<UserTdListDto> userTdListDtos = userTdListService.selectTdListConfirm(loginedUserDto);

        msgMap.put("userTdListDtos", userTdListDtos);

        return msgMap;

    }

    @PostMapping("/create_tdlist_confirm")
    @ResponseBody
    public int createTdListConfirm(UserTdListDto userTdListDto, HttpSession session) {
        log.info("[UserTdListController] createTdListConfirm()");

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        userTdListDto.setUser_id(loginedUserDto.getId());

        return userTdListService.createTdListConfirm(userTdListDto);

    }

    @PostMapping("/modify_tdlist_confirm")
    @ResponseBody
    public int modifyTdListConfirm(UserTdListDto userTdListDto) {
        log.info("[UserTdListController] modifyTdListConfirm()");

        return userTdListService.modifyTdListConfirm(userTdListDto);

    }

    @PostMapping("/modify_isfinish_confirm")
    @ResponseBody
    public int modifyIsFinishConfirm(@RequestParam("className") int className) {
        log.info("[UserTdListController] modifyTdListConfirm()");

        return userTdListService.modifyIsFinishConfirm(className);

    }

    @PostMapping("/delete_tdlist_confirm")
    @ResponseBody
    public int deleteTdListConfirm(@RequestParam("className") int className) {
        log.info("[UserTdListController] deleteTdListConfirm()");
        log.info("className.NO : " + className);

        return userTdListService.deleteTdListConfirm(className);

    }

    @PostMapping("/search_tag_confirm")
    @ResponseBody
    public Map<String, Object> searchTagConfirm(@RequestParam("search_word") String search_word, HttpSession session) {
        log.info("[UserTdListController] searchTagConfirm()");
        log.info("search_word : " + search_word);

        Map<String, Object> msgMap = new HashMap<>();

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        String user_id = loginedUserDto.getId();

        List<UserTdListDto> userTdListDtos = userTdListService.searchTagConfirm(search_word, user_id);

        msgMap.put("userTdListDtos", userTdListDtos);

        return msgMap;

    }

    @PostMapping("/search_finish_confirm")
    @ResponseBody
    public Map<String, Object> searchFinishConfirm(HttpSession session) {
        log.info("[UserTdListController] searchFinishConfirm()");

        Map<String, Object> msgMap = new HashMap<>();

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");
        String user_id = loginedUserDto.getId();

        List<UserTdListDto> userTdListDtos = userTdListService.searchFinishConfirm(user_id);

        msgMap.put("userTdListDtos", userTdListDtos);

        return msgMap;

    }

}
