package chapter2.item6;

import java.util.*;

/**
 * 演示 Effective Java 第6条：避免创建不必要的对象
 * 
 * 要点4：适配器（视图）无需重复创建
 * Map.keySet() 返回的是同一个 Set 视图，无需缓存
 * 
 * @author CN-JeffreyZhou
 */
public class Item6_AdapterPattern {
    
    public static void main(String[] args) {
        System.out.println("=== 适配器模式：视图对象重用 ===");
        
        // 创建一个动物园地图
        Map<String, String> zoo = new HashMap<>();
        zoo.put("区域A", "狗");
        zoo.put("区域B", "猫");
        zoo.put("区域C", "鸟");
        
        // 多次调用 keySet() 返回的是同一个 Set 视图
        Set<String> keys1 = zoo.keySet();
        Set<String> keys2 = zoo.keySet();
        Set<String> keys3 = zoo.keySet();
        
        // 验证是同一个对象
        System.out.println("keys1 == keys2: " + (keys1 == keys2));
        System.out.println("keys2 == keys3: " + (keys2 == keys3));
        
        // 修改视图会影响原 Map
        System.out.println("\n修改前的动物园: " + zoo);
        keys1.remove("区域A");
        System.out.println("通过 keySet 删除后: " + zoo);
        
        // 修改 Map 会反映在视图中
        zoo.put("区域D", "兔子");
        System.out.println("添加新区域后，keys1 的内容: " + keys1);
        
        System.out.println("\n教训：");
        System.out.println("1. keySet() 每次返回同一个视图对象，无需缓存");
        System.out.println("2. 视图对象是原对象的'窗口'，不是独立副本");
        System.out.println("3. 不要担心重复调用 keySet()，它很轻量");
    }
}
