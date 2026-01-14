package chapter2.item7;

import java.util.Arrays;

/**
 * 演示 Effective Java 第7条：消除过期的对象引用
 * 
 * 核心原则：自己管理内存时，要警惕内存泄漏
 * 
 * 问题：栈弹出元素后，过期引用仍然存在，导致内存泄漏
 * 
 * @author CN-JeffreyZhou
 */
public class Item7_MemoryLeakStack {

    /**
     * 错误示例：存在内存泄漏的栈实现
     */
    static class BadStack {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_CAPACITY = 16;

        public BadStack() {
            elements = new Object[DEFAULT_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        // 问题：弹出的元素仍被数组引用，无法被垃圾回收
        public Object pop() {
            if (size == 0)
                throw new IllegalStateException("栈为空");
            return elements[--size]; // 内存泄漏！
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    /**
     * 正确示例：消除过期引用的栈实现
     */
    static class GoodStack {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_CAPACITY = 16;

        public GoodStack() {
            elements = new Object[DEFAULT_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        // 正确：清空过期引用
        public Object pop() {
            if (size == 0)
                throw new IllegalStateException("栈为空");
            Object result = elements[--size];
            elements[size] = null; // 消除过期引用
            return result;
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 内存泄漏演示 ===\n");

        // 模拟大对象
        class Animal {
            private final String name;
            private final byte[] data = new byte[1024 * 1024]; // 1MB

            Animal(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        System.out.println("1. 错误示例（BadStack）：");
        BadStack badStack = new BadStack();
        badStack.push(new Animal("狗"));
        badStack.push(new Animal("猫"));
        badStack.push(new Animal("鸟"));
        
        System.out.println("弹出: " + badStack.pop());
        System.out.println("弹出: " + badStack.pop());
        System.out.println("问题：虽然弹出了，但对象仍被数组引用，无法被GC回收\n");

        System.out.println("2. 正确示例（GoodStack）：");
        GoodStack goodStack = new GoodStack();
        goodStack.push(new Animal("狗"));
        goodStack.push(new Animal("猫"));
        goodStack.push(new Animal("鸟"));
        
        System.out.println("弹出: " + goodStack.pop());
        System.out.println("弹出: " + goodStack.pop());
        System.out.println("正确：弹出后立即清空引用，对象可以被GC回收");
    }
}
