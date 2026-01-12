package chapter2.item5;

/**
 * 演示 Effective Java 第5条：优先考虑依赖注入来引用资源
 * 
 * 正面示例：使用依赖注入（推荐）
 * 优势：灵活、可测试、支持多实例
 * 
 * @author CN-JeffreyZhou
 */
public class Item5_GoodApproach {

    // 词典接口
    interface Dictionary {
        boolean isValid(String word);
    }

    // 陆地动物词典
    static class LandAnimalDictionary implements Dictionary {
        private final String[] words = {"狗", "猫", "马", "牛"};
        
        @Override
        public boolean isValid(String word) {
            for (String w : words) {
                if (w.equals(word)) return true;
            }
            return false;
        }
    }

    // 海洋动物词典
    static class SeaAnimalDictionary implements Dictionary {
        private final String[] words = {"鲸鱼", "海豚", "鲨鱼", "海龟"};
        
        @Override
        public boolean isValid(String word) {
            for (String w : words) {
                if (w.equals(word)) return true;
            }
            return false;
        }
    }

    // ✅ 正确方式：通过构造器注入依赖
    static class AnimalValidator {
        private final Dictionary dictionary;  // 依赖的资源
        
        // 依赖注入：通过构造器传入资源
        public AnimalValidator(Dictionary dictionary) {
            this.dictionary = dictionary;
        }
        
        public boolean isValidAnimal(String word) {
            return dictionary.isValid(word);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 正面示例：依赖注入 ===\n");
        
        // 灵活性：可以使用不同的词典
        AnimalValidator landValidator = new AnimalValidator(new LandAnimalDictionary());
        AnimalValidator seaValidator = new AnimalValidator(new SeaAnimalDictionary());
        
        System.out.println("陆地动物验证器：");
        System.out.println("狗是有效动物？" + landValidator.isValidAnimal("狗"));
        System.out.println("鲸鱼是有效动物？" + landValidator.isValidAnimal("鲸鱼"));
        
        System.out.println("\n海洋动物验证器：");
        System.out.println("鲸鱼是有效动物？" + seaValidator.isValidAnimal("鲸鱼"));
        System.out.println("狗是有效动物？" + seaValidator.isValidAnimal("狗"));
        
        System.out.println("\n✅ 优势：");
        System.out.println("1. 灵活性：可以轻松切换不同的词典");
        System.out.println("2. 可测试性：可以注入 mock 对象进行单元测试");
        System.out.println("3. 支持多实例：可以同时使用多种词典");
    }
}
