package chapter2.item6;

/**
 * 演示 Effective Java 第6条：避免创建不必要的对象
 * 
 * 要点1：重用不可变对象
 * String 是不可变的，应该重用而不是每次创建新实例
 * 
 * @author CN-JeffreyZhou
 */
public class Item6_StringCreation {
    
    public static void main(String[] args) {
        // 错误做法：每次调用都创建新的 String 实例
        String bad = new String("狗");  // 不要这样做！
        
        // 正确做法：重用字符串常量池中的实例
        String good = "狗";
        
        // 演示差异
        System.out.println("=== 字符串创建对比 ===");
        
        // 错误做法：循环中创建100万个不必要的对象
        long start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            String s = new String("狗");  // 每次都创建新对象
        }
        long badTime = System.nanoTime() - start;
        
        // 正确做法：重用字符串常量
        start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            String s = "狗";  // 重用常量池中的对象
        }
        long goodTime = System.nanoTime() - start;
        
        System.out.println("错误做法耗时: " + badTime / 1_000_000 + " ms");
        System.out.println("正确做法耗时: " + goodTime / 1_000_000 + " ms");
        System.out.println("性能提升: " + (badTime / goodTime) + "x");
    }
}
