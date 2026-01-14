package chapter2.item7;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 演示 Effective Java 第7条：消除过期的对象引用
 * 
 * 核心原则：缓存容易导致内存泄漏
 * 
 * 解决方案：使用 WeakHashMap，当键不再被外部引用时自动删除
 * 
 * @author CN-JeffreyZhou
 */
public class Item7_CacheMemoryLeak {

    /**
     * 错误示例：使用普通 HashMap 作为缓存
     */
    static class BadCache {
        // 问题：即使外部不再使用，缓存仍持有对象引用
        private final Map<Animal, String> cache = new HashMap<>();

        public void put(Animal key, String value) {
            cache.put(key, value);
            System.out.println("缓存: " + key.getName() + " -> " + value);
        }

        public String get(Animal key) {
            return cache.get(key);
        }

        public int size() {
            return cache.size();
        }
    }

    /**
     * 正确示例：使用 WeakHashMap 作为缓存
     */
    static class GoodCache {
        // 正确：键不再被外部引用时，自动从缓存中删除
        private final Map<Animal, String> cache = new WeakHashMap<>();

        public void put(Animal key, String value) {
            cache.put(key, value);
            System.out.println("缓存: " + key.getName() + " -> " + value);
        }

        public String get(Animal key) {
            return cache.get(key);
        }

        public int size() {
            return cache.size();
        }
    }

    static class Animal {
        private final String name;

        Animal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 缓存内存泄漏演示 ===\n");

        System.out.println("1. 错误示例（HashMap）：");
        BadCache badCache = new BadCache();
        Animal dog = new Animal("狗");
        Animal cat = new Animal("猫");
        
        badCache.put(dog, "汪汪叫");
        badCache.put(cat, "喵喵叫");
        System.out.println("缓存大小: " + badCache.size());
        
        dog = null; // 外部不再引用
        cat = null;
        System.gc(); // 建议垃圾回收
        Thread.sleep(100);
        
        System.out.println("外部引用清空后，缓存大小: " + badCache.size());
        System.out.println("问题：缓存仍持有对象，导致内存泄漏\n");

        System.out.println("2. 正确示例（WeakHashMap）：");
        GoodCache goodCache = new GoodCache();
        Animal bird = new Animal("鸟");
        Animal fish = new Animal("鱼");
        
        goodCache.put(bird, "叽叽喳喳");
        goodCache.put(fish, "游来游去");
        System.out.println("缓存大小: " + goodCache.size());
        
        bird = null; // 外部不再引用
        fish = null;
        System.gc(); // 建议垃圾回收
        Thread.sleep(100);
        
        System.out.println("外部引用清空后，缓存大小: " + goodCache.size());
        System.out.println("正确：WeakHashMap 自动清理不再使用的条目");
    }
}
