package chapter2.item4;

/**
 * 演示 Effective Java 第4条：通过私有构造器强化不可实例化的能力
 * 
 * 正确示例：使用私有构造器
 * 优势：
 * 1. 防止类被实例化
 * 2. 防止类被继承（子类无法调用父类构造器）
 * 3. 明确表达设计意图
 * 
 * @author CN-JeffreyZhou
 */
public class Item4_GoodExample {
    
    /**
     * 正确的工具类：私有构造器
     */
    static class AnimalUtils {
        // 私有构造器，防止实例化
        private AnimalUtils() {
            throw new AssertionError("工具类不应该被实例化");
        }
        
        public static String getSound(String animal) {
            return switch (animal) {
                case "狗" -> "汪汪";
                case "猫" -> "喵喵";
                default -> "未知";
            };
        }
        
        public static int getLegs(String animal) {
            return switch (animal) {
                case "狗", "猫" -> 4;
                case "鸟" -> 2;
                default -> 0;
            };
        }
    }
    
    /**
     * 正确的数学工具类
     */
    static class MathUtils {
        private MathUtils() {
            throw new AssertionError();
        }
        
        public static int add(int a, int b) {
            return a + b;
        }
        
        public static int multiply(int a, int b) {
            return a * b;
        }
    }
    
    // 尝试继承会编译失败
    // static class SubUtils extends AnimalUtils {
    //     // 编译错误：无法访问父类的私有构造器
    // }
    
    public static void main(String[] args) {
        // 正确用法：直接调用静态方法
        System.out.println("✅ 正确用法：直接调用静态方法");
        System.out.println("狗的叫声: " + AnimalUtils.getSound("狗"));
        System.out.println("猫有几条腿: " + AnimalUtils.getLegs("猫"));
        System.out.println("1 + 2 = " + MathUtils.add(1, 2));
        System.out.println("3 × 4 = " + MathUtils.multiply(3, 4));
        
        // 尝试实例化会编译失败
        // AnimalUtils utils = new AnimalUtils();  // 编译错误！
        // MathUtils math = new MathUtils();       // 编译错误！
        
        System.out.println("\n✅ 私有构造器成功阻止了实例化和继承");
    }
}
