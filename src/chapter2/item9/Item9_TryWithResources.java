package chapter2.item9;

import java.io.*;

/**
 * 演示 Effective Java 第9条：try-with-resources 的优势
 * 
 * 优势1：代码简洁易读
 * 优势2：异常不会被吞没（suppressed exceptions）
 * 优势3：可以结合 catch 子句
 * 
 * @author CN-JeffreyZhou
 */
public class Item9_TryWithResources {

    /**
     * 优势1：单个资源 - 代码简洁
     */
    static String readFirstLine(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    /**
     * 优势2：多个资源 - 代码依然清晰
     * 不需要嵌套，资源会按相反顺序自动关闭
     */
    static void copy(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        }
    }

    /**
     * 优势3：异常不会被吞没
     * 
     * 如果 eat() 和 close() 都抛出异常：
     * - eat() 的异常会被抛出（主异常）
     * - close() 的异常会被 suppressed（抑制），但不会丢失
     * - 可以通过 getSuppressed() 获取被抑制的异常
     */
    static class GoodAnimal implements AutoCloseable {
        public void eat() {
            throw new RuntimeException("吃东西时出错");  // 主异常
        }

        @Override
        public void close() {
            throw new RuntimeException("关闭时出错");    // 被抑制的异常
        }
    }

    static void feedAnimal() {
        try (GoodAnimal animal = new GoodAnimal()) {
            animal.eat();
        }
    }

    /**
     * 优势4：结合 catch 子句，无需嵌套
     */
    static String readFirstLineWithDefault(String path, String defaultVal) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            return defaultVal;  // 优雅地处理异常
        }
    }

    public static void main(String[] args) {
        System.out.println("=== try-with-resources 的优势演示 ===\n");

        // 演示异常不会被吞没
        System.out.println("优势：异常不会被吞没");
        try {
            feedAnimal();
        } catch (Exception e) {
            System.out.println("主异常：" + e.getMessage());
            System.out.println("✅ 看到了 '吃东西时出错'（这是我们真正关心的）");
            
            Throwable[] suppressed = e.getSuppressed();
            if (suppressed.length > 0) {
                System.out.println("\n被抑制的异常：");
                for (Throwable t : suppressed) {
                    System.out.println("  - " + t.getMessage());
                }
                System.out.println("✅ '关闭时出错' 也被保留了，可以查看完整的异常链");
            }
        }

        System.out.println("\n优势：代码简洁，多个资源不需要嵌套");
        System.out.println("优势：可以结合 catch 子句，无需额外嵌套");
    }
}
