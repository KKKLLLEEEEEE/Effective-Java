package chapter2.item1;

/**
 * 演示 Effective Java 第1条：用静态工厂方法代替构造器
 * 
 * 优势3：可以返回原返回类型的任何子类型对象
 * 
 * @author CN-JeffreyZhou
 */
public class Item1_Advantage3_ReturnSubtype {
    
    interface Animal {
        void makeSound();
    }
    
    static class Dog implements Animal {
        @Override
        public void makeSound() {
            System.out.println("汪汪汪！");
        }
    }
    
    static class Cat implements Animal {
        @Override
        public void makeSound() {
            System.out.println("喵喵喵！");
        }
    }
    
    static class AnimalFactory {
        public static Animal create(String type) {
            if ("狗".equals(type)) return new Dog();
            if ("猫".equals(type)) return new Cat();
            throw new IllegalArgumentException("未知类型");
        }
    }
    
    public static void main(String[] args) {
        Animal dog = AnimalFactory.create("狗");
        Animal cat = AnimalFactory.create("猫");
        
        dog.makeSound();
        cat.makeSound();
        
        System.out.println("\n客户端只需知道Animal接口，无需知道具体实现类");
    }
}
