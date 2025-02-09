package com.pn.service.pnservice.chat;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/9 18:54
 * @Descirption xxx
 */

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;


@ServerEndpoint("/imserver/{userId}/{teamId}")
@Component
public class WebSocketServer {

    static Log log = LogFactory.get(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    //存放用户session
    private static Map<String, WebSocketServer> socketMap = new ConcurrentHashMap<>();
    //存放队伍的队员id
    private static Map<String, List<String>> teamMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";

    private String teamId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId,
                       @PathParam("teamId") String teamId) {
        this.session = session;
        this.userId = userId;
        this.teamId = teamId;

        if (StringUtils.isBlank(userId) && StringUtils.isBlank(teamId)) {
            return;
        }

        if (!socketMap.containsKey(userId)) {
            socketMap.put(userId, this);
            addOnlineCount();
        }
        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (teamMap.containsKey(userId)) {
            teamMap.remove(userId);
            //从set中删除
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
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + userId + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    //私聊
    private void privateChat(String toUserId, String message) throws IOException {
        if (StringUtils.isNotBlank(toUserId) && socketMap.containsKey(toUserId)) {
            socketMap.get(toUserId).sendMessage(message);
        } else {
            log.error("请求的userId:" + toUserId + "不在该服务器上");
        }
    }

    //群聊
    private void teamChat(String teamId, String message) throws IOException {
        if (StringUtils.isNotBlank(teamId) && teamMap.containsKey(teamId)) {
            List<String> memberIds = teamMap.get(teamId);
            for (String memberId : memberIds) {
                socketMap.get(memberId).sendMessage(message);
            }
        } else {
            log.error("请求的群聊房间:" + teamId + "不在该服务器上");
        }

    }

    //广播
    private void sendMessageAll(String message, String userId) throws IOException {
        if (StringUtils.isNotBlank(message)) {
            for (Map.Entry<String, WebSocketServer> entry : socketMap.entrySet()) {
                if(!entry.getKey().equals(userId)){
                    entry.getValue().sendMessage(message);
                }
            }
            log.info("用户广播一条信息 + " + message);
        }
    }
}

