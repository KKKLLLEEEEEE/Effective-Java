package chapter2.item1;

/**
 * 演示 Effective Java 第1条：用静态工厂方法代替构造器
 * 
 * 优势1：静态工厂方法有名称，更易理解
 * 
 * @author CN-JeffreyZhou
 */
public class Item1_Advantage1_NamedMethod {
    
    static class Animal {
        private final String species;
        private final double weight;
        private final int age;
        
        private Animal(String species, double weight, int age) {
            this.species = species;
            this.weight = weight;
            this.age = age;
        }
        
        // 从体重和年龄创建
        public static Animal fromWeightAndAge(String species, double weight, int age) {
            return new Animal(species, weight, age);
        }
        
        // 创建幼年动物（年龄为0）
        public static Animal newborn(String species, double weight) {
            return new Animal(species, weight, 0);
        }
        
        // 创建成年动物（使用标准体重）
        public static Animal adult(String species) {
            double standardWeight = species.equals("狗") ? 20.0 : 4.0;
            return new Animal(species, standardWeight, 5);
        }
        
        @Override
        public String toString() {
            return String.format("%s(体重:%.1fkg, 年龄:%d岁)", species, weight, age);
        }
    }
    
    public static void main(String[] args) {
        Animal dog1 = Animal.fromWeightAndAge("狗", 25.0, 3);
        Animal dog2 = Animal.newborn("狗", 2.5);
        Animal cat = Animal.adult("猫");
        
        System.out.println("方法名清晰表达创建意图：");
        System.out.println(dog1);
        System.out.println(dog2);
        System.out.println(cat);
    }
}
