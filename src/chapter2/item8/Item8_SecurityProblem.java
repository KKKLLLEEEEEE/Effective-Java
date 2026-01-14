package chapter2.item8;

/**
 * 演示 Effective Java 第8条：终结方法的安全问题
 * 
 * 核心问题：finalizer 攻击 - 恶意子类可以通过 finalize 复活对象
 * 
 * @author CN-JeffreyZhou
 */
public class Item8_SecurityProblem {

    // ❌ 危险：可被 finalizer 攻击的类
    static class VulnerableAnimalDatabase {
        private String password;
        
        public VulnerableAnimalDatabase(String password) {
            // 验证密码
            if (!"secret123".equals(password)) {
                throw new SecurityException("密码错误！");
            }
            this.password = password;
            System.out.println("数据库访问授权成功");
        }
        
        public void accessData() {
            System.out.println("访问敏感动物数据...");
        }
        
        // 问题：即使构造失败，finalize 仍可能执行
    }
    
    // 恶意子类：利用 finalize 攻击
    static class MaliciousAttack extends VulnerableAnimalDatabase {
        static VulnerableAnimalDatabase stolenInstance;
        
        public MaliciousAttack(String password) {
            super(password); // 故意传错密码，构造失败
        }
        
        @Override
        protected void finalize() {
            // 攻击：即使构造失败，也能通过 finalize 获取对象引用
            stolenInstance = this;
            System.out.println("⚠️ 攻击成功！通过 finalize 窃取了对象引用");
        }
    }
    
    // ✅ 安全：使用 final 类防止子类化
    static final class SecureAnimalDatabase {
        private String password;
        
        public SecureAnimalDatabase(String password) {
            if (!"secret123".equals(password)) {
                throw new SecurityException("密码错误！");
            }
            this.password = password;
            System.out.println("安全数据库访问授权成功");
        }
        
        public void accessData() {
            System.out.println("访问敏感动物数据...");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 演示 Finalizer 攻击 ===\n");
        
        try {
            // 尝试用错误密码创建对象
            new MaliciousAttack("wrong_password");
        } catch (SecurityException e) {
            System.out.println("构造失败：" + e.getMessage());
        }
        
        // 触发 GC，执行 finalize
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 检查是否被攻击
        if (MaliciousAttack.stolenInstance != null) {
            System.out.println("\n危险：即使构造失败，仍可访问对象！");
            MaliciousAttack.stolenInstance.accessData();
        }
        
        System.out.println("\n=== 安全做法 ===");
        System.out.println("1. 使用 final 类防止子类化");
        System.out.println("2. 或在非 final 类中提供空的 final finalize 方法");
    }
}
