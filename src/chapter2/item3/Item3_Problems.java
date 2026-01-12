package chapter2.item3;

import java.lang.reflect.Constructor;
import java.io.*;

/**
 * 演示 Effective Java 第3条：单例模式的常见问题
 * 
 * 展示反射攻击和序列化问题
 * 
 * @author CN-JeffreyZhou
 */
public class Item3_Problems {
    
    // 普通单例（存在问题）
    static class VulnerableZoo implements Serializable {
        public static final VulnerableZoo INSTANCE = new VulnerableZoo();
        
        private VulnerableZoo() {}
        
        public void info() {
            System.out.println("动物园实例：" + this.hashCode());
        }
    }
    
    // 防御反射攻击的单例
    static class SecureZoo {
        public static final SecureZoo INSTANCE = new SecureZoo();
        
        private SecureZoo() {
            // 防御反射攻击
            if (INSTANCE != null) {
                throw new IllegalStateException("单例已存在！");
            }
        }
    }
    
    // 防御序列化问题的单例
    static class SerializableSafeZoo implements Serializable {
        public static final SerializableSafeZoo INSTANCE = new SerializableSafeZoo();
        
        private SerializableSafeZoo() {}
        
        // readResolve方法保证反序列化时返回同一实例
        private Object readResolve() {
            return INSTANCE;
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== 问题1：反射攻击 ===");
        try {
            Constructor<VulnerableZoo> constructor = VulnerableZoo.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            VulnerableZoo fake = constructor.newInstance();
            
            System.out.println("原始实例 == 反射创建: " + (VulnerableZoo.INSTANCE == fake)); // false
        } catch (Exception e) {
            System.out.println("反射攻击失败：" + e.getMessage());
        }
        
        System.out.println("\n=== 问题2：序列化破坏单例 ===");
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(VulnerableZoo.INSTANCE);
        
        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        VulnerableZoo deserialized = (VulnerableZoo) ois.readObject();
        
        System.out.println("原始实例 == 反序列化: " + (VulnerableZoo.INSTANCE == deserialized)); // false
        
        System.out.println("\n=== 解决方案：使用枚举 ===");
        System.out.println("枚举天然防御反射和序列化攻击，是实现单例的最佳方式");
    }
}
