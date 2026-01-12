package chapter2.item5;

import java.util.*;

/**
 * 演示 Effective Java 第5条：优先考虑依赖注入来引用资源
 * 
 * 真实场景：拼写检查器（书中原例）
 * 展示依赖注入在实际应用中的价值
 * 
 * @author CN-JeffreyZhou
 */
public class Item5_RealWorld {

    // 词典接口
    interface Lexicon {
        boolean contains(String word);
        Set<String> getSuggestions(String word);
    }

    // 英文词典
    static class EnglishLexicon implements Lexicon {
        private final Set<String> words = new HashSet<>(Arrays.asList(
            "hello", "world", "java", "code", "test"
        ));
        
        @Override
        public boolean contains(String word) {
            return words.contains(word.toLowerCase());
        }
        
        @Override
        public Set<String> getSuggestions(String word) {
            return new HashSet<>(Arrays.asList("hello", "help"));
        }
    }

    // 中文词典
    static class ChineseLexicon implements Lexicon {
        private final Set<String> words = new HashSet<>(Arrays.asList(
            "你好", "世界", "编程", "代码", "测试"
        ));
        
        @Override
        public boolean contains(String word) {
            return words.contains(word);
        }
        
        @Override
        public Set<String> getSuggestions(String word) {
            return new HashSet<>(Arrays.asList("你好", "您好"));
        }
    }

    // ✅ 拼写检查器：依赖注入词典
    static class SpellChecker {
        private final Lexicon dictionary;
        
        // 通过构造器注入词典资源
        public SpellChecker(Lexicon dictionary) {
            this.dictionary = Objects.requireNonNull(dictionary);
        }
        
        public boolean isValid(String word) {
            return dictionary.contains(word);
        }
        
        public Set<String> suggestions(String word) {
            return dictionary.getSuggestions(word);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 真实场景：拼写检查器 ===\n");
        
        // 英文拼写检查
        SpellChecker englishChecker = new SpellChecker(new EnglishLexicon());
        System.out.println("英文检查 'hello': " + englishChecker.isValid("hello"));
        System.out.println("英文检查 'helo': " + englishChecker.isValid("helo"));
        System.out.println("建议: " + englishChecker.suggestions("helo"));
        
        System.out.println();
        
        // 中文拼写检查
        SpellChecker chineseChecker = new SpellChecker(new ChineseLexicon());
        System.out.println("中文检查 '你好': " + chineseChecker.isValid("你好"));
        System.out.println("中文检查 '您好': " + chineseChecker.isValid("您好"));
        System.out.println("建议: " + chineseChecker.suggestions("您好"));
        
        System.out.println("\n✅ 同一个 SpellChecker 类支持多种语言，无需修改代码");
    }
}
