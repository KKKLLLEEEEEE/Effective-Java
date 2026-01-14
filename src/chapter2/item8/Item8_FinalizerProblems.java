package chapter2.item8;

/**
 * 演示 Effective Java 第8条：避免使用终结方法和清除方法
 * 
 * 核心问题：
 * 1. 执行时机不确定 - 可能永远不执行
 * 2. 性能严重下降
 * 3. 可能导致安全问题
 * 
 * @author CN-JeffreyZhou
 */
public class Item8_FinalizerProblems {

    // ❌ 错误示例：使用 finalize 清理资源
    static class BadAnimalShelter {
        private String name;
        
        public BadAnimalShelter(String name) {
            this.name = name;
            System.out.println("收容所 " + name + " 开放");
        }
        
        public void adoptAnimal(String animal) {
            System.out.println(name + " 送出了 " + animal);
        }
        
        @Override
        protected void finalize() throws Throwable {
            try {
                // 问题：不知道何时执行，可能永远不执行
                System.out.println("收容所 " + name + " 关闭（finalize）");
            } finally {
                super.finalize();
            }
        }
    }
    
    // ✅ 正确示例：使用 try-with-resources
    static class GoodAnimalShelter implements AutoCloseable {
        private String name;
        
        public GoodAnimalShelter(String name) {
            this.name = name;
            System.out.println("收容所 " + name + " 开放");
        }
        
        public void adoptAnimal(String animal) {
            System.out.println(name + " 送出了 " + animal);
        }
        
        @Override
        public void close() {
            // 确定性清理：立即执行
            System.out.println("收容所 " + name + " 关闭（close）");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 问题1：finalize 执行时机不确定 ===");
        
        // 错误方式：依赖 finalize
        for (int i = 0; i < 3; i++) {
            BadAnimalShelter bad = new BadAnimalShelter("坏收容所" + i);
            bad.adoptAnimal("狗");
            // 离开作用域，但 finalize 可能不执行
        }
        
        System.out.println("\n尝试触发 GC...");
        System.gc(); // 提示 GC，但不保证执行 finalize
        
        try {
            Thread.sleep(100); // 等待一下
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("注意：finalize 可能没有执行！\n");
        
        // 正确方式：使用 try-with-resources
        System.out.println("=== 正确做法：使用 AutoCloseable ===");
        try (GoodAnimalShelter good = new GoodAnimalShelter("好收容所")) {
            good.adoptAnimal("猫");
        } // close() 自动调用，确定性清理
        
        System.out.println("\n结论：close() 立即执行，finalize 不可靠");
    }
}
