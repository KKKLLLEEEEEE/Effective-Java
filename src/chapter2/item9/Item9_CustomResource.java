package chapter2.item9;

/**
 * 演示 Effective Java 第9条：自定义资源类
 * 
 * 要点：实现 AutoCloseable 接口即可使用 try-with-resources
 * 
 * @author CN-JeffreyZhou
 */
public class Item9_CustomResource {

    /**
     * 自定义资源：动物园
     * 实现 AutoCloseable 接口
     */
    static class Zoo implements AutoCloseable {
        private final String name;

        public Zoo(String name) {
            this.name = name;
            System.out.println("🏛️  " + name + " 开门营业");
        }

        public void feedAnimals() {
            System.out.println("🍖 喂养动物中...");
        }

        @Override
        public void close() {
            System.out.println("🔒 " + name + " 关门，清理资源");
        }
    }

    /**
     * 使用自定义资源
     */
    static void visitZoo() {
        try (Zoo zoo = new Zoo("北京动物园")) {
            zoo.feedAnimals();
            // 自动调用 close()，无需手动关闭
        }
        System.out.println("✅ 资源已自动释放\n");
    }

    /**
     * 多个自定义资源
     */
    static class AnimalFeeder implements AutoCloseable {
        private final String type;

        public AnimalFeeder(String type) {
            this.type = type;
            System.out.println("  📦 准备" + type + "饲料");
        }

        @Override
        public void close() {
            System.out.println("  🧹 清理" + type + "饲料容器");
        }
    }

    static void feedMultipleTypes() {
        try (Zoo zoo = new Zoo("上海动物园");
             AnimalFeeder meatFeeder = new AnimalFeeder("肉类");
             AnimalFeeder vegFeeder = new AnimalFeeder("蔬菜")) {
            
            System.out.println("🦁 喂养肉食动物");
            System.out.println("🐘 喂养草食动物");
            
            // 资源会按相反顺序关闭：vegFeeder -> meatFeeder -> zoo
        }
        System.out.println("✅ 所有资源按正确顺序释放");
    }

    public static void main(String[] args) {
        System.out.println("=== 自定义资源类示例 ===\n");
        
        System.out.println("示例1：单个自定义资源");
        visitZoo();
        
        System.out.println("示例2：多个自定义资源");
        feedMultipleTypes();
        
        System.out.println("\n💡 要点：");
        System.out.println("1. 实现 AutoCloseable 接口");
        System.out.println("2. 在 close() 方法中释放资源");
        System.out.println("3. 资源按声明的相反顺序关闭");
    }
}
