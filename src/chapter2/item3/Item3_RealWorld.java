package chapter2.item3;

/**
 * 演示 Effective Java 第3条：真实场景 - 配置管理器
 * 
 * 实际应用：全局配置、数据库连接池、日志管理器等
 * 
 * @author CN-JeffreyZhou
 */
public class Item3_RealWorld {
    
    // 应用配置管理器 - 使用枚举实现单例
    public enum AppConfig {
        INSTANCE;
        
        private String dbUrl = "jdbc:mysql://localhost:3306/mydb";
        private int maxConnections = 100;
        private boolean debugMode = false;
        
        public String getDbUrl() {
            return dbUrl;
        }
        
        public void setDbUrl(String dbUrl) {
            this.dbUrl = dbUrl;
        }
        
        public int getMaxConnections() {
            return maxConnections;
        }
        
        public void enableDebug() {
            this.debugMode = true;
            System.out.println("调试模式已启用");
        }
        
        public void showConfig() {
            System.out.println("数据库URL: " + dbUrl);
            System.out.println("最大连接数: " + maxConnections);
            System.out.println("调试模式: " + debugMode);
        }
    }
    
    public static void main(String[] args) {
        // 在应用的不同地方访问配置
        AppConfig config1 = AppConfig.INSTANCE;
        config1.enableDebug();
        
        AppConfig config2 = AppConfig.INSTANCE;
        config2.showConfig();
        
        // 验证是同一个实例
        System.out.println("\n配置管理器是单例: " + (config1 == config2));
    }
}
