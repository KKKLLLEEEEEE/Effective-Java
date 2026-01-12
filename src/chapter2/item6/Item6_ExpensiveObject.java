package chapter2.item6;

import java.util.regex.Pattern;

/**
 * 演示 Effective Java 第6条：避免创建不必要的对象
 * 
 * 要点2：重用昂贵的对象
 * 如果对象创建成本高，应该缓存重用而不是重复创建
 * 
 * @author CN-JeffreyZhou
 */
public class Item6_ExpensiveObject {
    
    // 错误做法：每次调用都创建 Pattern 对象
    static class BadAnimalValidator {
        static boolean isValidName(String name) {
            // Pattern.matches 内部每次都创建 Pattern 对象
            return name.matches("[\\u4e00-\\u9fa5]{2,4}");  // 匹配2-4个中文字符
        }
    }
    
    // 正确做法：缓存 Pattern 对象重用
    static class GoodAnimalValidator {
        // 将昂贵的 Pattern 对象声明为静态常量，只创建一次
        private static final Pattern NAME_PATTERN = 
            Pattern.compile("[\\u4e00-\\u9fa5]{2,4}");
        
        static boolean isValidName(String name) {
            return NAME_PATTERN.matcher(name).matches();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 昂贵对象重用对比 ===");
        
        String[] animalNames = {"狗", "猫", "大熊猫", "金毛犬", "invalid123"};
        
        // 测试错误做法
        long start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            for (String name : animalNames) {
                BadAnimalValidator.isValidName(name);
            }
        }
        long badTime = System.nanoTime() - start;
        
        // 测试正确做法
        start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            for (String name : animalNames) {
                GoodAnimalValidator.isValidName(name);
            }
        }
        long goodTime = System.nanoTime() - start;
        
        System.out.println("错误做法耗时: " + badTime / 1_000_000 + " ms");
        System.out.println("正确做法耗时: " + goodTime / 1_000_000 + " ms");
        System.out.println("性能提升: " + (badTime / goodTime) + "x");
        
        // 验证功能正确性
        System.out.println("\n验证结果:");
        for (String name : animalNames) {
            System.out.println(name + " -> " + GoodAnimalValidator.isValidName(name));
        }
    }
}
