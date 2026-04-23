package com.ruoyi.web.controller.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 监听 Redis 消息并将其推送到 WebSocket 客户端
 * 
 * @author ruoyi
 */
@Component
public class RedisWebSocketListener {
    
    private static final Logger log = LoggerFactory.getLogger(RedisWebSocketListener.class);

    /**
     * 接收 Redis 发来的消息
     * 消息格式可以约定为: userId:messageContent，也可以是 JSON 格式
     */
    public void receiveMessage(String message) {
        log.info("从 Redis 收到 WebSocket 广播消息: {}", message);
        try {
            // 这里简单处理：如果包含冒号，前面是userId，后面是消息内容。否则认为是广播给所有人。
            // 实际生产中建议使用 JSON 对象来封装目标用户和消息内容
            if (message.contains("@@")) {
                String[] parts = message.split("@@", 2);
                String targetUserId = parts[0];
                String msgContent = parts[1];
                WebSocketServer.sendInfo(msgContent, targetUserId);
            } else {
                WebSocketServer.sendAllInfo(message);
            }
        } catch (Exception e) {
            log.error("处理 Redis 广播消息失败", e);
        }
    }
}
