package chapter2.item8;

import java.sql.*;

/**
 * 演示 Effective Java 第8条：真实场景 - 数据库连接管理
 * 
 * 场景：数据库连接必须正确关闭，否则会耗尽连接池
 * 
 * @author CN-JeffreyZhou
 */
public class Item8_RealWorld {

    // ❌ 错误：依赖 finalize 关闭数据库连接
    static class BadDatabaseConnection {
        private Connection connection;
        
        public BadDatabaseConnection(String url) {
            try {
                // 模拟获取连接
                System.out.println("获取数据库连接: " + url);
                // connection = DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void executeQuery(String sql) {
            System.out.println("执行查询: " + sql);
        }
        
        @Override
        protected void finalize() throws Throwable {
            try {
                // 问题：可能永远不执行，导致连接泄漏
                System.out.println("⚠️ finalize 关闭连接（不可靠）");
                if (connection != null) {
                    connection.close();
                }
            } finally {
                super.finalize();
            }
        }
    }
    
    // ✅ 正确：实现 AutoCloseable
    static class GoodDatabaseConnection implements AutoCloseable {
        private Connection connection;
        private String url;
        
        public GoodDatabaseConnection(String url) {
            this.url = url;
            try {
                System.out.println("获取数据库连接: " + url);
                // connection = DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void executeQuery(String sql) {
            System.out.println("执行查询: " + sql);
        }
        
        @Override
        public void close() {
            // 确定性关闭连接
            System.out.println("✓ 立即关闭连接: " + url);
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ✅ 最佳实践：带安全网的连接管理
    static class DatabaseConnectionWithSafetyNet implements AutoCloseable {
        private static final java.lang.ref.Cleaner cleaner = java.lang.ref.Cleaner.create();
        
        private static class State implements Runnable {
            Connection connection;
            String url;
            
            State(Connection connection, String url) {
                this.connection = connection;
                this.url = url;
            }
            
            @Override
            public void run() {
                // 安全网：记录警告并清理
                System.err.println("⚠️ 警告：连接未正确关闭 " + url + "，安全网清理");
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private final State state;
        private final java.lang.ref.Cleaner.Cleanable cleanable;
        
        public DatabaseConnectionWithSafetyNet(String url) {
            Connection conn = null;
            try {
                System.out.println("获取数据库连接: " + url);
                // conn = DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.state = new State(conn, url);
            this.cleanable = cleaner.register(this, state);
        }
        
        public void executeQuery(String sql) {
            System.out.println("执行查询: " + sql);
        }
        
        @Override
        public void close() {
            cleanable.clean();
            System.out.println("✓ 正常关闭连接: " + state.url);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 场景：数据库连接管理 ===\n");
        
        System.out.println("1. 错误做法：依赖 finalize");
        BadDatabaseConnection bad = new BadDatabaseConnection("jdbc:mysql://localhost/bad");
        bad.executeQuery("SELECT * FROM users");
        bad = null; // 连接可能泄漏
        
        System.out.println("\n2. 正确做法：try-with-resources");
        try (GoodDatabaseConnection good = new GoodDatabaseConnection("jdbc:mysql://localhost/good")) {
            good.executeQuery("SELECT * FROM users");
        } // 连接立即关闭
        
        System.out.println("\n3. 最佳实践：带安全网");
        try (DatabaseConnectionWithSafetyNet best = new DatabaseConnectionWithSafetyNet("jdbc:mysql://localhost/best")) {
            best.executeQuery("SELECT * FROM users");
        } // 正常关闭
        
        System.out.println("\n4. 忘记关闭的情况");
        DatabaseConnectionWithSafetyNet forgotten = new DatabaseConnectionWithSafetyNet("jdbc:mysql://localhost/forgotten");
        forgotten.executeQuery("SELECT * FROM users");
        forgotten = null; // 忘记关闭，安全网会处理
        
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n总结：");
        System.out.println("✓ 始终使用 try-with-resources");
        System.out.println("✓ Cleaner 作为安全网，记录警告");
        System.out.println("✗ 永远不要依赖 finalize");
    }
}
