package com.marcos.chat.dto;

import com.marcos.chat.enums.MessageType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    private String message;
    private String sender;
    private MessageType type;
}
