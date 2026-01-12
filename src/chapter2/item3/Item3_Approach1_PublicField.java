package chapter2.item3;

/**
 * 演示 Effective Java 第3条：用私有构造器或者枚举类型强化Singleton属性
 * 
 * 方式1：公有静态final域
 * 优点：简洁明了，API清晰表明这是单例
 * 缺点：无法防御反射攻击
 * 
 * @author CN-JeffreyZhou
 */
public class Item3_Approach1_PublicField {
    
    // 单例动物园 - 全球只有一个
    public static class Zoo {
        // 公有静态final域 - 唯一实例
        public static final Zoo INSTANCE = new Zoo();
        
        private String name = "全球唯一动物园";
        
        // 私有构造器 - 防止外部创建实例
        private Zoo() {
            System.out.println("动物园初始化...");
        }
        
        public void showInfo() {
            System.out.println("欢迎来到：" + name);
        }
    }
    
    public static void main(String[] args) {
        // 获取单例
        Zoo zoo1 = Zoo.INSTANCE;
        Zoo zoo2 = Zoo.INSTANCE;
        
        zoo1.showInfo();
        
        // 验证是同一个实例
        System.out.println("zoo1 == zoo2: " + (zoo1 == zoo2)); // true
    }
}
