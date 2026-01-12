package chapter2.item4;

/**
 * 演示 Effective Java 第4条：通过私有构造器强化不可实例化的能力
 * 
 * 真实场景：常见的工具类
 * - 字符串工具类
 * - 文件路径工具类
 * - 常量类
 * 
 * @author CN-JeffreyZhou
 */
public class Item4_RealWorld {
    
    /**
     * 字符串工具类
     */
    static class StringUtils {
        private StringUtils() {
            throw new AssertionError("工具类不应该被实例化");
        }
        
        public static boolean isEmpty(String str) {
            return str == null || str.isEmpty();
        }
        
        public static String capitalize(String str) {
            if (isEmpty(str)) return str;
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }
    
    /**
     * 文件路径工具类
     */
    static class PathUtils {
        private PathUtils() {
            throw new AssertionError();
        }
        
        public static String getExtension(String filename) {
            int lastDot = filename.lastIndexOf('.');
            return lastDot > 0 ? filename.substring(lastDot + 1) : "";
        }
        
        public static String removeExtension(String filename) {
            int lastDot = filename.lastIndexOf('.');
            return lastDot > 0 ? filename.substring(0, lastDot) : filename;
        }
    }
    
    /**
     * 常量类（也应该使用私有构造器）
     */
    static class HttpStatus {
        private HttpStatus() {
            throw new AssertionError();
        }
        
        public static final int OK = 200;
        public static final int NOT_FOUND = 404;
        public static final int SERVER_ERROR = 500;
    }
    
    public static void main(String[] args) {
        // 使用字符串工具类
        System.out.println("=== 字符串工具类 ===");
        System.out.println("isEmpty(null): " + StringUtils.isEmpty(null));
        System.out.println("capitalize(\"hello\"): " + StringUtils.capitalize("hello"));
        
        // 使用文件路径工具类
        System.out.println("\n=== 文件路径工具类 ===");
        String filename = "document.pdf";
        System.out.println("文件: " + filename);
        System.out.println("扩展名: " + PathUtils.getExtension(filename));
        System.out.println("无扩展名: " + PathUtils.removeExtension(filename));
        
        // 使用常量类
        System.out.println("\n=== HTTP 状态码 ===");
        System.out.println("成功: " + HttpStatus.OK);
        System.out.println("未找到: " + HttpStatus.NOT_FOUND);
        System.out.println("服务器错误: " + HttpStatus.SERVER_ERROR);
    }
}
