package chapter2.item3;

/**
 * 演示 Effective Java 第3条：用私有构造器或者枚举类型强化Singleton属性
 * 
 * 方式2：静态工厂方法
 * 优点：灵活性高，可以改变是否为单例而不改变API；可以编写泛型单例工厂
 * 缺点：需要额外方法
 * 
 * @author CN-JeffreyZhou
 */
public class Item3_Approach2_StaticFactory {
    
    // 单例动物管理员
    public static class ZooKeeper {
        private static final ZooKeeper INSTANCE = new ZooKeeper();
        
        private String name = "资深饲养员";
        
        private ZooKeeper() {
            System.out.println("饲养员上岗...");
        }
        
        // 静态工厂方法 - 提供访问单例的接口
        public static ZooKeeper getInstance() {
            return INSTANCE;
        }
        
        public void feedAnimals() {
            System.out.println(name + "正在喂养动物");
        }
    }
    
    public static void main(String[] args) {
        // 通过静态工厂方法获取单例
        ZooKeeper keeper1 = ZooKeeper.getInstance();
        ZooKeeper keeper2 = ZooKeeper.getInstance();
        
        keeper1.feedAnimals();
        
        // 验证是同一个实例
        System.out.println("keeper1 == keeper2: " + (keeper1 == keeper2)); // true
    }
}
