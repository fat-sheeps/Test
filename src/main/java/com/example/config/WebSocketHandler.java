package com.example.config;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 单向消息 同一个会话内消息共享
 */
@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("WebSocket connection established: " + session.getId());
    }

    /*@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message: " + message.getPayload());
        // Echo the message back to the client
        session.sendMessage(new TextMessage("Server received: " + message.getPayload()));
    }*/
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received message: " + message.getPayload());
        // 广播消息给所有已连接的客户端
        broadcastMessage(session.getId() + ": " +message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        log.info("WebSocket connection closed: " + session.getId() + ", Status: " + status);
    }

    // 广播消息给所有客户端
    private void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 定时任务方法
    @Scheduled(fixedRate = 1000 * 60)  // 每隔1分钟执行一次
    public void sendTimeToClients() {
        String currentTime = DateUtil.formatDateTime(new Date());
        broadcastMessage(currentTime);
    }
}
