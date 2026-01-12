# 第4条：通过私有构造器强化不可实例化的能力

## 核心原则

有些工具类（utility class）只包含静态方法和静态字段，不应该被实例化。通过提供私有构造器，可以：
1. **防止实例化**：编译器不会生成默认构造器
2. **防止继承**：子类无法调用父类的私有构造器
3. **明确设计意图**：清晰表达这是一个工具类

## 为什么需要私有构造器？

### 问题1：默认构造器
如果不显式提供构造器，编译器会自动生成一个 public 无参构造器，导致类可以被实例化。

### 问题2：抽象类无效
使用抽象类无法阻止实例化，因为可以通过子类化来实例化。

### 解决方案
提供私有构造器，并在内部抛出异常（防止类内部误调用）。

## 代码示例

### 错误示例
- [Item4_BadExample.java](Item4_BadExample.java) - 没有私有构造器的问题

### 正确示例
- [Item4_GoodExample.java](Item4_GoodExample.java) - 使用私有构造器

### 真实场景
- [Item4_RealWorld.java](Item4_RealWorld.java) - 常见工具类（StringUtils、PathUtils、常量类）

## 关键要点

1. **工具类应该有私有构造器**
   ```java
   private UtilityClass() {
       throw new AssertionError();
   }
   ```

2. **副作用：防止继承**
   - 所有构造器都必须调用父类构造器
   - 私有构造器无法被子类访问
   - 因此类无法被继承

3. **抛出异常是可选的**
   - 防止类内部意外调用构造器
   - 提供清晰的错误信息

## 适用场景

- 工具类（只包含静态方法）
- 常量类（只包含静态常量）
- 工厂类的某些实现
- 任何不希望被实例化的类

## 运行示例

```bash
# 错误示例
javac chapter2/item4/Item4_BadExample.java
java chapter2.item4.Item4_BadExample

# 正确示例
javac chapter2/item4/Item4_GoodExample.java
java chapter2.item4.Item4_GoodExample

# 真实场景
javac chapter2/item4/Item4_RealWorld.java
java chapter2.item4.Item4_RealWorld
```
