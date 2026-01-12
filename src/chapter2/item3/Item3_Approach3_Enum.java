package chapter2.item3;

/**
 * 演示 Effective Java 第3条：用私有构造器或者枚举类型强化Singleton属性
 * 
 * 方式3：单元素枚举（最佳实践）
 * 优点：简洁、自动支持序列化、绝对防止多次实例化（即使面对反射和序列化攻击）
 * 缺点：如果Singleton必须扩展超类（而不是Enum），则不能使用此方法
 * 
 * @author CN-JeffreyZhou
 */
public class Item3_Approach3_Enum {
    
    // 单例动物收容所 - 使用枚举实现
    public enum AnimalShelter {
        INSTANCE; // 唯一实例
        
        private int animalCount = 0;
        
        public void rescue(String animal) {
            animalCount++;
            System.out.println("收容了一只" + animal + "，当前共有" + animalCount + "只动物");
        }
        
        public void showStatus() {
            System.out.println("收容所当前有" + animalCount + "只动物");
        }
    }
    
    public static void main(String[] args) {
        // 使用枚举单例
        AnimalShelter shelter1 = AnimalShelter.INSTANCE;
        AnimalShelter shelter2 = AnimalShelter.INSTANCE;
        
        shelter1.rescue("流浪狗");
        shelter2.rescue("流浪猫");
        shelter1.showStatus();
        
        // 验证是同一个实例
        System.out.println("shelter1 == shelter2: " + (shelter1 == shelter2)); // true
    }
}
