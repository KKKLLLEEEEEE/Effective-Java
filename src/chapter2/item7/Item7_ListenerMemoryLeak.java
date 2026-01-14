package chapter2.item7;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示 Effective Java 第7条：消除过期的对象引用
 * 
 * 核心原则：监听器和回调容易导致内存泄漏
 * 
 * 解决方案：显式注销监听器，或使用弱引用
 * 
 * @author CN-JeffreyZhou
 */
public class Item7_ListenerMemoryLeak {

    /**
     * 动物园（事件源）
     */
    static class Zoo {
        private final List<AnimalListener> listeners = new ArrayList<>();

        // 注册监听器
        public void addListener(AnimalListener listener) {
            listeners.add(listener);
            System.out.println("注册监听器: " + listener.getName());
        }

        // 注销监听器（重要！）
        public void removeListener(AnimalListener listener) {
            listeners.remove(listener);
            System.out.println("注销监听器: " + listener.getName());
        }

        // 触发事件
        public void feedAnimals() {
            System.out.println("\n动物园开始喂食...");
            for (AnimalListener listener : listeners) {
                listener.onFeed();
            }
        }

        public int getListenerCount() {
            return listeners.size();
        }
    }

    /**
     * 监听器接口
     */
    interface AnimalListener {
        void onFeed();
        String getName();
    }

    /**
     * 具体监听器
     */
    static class AnimalObserver implements AnimalListener {
        private final String name;

        AnimalObserver(String name) {
            this.name = name;
        }

        @Override
        public void onFeed() {
            System.out.println(name + " 收到喂食通知");
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 监听器内存泄漏演示 ===\n");

        Zoo zoo = new Zoo();

        System.out.println("1. 错误示例：忘记注销监听器");
        AnimalObserver dogObserver = new AnimalObserver("狗观察者");
        AnimalObserver catObserver = new AnimalObserver("猫观察者");
        
        zoo.addListener(dogObserver);
        zoo.addListener(catObserver);
        zoo.feedAnimals();
        
        System.out.println("\n外部不再使用观察者，但忘记注销...");
        dogObserver = null; // 外部引用清空
        catObserver = null;
        
        System.out.println("监听器数量: " + zoo.getListenerCount());
        System.out.println("问题：Zoo 仍持有监听器引用，导致内存泄漏\n");

        System.out.println("2. 正确示例：显式注销监听器");
        AnimalObserver birdObserver = new AnimalObserver("鸟观察者");
        AnimalObserver fishObserver = new AnimalObserver("鱼观察者");
        
        zoo.addListener(birdObserver);
        zoo.addListener(fishObserver);
        zoo.feedAnimals();
        
        System.out.println("\n不再使用时，显式注销监听器");
        zoo.removeListener(birdObserver);
        zoo.removeListener(fishObserver);
        
        birdObserver = null;
        fishObserver = null;
        
        System.out.println("监听器数量: " + zoo.getListenerCount());
        System.out.println("正确：显式注销后，对象可以被GC回收");
    }
}
