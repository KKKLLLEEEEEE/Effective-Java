package chapter2.item9;

import java.sql.*;
import java.io.*;
import java.util.Properties;

/**
 * 演示 Effective Java 第9条：真实场景应用
 * 
 * 场景：数据库连接、文件操作、网络连接等
 * 
 * @author CN-JeffreyZhou
 */
public class Item9_RealWorld {

    /**
     * 场景1：数据库操作（模拟）
     * 需要关闭 Connection、Statement、ResultSet
     */
    static class DatabaseExample {
        
        // ❌ 错误：try-finally 嵌套地狱
        static void queryOld(String sql) throws SQLException {
            Connection conn = DriverManager.getConnection("jdbc:...");
            try {
                Statement stmt = conn.createStatement();
                try {
                    ResultSet rs = stmt.executeQuery(sql);
                    try {
                        while (rs.next()) {
                            // 处理结果
                        }
                    } finally {
                        rs.close();
                    }
                } finally {
                    stmt.close();
                }
            } finally {
                conn.close();
            }
        }

        // ✅ 正确：try-with-resources 清晰简洁
        static void query(String sql) throws SQLException {
            try (Connection conn = DriverManager.getConnection("jdbc:...");
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                while (rs.next()) {
                    // 处理结果
                }
                // 所有资源自动按正确顺序关闭
            }
        }
    }

    /**
     * 场景2：配置文件读取
     */
    static class ConfigLoader {
        
        // ✅ 结合 catch 子句，提供默认值
        static Properties loadConfig(String path) {
            Properties props = new Properties();
            try (InputStream in = new FileInputStream(path)) {
                props.load(in);
            } catch (IOException e) {
                System.err.println("配置文件加载失败，使用默认配置: " + e.getMessage());
                // 设置默认值
                props.setProperty("timeout", "30");
                props.setProperty("retries", "3");
            }
            return props;
        }
    }

    /**
     * 场景3：自定义缓存管理器
     */
    static class CacheManager implements AutoCloseable {
        private final String cacheName;
        private boolean isOpen = true;

        public CacheManager(String cacheName) {
            this.cacheName = cacheName;
            System.out.println("📂 初始化缓存: " + cacheName);
        }

        public void put(String key, Object value) {
            if (!isOpen) throw new IllegalStateException("缓存已关闭");
            System.out.println("  ✍️  写入缓存: " + key);
        }

        public Object get(String key) {
            if (!isOpen) throw new IllegalStateException("缓存已关闭");
            System.out.println("  📖 读取缓存: " + key);
            return "cached_value";
        }

        @Override
        public void close() {
            if (isOpen) {
                System.out.println("💾 持久化缓存: " + cacheName);
                System.out.println("🔒 关闭缓存: " + cacheName);
                isOpen = false;
            }
        }

        // 使用示例
        static void processWithCache() {
            try (CacheManager cache = new CacheManager("user-cache")) {
                cache.put("user:1001", "张三");
                cache.get("user:1001");
                // 即使发生异常，缓存也会被正确关闭和持久化
            }
        }
    }

    /**
     * 场景4：文件批量处理
     */
    static void processFiles(String inputPath, String outputPath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                // 处理并写入
                writer.write(line.toUpperCase());
                writer.newLine();
            }
            
        } catch (IOException e) {
            System.err.println("文件处理失败: " + e.getMessage());
            throw e;  // 重新抛出，让调用者处理
        }
        // 无论成功或失败，文件都会被正确关闭
    }

    public static void main(String[] args) {
        System.out.println("=== 真实场景应用 ===\n");

        System.out.println("场景1：缓存管理器");
        CacheManager.processWithCache();

        System.out.println("\n场景2：配置加载（带默认值）");
        Properties config = ConfigLoader.loadConfig("nonexistent.properties");
        System.out.println("配置项: " + config);

        System.out.println("\n💡 实际应用场景：");
        System.out.println("- 数据库连接（Connection、Statement、ResultSet）");
        System.out.println("- 文件操作（Reader、Writer、Stream）");
        System.out.println("- 网络连接（Socket、HttpClient）");
        System.out.println("- 锁资源（Lock、Semaphore）");
        System.out.println("- 缓存管理（Cache、Session）");
    }
}
