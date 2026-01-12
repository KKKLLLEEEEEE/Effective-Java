package chapter2.item2;

/**
 * 演示 Effective Java 第2条：遇到多个构造器参数时要考虑使用构建器
 * 
 * 问题1：重叠构造器模式（Telescoping Constructor Pattern）
 * - 参数多时难以阅读和编写
 * - 容易搞错参数顺序
 * 
 * @author CN-JeffreyZhou
 */
public class Item2_TelescopingConstructor {
    
    /**
     * 反例：重叠构造器模式
     * 动物营养信息类 - 有很多可选参数
     */
    static class AnimalNutrition {
        private final String name;           // 必需
        private final int age;               // 必需
        private final int calories;          // 可选
        private final int fat;               // 可选
        private final int protein;           // 可选
        private final int vitamins;          // 可选
        
        public AnimalNutrition(String name, int age) {
            this(name, age, 0);
        }
        
        public AnimalNutrition(String name, int age, int calories) {
            this(name, age, calories, 0);
        }
        
        public AnimalNutrition(String name, int age, int calories, int fat) {
            this(name, age, calories, fat, 0);
        }
        
        public AnimalNutrition(String name, int age, int calories, int fat, int protein) {
            this(name, age, calories, fat, protein, 0);
        }
        
        public AnimalNutrition(String name, int age, int calories, int fat, int protein, int vitamins) {
            this.name = name;
            this.age = age;
            this.calories = calories;
            this.fat = fat;
            this.protein = protein;
            this.vitamins = vitamins;
        }
        
        @Override
        public String toString() {
            return String.format("%s(%d岁) - 卡路里:%d, 脂肪:%d, 蛋白质:%d, 维生素:%d",
                    name, age, calories, fat, protein, vitamins);
        }
    }
    
    public static void main(String[] args) {
        // 问题：参数太多，难以阅读，容易出错
        AnimalNutrition dog = new AnimalNutrition("狗", 3, 500, 20, 30, 10);
        System.out.println("重叠构造器模式：" + dog);
        
        // 问题：只想设置卡路里和维生素，但必须传入中间的参数
        AnimalNutrition cat = new AnimalNutrition("猫", 2, 400, 0, 0, 15);
        System.out.println("被迫传入不需要的参数：" + cat);
    }
}
