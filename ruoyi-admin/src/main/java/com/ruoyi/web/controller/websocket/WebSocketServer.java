package com.ruoyi.web.controller.websocket;

import java.util.concurrent.ConcurrentHashMap;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

/**
 * WebSocket 核心服务端点
 * 
 * @author ruoyi
 */
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer
{
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /** 
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收userId
     */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId)
    {
        this.session = session;
        this.userId = userId;
        
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
        } else {
            webSocketMap.put(userId, this);
            addOnlineCount();
        }
        
        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());
        
        try {
            sendMessage("连接成功");
        } catch (Exception e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose()
    {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        log.info("用户消息:" + userId + ",报文:" + message);
        // 可以群发消息，或者响应给某人
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error)
    {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws Exception
    {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送给指定用户
     */
    public static void sendInfo(String message, String userId) throws Exception
    {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if(StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    /**
     * 发送给所有在线用户
     */
    public static void sendAllInfo(String message) throws Exception
    {
        for (String key : webSocketMap.keySet()) {
            webSocketMap.get(key).sendMessage(message);
        }
    }

    public static synchronized int getOnlineCount()
    {
        return onlineCount;
    }

    public static synchronized void addOnlineCount()
    {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount()
    {
        WebSocketServer.onlineCount--;
    }
}
