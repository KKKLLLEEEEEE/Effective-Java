package chapter2.item1;

/**
 * 演示 Effective Java 第1条：用静态工厂方法代替构造器
 * 
 * 劣势1：类如果不含公有或受保护的构造器，就不能被子类化
 * 劣势2：静态工厂方法与其他静态方法没有明显区别
 * 
 * @author CN-JeffreyZhou
 */
public class Item1_Disadvantages {
    
    // 劣势1：无法子类化
    static class Animal {
        private final String name;
        
        private Animal(String name) {
            this.name = name;
        }
        
        public static Animal of(String name) {
            return new Animal(name);
        }
        
        public String getName() {
            return name;
        }
    }
    
    // 编译错误！无法继承只有私有构造器的类
    // static class Dog extends Animal {
    //     public Dog(String name) {
    //         super(name); // 错误
    //     }
    // }
    
    // 解决方案：使用组合
    static class Dog {
        private final Animal animal;
        
        private Dog(String name) {
            this.animal = Animal.of(name);
        }
        
        public static Dog of(String name) {
            return new Dog(name);
        }
        
        public String getName() {
            return animal.getName();
        }
    }
    
    // 劣势2：静态工厂方法命名约定
    static class Cat {
        private final String name;
        
        private Cat(String name) {
            this.name = name;
        }
        
        // 常用命名约定
        public static Cat from(String name) { return new Cat(name); }
        public static Cat of(String name) { return new Cat(name); }
        public static Cat valueOf(String name) { return new Cat(name); }
        public static Cat getInstance(String name) { return new Cat(name); }
        public static Cat newInstance(String name) { return new Cat(name); }
    }
    
    public static void main(String[] args) {
        Animal animal = Animal.of("动物");
        Dog dog = Dog.of("旺财");
        
        System.out.println("劣势1：无法子类化，但可用组合替代");
        System.out.println("劣势2：需遵循命名约定（from/of/valueOf/getInstance等）");
    }
}
