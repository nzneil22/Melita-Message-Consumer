package com.melitamessageconsumer;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MessagePayload {
    final String body;

    @Override
    public String toString(){
        return body;
    }
}
