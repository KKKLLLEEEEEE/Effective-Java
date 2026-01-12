package chapter2.item6;

import java.util.regex.Pattern;

/**
 * 演示 Effective Java 第6条：避免创建不必要的对象
 * 
 * 真实场景：用户输入验证器
 * 展示如何在实际应用中避免创建不必要的对象
 * 
 * @author CN-JeffreyZhou
 */
public class Item6_RealWorld {
    
    // 错误做法：每次验证都创建新的 Pattern 对象
    static class BadUserValidator {
        public boolean isValidEmail(String email) {
            return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }
        
        public boolean isValidPhone(String phone) {
            return phone.matches("^1[3-9]\\d{9}$");
        }
        
        public boolean isValidUsername(String username) {
            return username.matches("^[a-zA-Z0-9_]{4,16}$");
        }
    }
    
    // 正确做法：缓存 Pattern 对象
    static class GoodUserValidator {
        private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^1[3-9]\\d{9}$");
        private static final Pattern USERNAME_PATTERN = 
            Pattern.compile("^[a-zA-Z0-9_]{4,16}$");
        
        public boolean isValidEmail(String email) {
            return EMAIL_PATTERN.matcher(email).matches();
        }
        
        public boolean isValidPhone(String phone) {
            return PHONE_PATTERN.matcher(phone).matches();
        }
        
        public boolean isValidUsername(String username) {
            return USERNAME_PATTERN.matcher(username).matches();
        }
    }
    
    // 自动装箱陷阱：统计系统
    static class Statistics {
        // 错误：使用包装类型
        public static Long calculateTotalBad(Integer[] numbers) {
            Long sum = 0L;  // 包装类型
            for (Integer num : numbers) {
                sum += num;  // 大量自动装箱
            }
            return sum;
        }
        
        // 正确：使用基本类型
        public static long calculateTotalGood(int[] numbers) {
            long sum = 0L;  // 基本类型
            for (int num : numbers) {
                sum += num;  // 无装箱
            }
            return sum;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 真实场景：用户输入验证 ===");
        
        String[] emails = {
            "user@example.com",
            "test.user@domain.co.uk",
            "invalid-email",
            "another@test.com"
        };
        
        // 性能对比
        BadUserValidator badValidator = new BadUserValidator();
        GoodUserValidator goodValidator = new GoodUserValidator();
        
        long start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            for (String email : emails) {
                badValidator.isValidEmail(email);
            }
        }
        long badTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            for (String email : emails) {
                goodValidator.isValidEmail(email);
            }
        }
        long goodTime = System.nanoTime() - start;
        
        System.out.println("错误做法耗时: " + badTime / 1_000_000 + " ms");
        System.out.println("正确做法耗时: " + goodTime / 1_000_000 + " ms");
        System.out.println("性能提升: " + (badTime / goodTime) + "x");
        
        // 验证功能
        System.out.println("\n邮箱验证结果:");
        for (String email : emails) {
            System.out.println(email + " -> " + goodValidator.isValidEmail(email));
        }
        
        // 自动装箱示例
        System.out.println("\n=== 统计计算性能对比 ===");
        int[] numbers = new int[1_000_000];
        Integer[] boxedNumbers = new Integer[1_000_000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
            boxedNumbers[i] = i;
        }
        
        start = System.nanoTime();
        long result1 = Statistics.calculateTotalBad(boxedNumbers);
        long badStatTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        long result2 = Statistics.calculateTotalGood(numbers);
        long goodStatTime = System.nanoTime() - start;
        
        System.out.println("包装类型耗时: " + badStatTime / 1_000_000 + " ms");
        System.out.println("基本类型耗时: " + goodStatTime / 1_000_000 + " ms");
        System.out.println("性能提升: " + (badStatTime / goodStatTime) + "x");
    }
}
