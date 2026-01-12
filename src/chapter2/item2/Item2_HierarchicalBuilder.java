package chapter2.item2;

/**
 * 演示 Effective Java 第2条：遇到多个构造器参数时要考虑使用构建器
 * 
 * 高级用法：层次化的 Builder（适用于类层次结构）
 * - 使用泛型和递归类型参数
 * - 子类 Builder 返回子类型
 * 
 * @author CN-JeffreyZhou
 */
public class Item2_HierarchicalBuilder {
    
    /**
     * 抽象动物类
     */
    static abstract class Animal {
        final String name;
        final int age;
        
        abstract static class Builder<T extends Builder<T>> {
            private String name;
            private int age;
            
            public T name(String val) {
                name = val;
                return self();
            }
            
            public T age(int val) {
                age = val;
                return self();
            }
            
            abstract Animal build();
            
            // 子类必须重写此方法并返回 this
            protected abstract T self();
        }
        
        Animal(Builder<?> builder) {
            name = builder.name;
            age = builder.age;
        }
    }
    
    /**
     * 狗类 - 添加品种属性
     */
    static class Dog extends Animal {
        private final String breed;
        
        public static class Builder extends Animal.Builder<Builder> {
            private String breed;
            
            public Builder breed(String val) {
                breed = val;
                return this;
            }
            
            @Override
            public Dog build() {
                return new Dog(this);
            }
            
            @Override
            protected Builder self() {
                return this;
            }
        }
        
        private Dog(Builder builder) {
            super(builder);
            breed = builder.breed;
        }
        
        @Override
        public String toString() {
            return String.format("狗[名字:%s, 年龄:%d, 品种:%s]", name, age, breed);
        }
    }
    
    /**
     * 鸟类 - 添加是否会飞属性
     */
    static class Bird extends Animal {
        private final boolean canFly;
        
        public static class Builder extends Animal.Builder<Builder> {
            private boolean canFly = false;
            
            public Builder canFly(boolean val) {
                canFly = val;
                return this;
            }
            
            @Override
            public Bird build() {
                return new Bird(this);
            }
            
            @Override
            protected Builder self() {
                return this;
            }
        }
        
        private Bird(Builder builder) {
            super(builder);
            canFly = builder.canFly;
        }
        
        @Override
        public String toString() {
            return String.format("鸟[名字:%s, 年龄:%d, 会飞:%s]", name, age, canFly ? "是" : "否");
        }
    }
    
    public static void main(String[] args) {
        // 狗的 Builder 返回 Dog.Builder，可以调用 breed()
        Dog dog = new Dog.Builder()
                .name("旺财")
                .age(3)
                .breed("金毛")
                .build();
        System.out.println("层次化Builder - 狗：" + dog);
        
        // 鸟的 Builder 返回 Bird.Builder，可以调用 canFly()
        Bird bird = new Bird.Builder()
                .name("小黄")
                .age(1)
                .canFly(true)
                .build();
        System.out.println("层次化Builder - 鸟：" + bird);
        
        // 优势：每个子类的 Builder 都能流畅地调用父类和自己的方法
    }
}
