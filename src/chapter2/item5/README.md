# 第5条：优先考虑依赖注入来引用资源

## 核心原则

**不要用静态工具类或单例来实现依赖一个或多个底层资源的类，而应该将资源（或创建资源的工厂）传给构造器。**

## 为什么需要依赖注入？

许多类依赖一个或多个底层资源。例如：
- 拼写检查器依赖词典
- 数据访问对象依赖数据库连接
- 配置管理器依赖配置文件

如果用静态工具类或单例实现这些类，会导致：
1. **缺乏灵活性**：无法切换不同的资源（如不同语言的词典）
2. **难以测试**：无法注入 mock 对象进行单元测试
3. **违反单一职责**：类既负责业务逻辑，又负责资源管理

## 代码示例

### 1. 反面示例：静态工具类和单例
- [Item5_BadApproach.java](Item5_BadApproach.java)
- 问题：硬编码依赖，无法灵活切换资源

### 2. 正面示例：依赖注入
- [Item5_GoodApproach.java](Item5_GoodApproach.java)
- 优势：灵活、可测试、支持多实例

### 3. 工厂模式的依赖注入
- [Item5_FactoryPattern.java](Item5_FactoryPattern.java)
- 使用 `Supplier<T>` 延迟创建资源

### 4. 真实场景：拼写检查器
- [Item5_RealWorld.java](Item5_RealWorld.java)
- 书中原例，展示实际应用价值

## 关键要点

1. **依赖注入的本质**：将资源的创建和使用分离
2. **注入方式**：通过构造器传入资源或工厂
3. **适用场景**：类依赖底层资源，且资源会影响类的行为
4. **框架支持**：Spring、Guice 等框架提供自动依赖注入

## 运行示例

```bash
# 反面示例
javac chapter2/item5/Item5_BadApproach.java
java chapter2.item5.Item5_BadApproach

# 正面示例
javac chapter2/item5/Item5_GoodApproach.java
java chapter2.item5.Item5_GoodApproach

# 工厂模式
javac chapter2/item5/Item5_FactoryPattern.java
java chapter2.item5.Item5_FactoryPattern

# 真实场景
javac chapter2/item5/Item5_RealWorld.java
java chapter2.item5.Item5_RealWorld
```

## 总结

依赖注入提升了灵活性、可复用性和可测试性。虽然会增加代码量，但带来的好处远超成本。现代依赖注入框架（如 Spring）可以进一步简化使用。
