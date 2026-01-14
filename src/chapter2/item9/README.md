# 第9条：try-with-resources 优先于 try-finally

## 核心原则

Java 库中有许多资源需要手动关闭（如 InputStream、OutputStream、java.sql.Connection）。使用 try-with-resources 而不是 try-finally 来管理这些资源。

## try-finally 的问题

### 1. 代码丑陋
- 单个资源还可以接受
- 多个资源需要嵌套，代码难以阅读和维护

### 2. 异常被吞没（最严重的问题）
- 如果 try 块和 finally 块都抛出异常
- finally 块的异常会覆盖 try 块的异常
- 导致调试困难，真正的问题被隐藏

## try-with-resources 的优势

### 1. 代码简洁
- 不需要嵌套
- 多个资源可以在一行声明
- 自动按相反顺序关闭

### 2. 异常不会丢失
- try 块的异常会被抛出（主异常）
- close() 的异常会被 suppressed（抑制）
- 可以通过 getSuppressed() 获取被抑制的异常
- 完整的异常链有助于调试

### 3. 可以结合 catch 子句
- 无需额外嵌套
- 代码更加清晰

## 使用要求

资源类必须实现 `AutoCloseable` 接口（或其子接口 `Closeable`）

```java
public interface AutoCloseable {
    void close() throws Exception;
}
```

## 代码示例

### 示例1：问题演示
[Item9_TryFinallyProblems.java](Item9_TryFinallyProblems.java)
- try-finally 的嵌套问题
- 异常被吞没的问题

### 示例2：优势演示
[Item9_TryWithResources.java](Item9_TryWithResources.java)
- 代码简洁
- 异常不会丢失
- 结合 catch 子句

### 示例3：自定义资源
[Item9_CustomResource.java](Item9_CustomResource.java)
- 实现 AutoCloseable 接口
- 多个资源的关闭顺序

### 示例4：真实场景
[Item9_RealWorld.java](Item9_RealWorld.java)
- 数据库连接管理
- 配置文件加载
- 缓存管理器
- 文件批量处理

## 关键要点

1. **始终优先使用 try-with-resources**，而不是 try-finally
2. **代码更简洁**，更易于阅读和维护
3. **异常处理更好**，不会丢失关键的调试信息
4. **自定义资源类**应该实现 AutoCloseable 接口
5. **多个资源**会按声明的相反顺序关闭

## 实际应用场景

- 数据库连接（Connection、Statement、ResultSet）
- 文件操作（Reader、Writer、Stream）
- 网络连接（Socket、HttpClient）
- 锁资源（Lock、Semaphore）
- 缓存管理（Cache、Session）

## 运行示例

```bash
# 问题演示
java chapter2.item9.Item9_TryFinallyProblems

# 优势演示
java chapter2.item9.Item9_TryWithResources

# 自定义资源
java chapter2.item9.Item9_CustomResource

# 真实场景
java chapter2.item9.Item9_RealWorld
```
