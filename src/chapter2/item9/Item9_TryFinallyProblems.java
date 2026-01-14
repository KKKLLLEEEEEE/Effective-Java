package chapter2.item9;

import java.io.*;

/**
 * 演示 Effective Java 第9条：try-with-resources 优先于 try-finally
 * 
 * 问题1：try-finally 代码丑陋且难以正确编写
 * 问题2：异常被吞没，难以调试
 * 
 * @author CN-JeffreyZhou
 */
public class Item9_TryFinallyProblems {

    /**
     * 问题1：单个资源的 try-finally 还算可以
     */
    static String readFirstLineOld(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }

    /**
     * 问题2：多个资源的 try-finally 代码丑陋
     * 嵌套的 try-finally 难以阅读和维护
     */
    static void copyOld(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[1024];
                int n;
                while ((n = in.read(buf)) >= 0)
                    out.write(buf, 0, n);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * 问题3：异常被吞没 - 这是最严重的问题！
     * 
     * 场景：假设物理设备故障
     * - readLine() 抛出异常
     * - close() 也抛出异常
     * 结果：第二个异常会完全覆盖第一个异常，导致调试困难
     */
    static class BadAnimal implements Closeable {
        public void eat() {
            throw new RuntimeException("吃东西时出错");  // 第一个异常
        }

        @Override
        public void close() {
            throw new RuntimeException("关闭时出错");    // 第二个异常会覆盖第一个
        }
    }

    static void feedAnimalOld() {
        BadAnimal animal = new BadAnimal();
        try {
            animal.eat();  // 抛出异常
        } finally {
            animal.close();  // 也抛出异常，会覆盖 eat() 的异常
        }
    }

    public static void main(String[] args) {
        System.out.println("=== try-finally 的问题演示 ===\n");

        // 演示异常被吞没
        System.out.println("问题：异常被吞没");
        try {
            feedAnimalOld();
        } catch (Exception e) {
            System.out.println("捕获到的异常：" + e.getMessage());
            System.out.println("❌ 只看到 '关闭时出错'，'吃东西时出错' 被吞没了！");
            System.out.println("这让调试变得非常困难\n");
        }

        System.out.println("多个资源的嵌套 try-finally 代码丑陋且容易出错");
    }
}
