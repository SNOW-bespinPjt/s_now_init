package com.btc.snow.user.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {

    String message = "";
    String href = "";

    public Message(String message, String href){
        this.message = message;
        this.href = href;

    }
}
