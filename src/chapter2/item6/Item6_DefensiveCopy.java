package chapter2.item6;

import java.util.Date;

/**
 * 演示 Effective Java 第6条：避免创建不必要的对象
 * 
 * 要点5：对象池通常不是好主意（除非对象创建非常昂贵）
 * 要点6：不要误用本条目来避免防御性拷贝
 * 
 * 防御性拷贝是为了安全，重用对象是为了性能
 * 当需要防御性拷贝时，不重用对象的代价远小于安全漏洞的代价
 * 
 * @author CN-JeffreyZhou
 */
public class Item6_DefensiveCopy {
    
    // 错误做法：为了避免创建对象而不做防御性拷贝
    static class BadAnimal {
        private Date birthDate;
        
        public BadAnimal(Date birthDate) {
            this.birthDate = birthDate;  // 直接引用，不安全！
        }
        
        public Date getBirthDate() {
            return birthDate;  // 直接返回内部引用，不安全！
        }
    }
    
    // 正确做法：必要时进行防御性拷贝
    static class GoodAnimal {
        private Date birthDate;
        
        public GoodAnimal(Date birthDate) {
            this.birthDate = new Date(birthDate.getTime());  // 防御性拷贝
        }
        
        public Date getBirthDate() {
            return new Date(birthDate.getTime());  // 防御性拷贝
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 防御性拷贝 vs 重用对象 ===");
        
        Date originalDate = new Date();
        System.out.println("原始日期: " + originalDate);
        
        // 错误做法的问题
        System.out.println("\n错误做法（不做防御性拷贝）:");
        BadAnimal badDog = new BadAnimal(originalDate);
        System.out.println("狗的生日: " + badDog.getBirthDate());
        
        // 外部修改会影响内部状态！
        originalDate.setTime(0);
        System.out.println("外部修改日期后，狗的生日变成: " + badDog.getBirthDate());
        
        // 通过 getter 也能修改内部状态！
        badDog.getBirthDate().setTime(System.currentTimeMillis());
        System.out.println("通过 getter 修改后: " + badDog.getBirthDate());
        
        // 正确做法
        System.out.println("\n正确做法（防御性拷贝）:");
        Date safeDate = new Date();
        GoodAnimal goodCat = new GoodAnimal(safeDate);
        System.out.println("猫的生日: " + goodCat.getBirthDate());
        
        // 外部修改不会影响内部状态
        safeDate.setTime(0);
        System.out.println("外部修改日期后，猫的生日仍是: " + goodCat.getBirthDate());
        
        // 通过 getter 也无法修改内部状态
        goodCat.getBirthDate().setTime(0);
        System.out.println("尝试通过 getter 修改后，猫的生日仍是: " + goodCat.getBirthDate());
        
        System.out.println("\n教训：");
        System.out.println("1. 不要为了避免创建对象而牺牲安全性");
        System.out.println("2. 防御性拷贝的代价 << 安全漏洞的代价");
        System.out.println("3. '避免创建对象'不适用于防御性拷贝场景");
    }
}
