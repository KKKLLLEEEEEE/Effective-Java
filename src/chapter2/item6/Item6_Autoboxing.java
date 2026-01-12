package chapter2.item6;

/**
 * 演示 Effective Java 第6条：避免创建不必要的对象
 * 
 * 要点3：警惕自动装箱（Autoboxing）
 * 优先使用基本类型而非包装类型，避免无意识的自动装箱
 * 
 * @author CN-JeffreyZhou
 */
public class Item6_Autoboxing {
    
    // 错误做法：使用包装类型导致大量自动装箱
    static long sumWithBoxing() {
        Long sum = 0L;  // 注意：这里用的是 Long 而不是 long
        for (long i = 0; i < 10_000_000; i++) {
            sum += i;  // 每次循环都创建一个新的 Long 对象！
        }
        return sum;
    }
    
    // 正确做法：使用基本类型
    static long sumWithPrimitive() {
        long sum = 0L;  // 使用基本类型 long
        for (long i = 0; i < 10_000_000; i++) {
            sum += i;  // 没有对象创建，纯粹的算术运算
        }
        return sum;
    }
    
    // 动物示例：统计动物数量
    static class AnimalCounter {
        // 错误：使用包装类型
        static Integer countBadWay(String[] animals, String target) {
            Integer count = 0;  // 包装类型
            for (String animal : animals) {
                if (animal.equals(target)) {
                    count++;  // 自动装箱：count = Integer.valueOf(count.intValue() + 1)
                }
            }
            return count;
        }
        
        // 正确：使用基本类型
        static int countGoodWay(String[] animals, String target) {
            int count = 0;  // 基本类型
            for (String animal : animals) {
                if (animal.equals(target)) {
                    count++;  // 纯粹的算术运算
                }
            }
            return count;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 自动装箱性能对比 ===");
        
        // 测试求和性能
        long start = System.nanoTime();
        long result1 = sumWithBoxing();
        long badTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        long result2 = sumWithPrimitive();
        long goodTime = System.nanoTime() - start;
        
        System.out.println("包装类型耗时: " + badTime / 1_000_000 + " ms");
        System.out.println("基本类型耗时: " + goodTime / 1_000_000 + " ms");
        System.out.println("性能提升: " + (badTime / goodTime) + "x");
        System.out.println("结果验证: " + (result1 == result2));
        
        // 动物计数示例
        System.out.println("\n=== 动物计数示例 ===");
        String[] zoo = new String[1_000_000];
        for (int i = 0; i < zoo.length; i++) {
            zoo[i] = (i % 3 == 0) ? "狗" : (i % 3 == 1) ? "猫" : "鸟";
        }
        
        start = System.nanoTime();
        int dogCount = AnimalCounter.countGoodWay(zoo, "狗");
        long countTime = System.nanoTime() - start;
        
        System.out.println("狗的数量: " + dogCount);
        System.out.println("统计耗时: " + countTime / 1_000_000 + " ms");
        System.out.println("\n教训：优先使用基本类型（int, long）而非包装类型（Integer, Long）");
    }
}
