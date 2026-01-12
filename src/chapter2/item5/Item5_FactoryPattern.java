package chapter2.item5;

import java.util.function.Supplier;

/**
 * 演示 Effective Java 第5条：优先考虑依赖注入来引用资源
 * 
 * 高级示例：工厂模式的依赖注入
 * 使用 Supplier<T> 作为工厂，延迟创建资源
 * 
 * @author CN-JeffreyZhou
 */
public class Item5_FactoryPattern {

    // 动物类
    static class Animal {
        private final String name;
        private final String type;
        
        public Animal(String name, String type) {
            this.name = name;
            this.type = type;
        }
        
        @Override
        public String toString() {
            return type + "：" + name;
        }
    }

    // 动物收容所：接受工厂来创建动物
    static class AnimalShelter {
        private final Supplier<? extends Animal> animalFactory;
        
        // 注入工厂而非具体对象
        public AnimalShelter(Supplier<? extends Animal> animalFactory) {
            this.animalFactory = animalFactory;
        }
        
        public Animal rescueAnimal() {
            // 每次调用工厂创建新动物
            return animalFactory.get();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 工厂模式的依赖注入 ===\n");
        
        // 使用 lambda 表达式作为工厂
        AnimalShelter dogShelter = new AnimalShelter(() -> new Animal("旺财", "狗"));
        AnimalShelter catShelter = new AnimalShelter(() -> new Animal("咪咪", "猫"));
        
        System.out.println("狗收容所救助：" + dogShelter.rescueAnimal());
        System.out.println("狗收容所救助：" + dogShelter.rescueAnimal());
        
        System.out.println("\n猫收容所救助：" + catShelter.rescueAnimal());
        System.out.println("猫收容所救助：" + catShelter.rescueAnimal());
        
        System.out.println("\n✅ 优势：每次调用都创建新对象，灵活控制对象生命周期");
    }
}
