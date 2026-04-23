package com.ruoyi.web.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import com.ruoyi.web.controller.websocket.RedisWebSocketListener;

/**
 * Redis 消息监听器配置，用于 WebSocket 集群消息同步
 * 
 * @author ruoyi
 */
@Configuration
public class RedisMessageListenerConfig
{
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 订阅一个通道 "websocket:topic"
        container.addMessageListener(listenerAdapter, new PatternTopic("websocket:topic"));
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RedisWebSocketListener receiver) {
        // 当收到消息时调用 receiver 的 receiveMessage 方法
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
