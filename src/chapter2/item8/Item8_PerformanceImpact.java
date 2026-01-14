package chapter2.item8;

/**
 * 演示 Effective Java 第8条：终结方法的性能问题
 * 
 * 核心问题：使用 finalize 会导致性能严重下降（慢50倍以上）
 * 
 * @author CN-JeffreyZhou
 */
public class Item8_PerformanceImpact {

    // ❌ 使用 finalize 的类
    static class AnimalWithFinalizer {
        private String name;
        
        public AnimalWithFinalizer(String name) {
            this.name = name;
        }
        
        @Override
        protected void finalize() throws Throwable {
            // 即使是空的 finalize 也会严重影响性能
            super.finalize();
        }
    }
    
    // ✅ 不使用 finalize 的类
    static class AnimalWithoutFinalizer {
        private String name;
        
        public AnimalWithoutFinalizer(String name) {
            this.name = name;
        }
    }
    
    public static void main(String[] args) {
        int iterations = 1_000_000;
        
        // 测试不使用 finalize
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            AnimalWithoutFinalizer animal = new AnimalWithoutFinalizer("狗" + i);
        }
        long withoutFinalize = System.nanoTime() - start;
        
        // 测试使用 finalize
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            AnimalWithFinalizer animal = new AnimalWithFinalizer("猫" + i);
        }
        long withFinalize = System.nanoTime() - start;
        
        System.out.println("=== 性能对比（创建 " + iterations + " 个对象）===");
        System.out.printf("不使用 finalize: %.2f ms%n", withoutFinalize / 1_000_000.0);
        System.out.printf("使用 finalize:    %.2f ms%n", withFinalize / 1_000_000.0);
        System.out.printf("性能下降: %.1f 倍%n", (double) withFinalize / withoutFinalize);
        System.out.println("\n结论：finalize 会导致性能严重下降！");
    }
}
