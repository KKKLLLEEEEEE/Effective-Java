package chapter2.item5;

/**
 * 演示 Effective Java 第5条：优先考虑依赖注入来引用资源
 * 
 * 反面示例：使用静态工具类和单例模式（不推荐）
 * 问题：硬编码依赖，难以测试，缺乏灵活性
 * 
 * @author CN-JeffreyZhou
 */
public class Item5_BadApproach {

    // ❌ 错误方式1：静态工具类
    static class AnimalDictionary_StaticUtil {
        private static final String[] WORDS = {"狗", "猫", "鸟"};  // 硬编码资源
        
        private AnimalDictionary_StaticUtil() {}  // 不可实例化
        
        public static boolean isValidAnimal(String word) {
            for (String w : WORDS) {
                if (w.equals(word)) return true;
            }
            return false;
        }
    }

    // ❌ 错误方式2：单例模式
    static class AnimalDictionary_Singleton {
        private static final AnimalDictionary_Singleton INSTANCE = new AnimalDictionary_Singleton();
        private final String[] words = {"狗", "猫", "鸟"};  // 硬编码资源
        
        private AnimalDictionary_Singleton() {}
        
        public static AnimalDictionary_Singleton getInstance() {
            return INSTANCE;
        }
        
        public boolean isValidAnimal(String word) {
            for (String w : words) {
                if (w.equals(word)) return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 反面示例：硬编码依赖 ===\n");
        
        // 使用静态工具类
        System.out.println("静态工具类：");
        System.out.println("狗是有效动物？" + AnimalDictionary_StaticUtil.isValidAnimal("狗"));
        System.out.println("问题：无法切换到其他词典（如海洋动物、鸟类等）");
        System.out.println("问题：无法进行单元测试（无法注入 mock 对象）\n");
        
        // 使用单例
        System.out.println("单例模式：");
        AnimalDictionary_Singleton dict = AnimalDictionary_Singleton.getInstance();
        System.out.println("猫是有效动物？" + dict.isValidAnimal("猫"));
        System.out.println("问题：同样缺乏灵活性，无法支持多种词典");
    }
}
