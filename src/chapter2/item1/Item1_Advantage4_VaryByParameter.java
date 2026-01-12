package chapter2.item1;

/**
 * 演示 Effective Java 第1条：用静态工厂方法代替构造器
 * 
 * 优势4：返回对象的类可以随着每次调用而发生变化，取决于参数
 * 
 * @author CN-JeffreyZhou
 */
public class Item1_Advantage4_VaryByParameter {
    
    interface FeedingStrategy {
        void feed();
    }
    
    static class MeatFeeding implements FeedingStrategy {
        @Override
        public void feed() {
            System.out.println("喂食肉类");
        }
    }
    
    static class VegetableFeeding implements FeedingStrategy {
        @Override
        public void feed() {
            System.out.println("喂食蔬菜");
        }
    }
    
    static class MixedFeeding implements FeedingStrategy {
        @Override
        public void feed() {
            System.out.println("喂食混合食物");
        }
    }
    
    static class FeedingFactory {
        public static FeedingStrategy getStrategy(String animalType) {
            switch (animalType) {
                case "狗": return new MixedFeeding();
                case "猫": return new MeatFeeding();
                case "兔": return new VegetableFeeding();
                default: throw new IllegalArgumentException("未知动物");
            }
        }
    }
    
    public static void main(String[] args) {
        FeedingStrategy dogFeeding = FeedingFactory.getStrategy("狗");
        FeedingStrategy catFeeding = FeedingFactory.getStrategy("猫");
        FeedingStrategy rabbitFeeding = FeedingFactory.getStrategy("兔");
        
        System.out.print("狗: "); dogFeeding.feed();
        System.out.print("猫: "); catFeeding.feed();
        System.out.print("兔: "); rabbitFeeding.feed();
    }
}
