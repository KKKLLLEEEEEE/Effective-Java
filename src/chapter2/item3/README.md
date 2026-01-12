# 第3条：用私有构造器或者枚举类型强化Singleton属性

## 核心原则

Singleton（单例）是指仅被实例化一次的类。实现单例有三种方式，其中**枚举是最佳实践**。

## 三种实现方式

### 方式1：公有静态final域
```java
public class Zoo {
    public static final Zoo INSTANCE = new Zoo();
    private Zoo() { }
}
```
**优点**：简洁明了，API清晰表明这是单例  
**缺点**：无法防御反射攻击

### 方式2：静态工厂方法
```java
public class ZooKeeper {
    private static final ZooKeeper INSTANCE = new ZooKeeper();
    private ZooKeeper() { }
    public static ZooKeeper getInstance() { return INSTANCE; }
}
```
**优点**：灵活性高，可以改变实现而不改变API；可以编写泛型单例工厂  
**缺点**：需要额外方法

### 方式3：单元素枚举（推荐）
```java
public enum AnimalShelter {
    INSTANCE;
    public void rescue(String animal) { }
}
```
**优点**：简洁、自动支持序列化、绝对防止多次实例化  
**缺点**：如果必须扩展超类（而不是Enum），则不能使用

## 常见问题

### 1. 反射攻击
可以通过反射调用私有构造器创建新实例，破坏单例。

**解决方案**：
- 在构造器中检查实例是否已存在
- 使用枚举（天然防御）

### 2. 序列化问题
反序列化会创建新实例，破坏单例。

**解决方案**：
- 添加 `readResolve()` 方法
- 使用枚举（天然防御）

## 示例文件

- `Item3_Approach1_PublicField.java` - 方式1：公有静态final域
- `Item3_Approach2_StaticFactory.java` - 方式2：静态工厂方法
- `Item3_Approach3_Enum.java` - 方式3：枚举（最佳实践）
- `Item3_Problems.java` - 常见问题演示
- `Item3_RealWorld.java` - 真实场景：配置管理器

## 关键要点

1. **枚举是实现单例的最佳方式** - 简洁、安全、防御反射和序列化
2. **私有构造器是关键** - 防止外部创建实例
3. **注意序列化问题** - 如果不用枚举，需要实现 `readResolve()`
4. **适用场景** - 配置管理、连接池、日志管理等全局唯一资源

## 运行示例

```bash
# 方式1：公有静态final域
java chapter2.item3.Item3_Approach1_PublicField

# 方式2：静态工厂方法
java chapter2.item3.Item3_Approach2_StaticFactory

# 方式3：枚举（推荐）
java chapter2.item3.Item3_Approach3_Enum

# 常见问题演示
java chapter2.item3.Item3_Problems

# 真实场景
java chapter2.item3.Item3_RealWorld
```
