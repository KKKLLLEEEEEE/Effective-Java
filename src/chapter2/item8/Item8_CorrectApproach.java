package chapter2.item8;

import java.io.*;

/**
 * 演示 Effective Java 第8条：正确的资源清理方式
 * 
 * 推荐做法：
 * 1. 实现 AutoCloseable 接口
 * 2. 使用 try-with-resources
 * 3. Cleaner 作为安全网（不是主要清理手段）
 * 
 * @author CN-JeffreyZhou
 */
public class Item8_CorrectApproach {

    // ✅ 正确示例：管理动物医疗记录文件
    static class AnimalMedicalRecord implements AutoCloseable {
        private final String animalName;
        private final BufferedWriter writer;
        
        public AnimalMedicalRecord(String animalName, String filename) throws IOException {
            this.animalName = animalName;
            this.writer = new BufferedWriter(new FileWriter(filename));
            System.out.println("打开 " + animalName + " 的医疗记录");
        }
        
        public void addRecord(String record) throws IOException {
            writer.write(record);
            writer.newLine();
        }
        
        @Override
        public void close() throws IOException {
            // 确定性清理资源
            System.out.println("关闭 " + animalName + " 的医疗记录");
            writer.close();
        }
    }
    
    // ✅ 使用 Cleaner 作为安全网（Java 9+）
    static class AnimalCage implements AutoCloseable {
        private static final java.lang.ref.Cleaner cleaner = java.lang.ref.Cleaner.create();
        
        // 清理状态（不能引用外部类）
        private static class State implements Runnable {
            String cageName;
            
            State(String cageName) {
                this.cageName = cageName;
            }
            
            @Override
            public void run() {
                // 安全网：如果客户端忘记调用 close()
                System.out.println("⚠️ 安全网触发：清理笼子 " + cageName);
            }
        }
        
        private final State state;
        private final java.lang.ref.Cleaner.Cleanable cleanable;
        
        public AnimalCage(String cageName) {
            this.state = new State(cageName);
            this.cleanable = cleaner.register(this, state);
            System.out.println("笼子 " + cageName + " 准备好了");
        }
        
        @Override
        public void close() {
            // 正常清理
            cleanable.clean();
            System.out.println("正常关闭笼子 " + state.cageName);
        }
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("=== 正确做法1：try-with-resources ===");
        
        // 自动调用 close()，确保资源释放
        try (AnimalMedicalRecord record = new AnimalMedicalRecord("狗狗", "dog_record.txt")) {
            record.addRecord("2024-01-15: 疫苗接种");
            record.addRecord("2024-01-20: 健康检查");
        } // close() 自动调用
        
        System.out.println("\n=== 正确做法2：Cleaner 作为安全网 ===");
        
        // 正常使用：调用 close()
        try (AnimalCage cage1 = new AnimalCage("1号笼")) {
            System.out.println("使用笼子...");
        }
        
        // 忘记调用 close()：Cleaner 作为安全网
        AnimalCage cage2 = new AnimalCage("2号笼");
        cage2 = null; // 忘记 close
        System.gc(); // Cleaner 可能会清理
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n总结：");
        System.out.println("1. 优先使用 try-with-resources");
        System.out.println("2. Cleaner 只是安全网，不能依赖");
        System.out.println("3. 永远不要依赖 finalize 或 cleaner 做主要清理");
    }
}
