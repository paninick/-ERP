package com.ruoyi.erp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket STOMP 配置
 * 场景：ERP 生产看板实时推送（工单状态变化、工序完成通知）
 *
 * 订阅话题：
 *   /topic/erp/produce   — 生产看板实时数据（全员广播）
 *   /topic/erp/alert     — 异常报警推送
 *   /user/queue/notify   — 个人通知（点对点）
 *
 * 前端连接：
 *   SockJS → http://host/ws/erp
 *   STOMP  → subscribe('/topic/erp/produce', callback)
 */
@Configuration
@EnableWebSocketMessageBroker
public class ErpWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用内存消息代理，处理 /topic/** 和 /user/** 的订阅
        config.enableSimpleBroker("/topic", "/user");
        // 客户端发往服务端的消息前缀（@MessageMapping 方法的前缀）
        config.setApplicationDestinationPrefixes("/app");
        // 点对点消息的用户前缀
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/erp")
                // 允许所有来源（生产环境应限制为部署域名）
                .setAllowedOriginPatterns("*")
                // SockJS 回退（兼容不支持原生 WebSocket 的浏览器）
                .withSockJS();
    }
}
