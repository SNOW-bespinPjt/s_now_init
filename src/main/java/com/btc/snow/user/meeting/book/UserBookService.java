package com.btc.snow.user.meeting.book;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Log4j2
public class UserBookService {

    String key = "ttbgjhs791423001";

    @Autowired
    private IUserBookMapper iUserBookMapper;

    @Scheduled(cron = "0 0 13 * * MON")
    public void scheduledUpdateBook() {
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        for (int start = 1; start <= 20; start++) {
            String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + key
                    + "&QueryType=Bestseller&MaxResults=50&start=" + start
                    + "&SearchTarget=Book&CategoryId=351&output=js&Version=20131101";

            HttpEntity<UserBookDto> entity = new HttpEntity<>(httpHeaders);

            // 데이터 요청
            UserBookDto body = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UserBookDto.class).getBody();

            if (body != null && !body.getItem().isEmpty()) {
                iUserBookMapper.scheduledUpdateBook(new ArrayList<UserBookDto.Item>(body.getItem()));
//                iBookDaoMapper.scheduledUpdateBook(body.getItem().get(0));


            }

        }

    }


}
