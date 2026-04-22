package com.ruoyi.erp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * ERP 实时推送 Service
 * 通过 STOMP over WebSocket 向前端推送生产状态变化
 *
 * 在任意 Service 里注入此类并调用 push* 方法即可触发推送。
 * 示例（报工完成后推送看板刷新信号）：
 *   erpRealtimePushService.pushProduceBoardRefresh();
 */
@Service
public class ErpRealtimePushService {

    private static final Logger log = LoggerFactory.getLogger(ErpRealtimePushService.class);

    private static final String TOPIC_PRODUCE  = "/topic/erp/produce";
    private static final String TOPIC_ALERT    = "/topic/erp/alert";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 推送生产看板刷新信号（触发前端重新拉取数据）
     */
    public void pushProduceBoardRefresh() {
        try {
            String payload = "{\"type\":\"REFRESH\",\"ts\":" + System.currentTimeMillis() + "}";
            messagingTemplate.convertAndSend(TOPIC_PRODUCE, payload);
        } catch (Exception e) {
            log.warn("WebSocket 推送失败（非致命，不影响业务）: {}", e.getMessage());
        }
    }

    public void pushProcessComplete(String jobNo, String processName, int qty) {
        try {
            String payload = String.format(
                "{\"type\":\"PROCESS_COMPLETE\",\"jobNo\":\"%s\",\"processName\":\"%s\",\"qty\":%d,\"ts\":%d}",
                jobNo, processName, qty, System.currentTimeMillis());
            messagingTemplate.convertAndSend(TOPIC_PRODUCE, payload);
        } catch (Exception e) {
            log.warn("WebSocket 推送失败（非致命）: {}", e.getMessage());
        }
    }

    public void pushAlert(String level, String message) {
        try {
            String payload = String.format(
                "{\"level\":\"%s\",\"message\":\"%s\",\"ts\":%d}",
                level, message.replace("\"", "\\\""), System.currentTimeMillis());
            messagingTemplate.convertAndSend(TOPIC_ALERT, payload);
        } catch (Exception e) {
            log.warn("WebSocket 报警推送失败（非致命）: {}", e.getMessage());
        }
    }

    public void pushUserNotify(String username, String message) {
        try {
            String payload = String.format(
                "{\"message\":\"%s\",\"ts\":%d}",
                message.replace("\"", "\\\""), System.currentTimeMillis());
            messagingTemplate.convertAndSendToUser(username, "/queue/notify", payload);
        } catch (Exception e) {
            log.warn("WebSocket 用户通知推送失败（非致命）: {}", e.getMessage());
        }
    }
}
