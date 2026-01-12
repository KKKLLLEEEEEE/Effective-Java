package chapter2.item1;

/**
 * 演示 Effective Java 第1条：用静态工厂方法代替构造器
 * 
 * 优势2：不必每次调用时都创建新对象
 * 
 * @author CN-JeffreyZhou
 */
public class Item1_Advantage2_InstanceControl {
    
    static class AnimalType {
        public static final AnimalType DOG = new AnimalType("狗");
        public static final AnimalType CAT = new AnimalType("猫");
        public static final AnimalType BIRD = new AnimalType("鸟");
        
        private final String name;
        
        private AnimalType(String name) {
            this.name = name;
        }
        
        public static AnimalType valueOf(String name) {
            switch (name) {
                case "狗": return DOG;
                case "猫": return CAT;
                case "鸟": return BIRD;
                default: throw new IllegalArgumentException("未知动物类型");
            }
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    public static void main(String[] args) {
        AnimalType t1 = AnimalType.valueOf("狗");
        AnimalType t2 = AnimalType.valueOf("狗");
        
        System.out.println("t1 == t2: " + (t1 == t2)); // true，返回同一实例
        System.out.println("避免重复创建对象，节省内存");
    }
}
