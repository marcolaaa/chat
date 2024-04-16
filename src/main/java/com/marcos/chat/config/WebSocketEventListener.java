package com.marcos.chat.config;

import com.marcos.chat.dto.ChatMessage;
import com.marcos.chat.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    public final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("username");

        if (username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder().type(MessageType.LEAVE).sender(username).build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
