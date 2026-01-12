package chapter2.item2;

/**
 * 演示 Effective Java 第2条：遇到多个构造器参数时要考虑使用构建器
 * 
 * 问题2：JavaBeans 模式
 * - 对象在构造过程中可能处于不一致状态
 * - 无法将类做成不可变的
 * - 需要额外努力确保线程安全
 * 
 * @author CN-JeffreyZhou
 */
public class Item2_JavaBeans {
    
    /**
     * 反例：JavaBeans 模式
     */
    static class AnimalNutrition {
        private String name;           // 必需，但可能未设置
        private int age;               // 必需，但可能未设置
        private int calories = 0;      // 可选
        private int fat = 0;           // 可选
        private int protein = 0;       // 可选
        private int vitamins = 0;      // 可选
        
        public AnimalNutrition() {}
        
        public void setName(String name) { this.name = name; }
        public void setAge(int age) { this.age = age; }
        public void setCalories(int calories) { this.calories = calories; }
        public void setFat(int fat) { this.fat = fat; }
        public void setProtein(int protein) { this.protein = protein; }
        public void setVitamins(int vitamins) { this.vitamins = vitamins; }
        
        @Override
        public String toString() {
            return String.format("%s(%d岁) - 卡路里:%d, 脂肪:%d, 蛋白质:%d, 维生素:%d",
                    name, age, calories, fat, protein, vitamins);
        }
    }
    
    public static void main(String[] args) {
        // 问题1：对象在多次调用中处于不一致状态
        AnimalNutrition dog = new AnimalNutrition();
        // 此时 dog 的 name 和 age 都是 null/0，对象不完整！
        dog.setName("狗");
        dog.setAge(3);
        dog.setCalories(500);
        dog.setProtein(30);
        System.out.println("JavaBeans模式：" + dog);
        
        // 问题2：代码冗长
        AnimalNutrition cat = new AnimalNutrition();
        cat.setName("猫");
        cat.setAge(2);
        cat.setVitamins(15);
        System.out.println("代码冗长：" + cat);
        
        // 问题3：无法保证必需参数被设置
        AnimalNutrition bird = new AnimalNutrition();
        bird.setCalories(200);  // 忘记设置 name 和 age！
        System.out.println("缺少必需参数：" + bird);  // name=null, age=0
    }
}
