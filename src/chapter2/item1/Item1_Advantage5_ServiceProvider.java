package chapter2.item1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 演示 Effective Java 第1条：用静态工厂方法代替构造器
 * 
 * 优势5：方法返回的对象所属的类，在编写该静态工厂方法时可以不存在
 * 这是服务提供者框架的基础（如JDBC）
 * 
 * @author CN-JeffreyZhou
 */
public class Item1_Advantage5_ServiceProvider {
    
    interface AnimalService {
        void care();
    }
    
    interface AnimalProvider {
        AnimalService newService();
    }
    
    static class AnimalServiceManager {
        private static final Map<String, AnimalProvider> providers = new ConcurrentHashMap<>();
        
        public static void register(String name, AnimalProvider provider) {
            providers.put(name, provider);
        }
        
        public static AnimalService getService(String name) {
            AnimalProvider provider = providers.get(name);
            if (provider == null) throw new IllegalArgumentException("未注册的服务");
            return provider.newService();
        }
    }
    
    // 以下实现可以在运行时动态加载
    static class DogCareService implements AnimalService {
        @Override
        public void care() {
            System.out.println("遛狗、喂食、洗澡");
        }
    }
    
    static class CatCareService implements AnimalService {
        @Override
        public void care() {
            System.out.println("铲屎、喂食、逗猫");
        }
    }
    
    public static void main(String[] args) {
        // 注册服务提供者
        AnimalServiceManager.register("狗", DogCareService::new);
        AnimalServiceManager.register("猫", CatCareService::new);
        
        // 获取服务
        AnimalService dogService = AnimalServiceManager.getService("狗");
        AnimalService catService = AnimalServiceManager.getService("猫");
        
        System.out.print("狗护理: "); dogService.care();
        System.out.print("猫护理: "); catService.care();
        
        System.out.println("\n关键：AnimalServiceManager编写时，具体实现类可以不存在");
    }
}
