package chapter2.item7;

import java.util.*;

/**
 * 演示 Effective Java 第7条：消除过期的对象引用（真实场景）
 * 
 * 场景：用户会话管理系统
 * 问题：会话过期后未清理，导致内存泄漏
 * 
 * @author CN-JeffreyZhou
 */
public class Item7_RealWorld {

    /**
     * 错误示例：会话管理器未清理过期会话
     */
    static class BadSessionManager {
        private final Map<String, UserSession> sessions = new HashMap<>();

        public void createSession(String sessionId, String username) {
            sessions.put(sessionId, new UserSession(username));
            System.out.println("创建会话: " + sessionId + " (" + username + ")");
        }

        public UserSession getSession(String sessionId) {
            return sessions.get(sessionId);
        }

        // 问题：只标记过期，但不清理
        public void expireSession(String sessionId) {
            UserSession session = sessions.get(sessionId);
            if (session != null) {
                session.expire();
                System.out.println("会话已过期: " + sessionId);
                // 内存泄漏：未从 Map 中删除
            }
        }

        public int getSessionCount() {
            return sessions.size();
        }
    }

    /**
     * 正确示例：会话管理器清理过期会话
     */
    static class GoodSessionManager {
        private final Map<String, UserSession> sessions = new HashMap<>();

        public void createSession(String sessionId, String username) {
            sessions.put(sessionId, new UserSession(username));
            System.out.println("创建会话: " + sessionId + " (" + username + ")");
        }

        public UserSession getSession(String sessionId) {
            return sessions.get(sessionId);
        }

        // 正确：清理过期会话
        public void expireSession(String sessionId) {
            UserSession session = sessions.remove(sessionId); // 从 Map 中删除
            if (session != null) {
                session.expire();
                session.clearData(); // 清理会话数据
                System.out.println("会话已过期并清理: " + sessionId);
            }
        }

        // 定期清理所有过期会话
        public void cleanupExpiredSessions() {
            sessions.entrySet().removeIf(entry -> {
                if (entry.getValue().isExpired()) {
                    entry.getValue().clearData();
                    System.out.println("清理过期会话: " + entry.getKey());
                    return true;
                }
                return false;
            });
        }

        public int getSessionCount() {
            return sessions.size();
        }
    }

    /**
     * 用户会话
     */
    static class UserSession {
        private final String username;
        private final long createTime;
        private boolean expired = false;
        private Map<String, Object> attributes = new HashMap<>();

        UserSession(String username) {
            this.username = username;
            this.createTime = System.currentTimeMillis();
            // 模拟会话数据
            attributes.put("loginTime", new Date());
            attributes.put("permissions", Arrays.asList("read", "write"));
        }

        public void expire() {
            this.expired = true;
        }

        public boolean isExpired() {
            return expired;
        }

        // 清理会话数据
        public void clearData() {
            attributes.clear();
            attributes = null; // 帮助GC
        }

        public String getUsername() {
            return username;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 真实场景：会话管理内存泄漏 ===\n");

        System.out.println("1. 错误示例（BadSessionManager）：");
        BadSessionManager badManager = new BadSessionManager();
        badManager.createSession("session-001", "张三");
        badManager.createSession("session-002", "李四");
        badManager.createSession("session-003", "王五");
        
        System.out.println("当前会话数: " + badManager.getSessionCount());
        
        badManager.expireSession("session-001");
        badManager.expireSession("session-002");
        
        System.out.println("过期2个会话后，会话数: " + badManager.getSessionCount());
        System.out.println("问题：过期会话仍占用内存\n");

        System.out.println("2. 正确示例（GoodSessionManager）：");
        GoodSessionManager goodManager = new GoodSessionManager();
        goodManager.createSession("session-101", "赵六");
        goodManager.createSession("session-102", "孙七");
        goodManager.createSession("session-103", "周八");
        
        System.out.println("当前会话数: " + goodManager.getSessionCount());
        
        goodManager.expireSession("session-101");
        goodManager.expireSession("session-102");
        
        System.out.println("过期2个会话后，会话数: " + goodManager.getSessionCount());
        System.out.println("正确：过期会话被清理，释放内存");
    }
}
