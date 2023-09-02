package com.btc.snow.user.tdlist;


import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String selectTdListForm(Model model, HttpSession session) {
        log.info("[UserTdListController] selectTdListForm()");

        String nextPage = "user/member/todolist_form";

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
    public void createTdListConfirm(UserTdListDto userTdListDto, HttpSession session) {
        log.info("[UserTdListController] createTdListConfirm()");

        Map<String, Object> msgMap = new HashMap<>();

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        userTdListDto.setUser_id(loginedUserDto.getId());

        log.info("userid : " + userTdListDto.getUser_id());
        log.info("getContent : " + userTdListDto.getContent());
        log.info("getTag : " + userTdListDto.getTag());
        log.info("getIs_finish : " + userTdListDto.getIs_finish());
        log.info("getStart_date : " + userTdListDto.getStart_date());
        log.info("getEnd_date : " + userTdListDto.getEnd_date());

        userTdListService.createTdListConfirm(userTdListDto);

    }

    @GetMapping("/modify_tdlist_confirm")
    public String modifyTdListConfirm(UserTdListDto userTdListDto) {
        log.info("[UserTdListController] modifyTdListConfirm()");

        userTdListService.modifyTdListConfirm(userTdListDto);

        return null;
    }

    @PostMapping("/delete_tdlist_confirm")
    @ResponseBody
    public void deleteTdListConfirm(@RequestParam("className") int className) {
        log.info("[UserTdListController] deleteTdListConfirm()");

        log.info("className.NO : " + className);

        userTdListService.deleteTdListConfirm(className);

    }

}
