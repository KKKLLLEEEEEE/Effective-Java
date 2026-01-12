package chapter2.item2;

/**
 * 演示 Effective Java 第2条：遇到多个构造器参数时要考虑使用构建器
 * 
 * 解决方案：Builder 模式
 * - 易于阅读和编写
 * - 对象不可变
 * - 保证必需参数被设置
 * - 支持流式调用
 * 
 * @author CN-JeffreyZhou
 */
public class Item2_Builder {
    
    /**
     * 正例：Builder 模式
     */
    static class AnimalNutrition {
        private final String name;           // 必需
        private final int age;               // 必需
        private final int calories;          // 可选
        private final int fat;               // 可选
        private final int protein;           // 可选
        private final int vitamins;          // 可选
        
        public static class Builder {
            // 必需参数
            private final String name;
            private final int age;
            
            // 可选参数 - 初始化为默认值
            private int calories = 0;
            private int fat = 0;
            private int protein = 0;
            private int vitamins = 0;
            
            public Builder(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            public Builder calories(int val) {
                calories = val;
                return this;
            }
            
            public Builder fat(int val) {
                fat = val;
                return this;
            }
            
            public Builder protein(int val) {
                protein = val;
                return this;
            }
            
            public Builder vitamins(int val) {
                vitamins = val;
                return this;
            }
            
            public AnimalNutrition build() {
                return new AnimalNutrition(this);
            }
        }
        
        private AnimalNutrition(Builder builder) {
            name = builder.name;
            age = builder.age;
            calories = builder.calories;
            fat = builder.fat;
            protein = builder.protein;
            vitamins = builder.vitamins;
        }
        
        @Override
        public String toString() {
            return String.format("%s(%d岁) - 卡路里:%d, 脂肪:%d, 蛋白质:%d, 维生素:%d",
                    name, age, calories, fat, protein, vitamins);
        }
    }
    
    public static void main(String[] args) {
        // 优势1：易于阅读，参数含义清晰
        AnimalNutrition dog = new AnimalNutrition.Builder("狗", 3)
                .calories(500)
                .fat(20)
                .protein(30)
                .vitamins(10)
                .build();
        System.out.println("Builder模式：" + dog);
        
        // 优势2：只设置需要的可选参数
        AnimalNutrition cat = new AnimalNutrition.Builder("猫", 2)
                .calories(400)
                .vitamins(15)
                .build();
        System.out.println("灵活设置参数：" + cat);
        
        // 优势3：必需参数在构造器中强制要求
        AnimalNutrition bird = new AnimalNutrition.Builder("鸟", 1)
                .build();
        System.out.println("只设置必需参数：" + bird);
        
        // 优势4：对象不可变，线程安全
        // dog.name = "猫";  // 编译错误！字段是 final 的
    }
}
