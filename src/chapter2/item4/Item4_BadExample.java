package chapter2.item4;

/**
 * 演示 Effective Java 第4条：通过私有构造器强化不可实例化的能力
 * 
 * 错误示例：工具类没有私有构造器
 * 问题：
 * 1. 可以被实例化（没有意义）
 * 2. 可以被继承（违背设计意图）
 * 
 * @author CN-JeffreyZhou
 */
public class Item4_BadExample {
    
    /**
     * 错误的工具类：没有私有构造器
     */
    static class AnimalUtils {
        // 编译器会自动生成默认的 public 构造器
        
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
     * 错误的工具类：显式提供了 public 构造器
     */
    static class MathUtils {
        public MathUtils() {
            // 这个构造器毫无意义，但可以被调用
        }
        
        public static int add(int a, int b) {
            return a + b;
        }
    }
    
    /**
     * 错误的工具类：抽象类也无法阻止实例化
     */
    static abstract class AbstractUtils {
        public static String format(String text) {
            return "[" + text + "]";
        }
    }
    
    // 抽象类可以被子类化，然后实例化
    static class ConcreteUtils extends AbstractUtils {
        // 子类可以被实例化
    }
    
    public static void main(String[] args) {
        // 问题1：工具类可以被实例化（毫无意义）
        AnimalUtils utils1 = new AnimalUtils();  // 编译通过！
        MathUtils utils2 = new MathUtils();      // 编译通过！
        
        System.out.println("❌ 错误：工具类被实例化了");
        System.out.println("utils1: " + utils1);
        System.out.println("utils2: " + utils2);
        
        // 问题2：抽象类可以通过子类实例化
        ConcreteUtils utils3 = new ConcreteUtils();  // 编译通过！
        System.out.println("❌ 错误：抽象工具类的子类被实例化了");
        System.out.println("utils3: " + utils3);
        
        // 正确用法：直接调用静态方法
        System.out.println("\n✅ 正确用法：");
        System.out.println("狗的叫声: " + AnimalUtils.getSound("狗"));
        System.out.println("1 + 2 = " + MathUtils.add(1, 2));
    }
}
