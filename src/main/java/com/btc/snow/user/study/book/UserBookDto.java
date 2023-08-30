package com.btc.snow.user.study.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class UserBookDto {
    private List<Item> item;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String title;
        private String author;
        private String cover;
        private String isbn13;
        private String publisher;
        private String description;
    }

}

