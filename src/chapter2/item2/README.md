# 第2条：遇到多个构造器参数时要考虑使用构建器

## 核心原则

当类的构造器或静态工厂方法有多个参数时，Builder 模式是一个不错的选择，特别是当大多数参数都是可选的时候。

## 三种方案对比

### 1. 重叠构造器模式（Telescoping Constructor Pattern）❌

**问题：**
- 参数多时难以阅读和编写
- 容易搞错参数顺序
- 客户端代码难以理解

```java
// 难以理解这些参数的含义
AnimalNutrition dog = new AnimalNutrition("狗", 3, 500, 20, 30, 10);
```

**示例：** [Item2_TelescopingConstructor.java](Item2_TelescopingConstructor.java)

### 2. JavaBeans 模式 ❌

**问题：**
- 对象在构造过程中可能处于不一致状态
- 无法将类做成不可变的
- 需要额外努力确保线程安全
- 无法保证必需参数被设置

```java
AnimalNutrition dog = new AnimalNutrition();
// 此时对象不完整！
dog.setName("狗");
dog.setAge(3);
```

**示例：** [Item2_JavaBeans.java](Item2_JavaBeans.java)

### 3. Builder 模式 ✅

**优势：**
- 易于阅读和编写
- 对象不可变，线程安全
- 保证必需参数被设置
- 支持流式调用
- 参数含义清晰

```java
AnimalNutrition dog = new AnimalNutrition.Builder("狗", 3)
        .calories(500)
        .protein(30)
        .vitamins(10)
        .build();
```

**示例：** [Item2_Builder.java](Item2_Builder.java)

## 高级用法

### 层次化的 Builder

适用于类层次结构，使用泛型和递归类型参数，让子类的 Builder 能够返回子类型。

**示例：** [Item2_HierarchicalBuilder.java](Item2_HierarchicalBuilder.java)

## 真实场景

### HTTP 请求构建器

展示 Builder 模式在实际开发中的应用，处理多个可选参数（headers, timeout, retry等）。

**示例：** [Item2_Builder_RealWorld.java](Item2_Builder_RealWorld.java)

## 何时使用 Builder

- 类有 4 个或更多参数
- 许多参数是可选的
- 参数类型相同（容易搞混）
- 未来可能添加更多参数

## Builder 的劣势

- 代码量较大
- 创建对象前必须先创建 Builder
- 在性能关键的场景可能有影响

## 关键要点

1. **Builder 模式结合了重叠构造器的安全性和 JavaBeans 的可读性**
2. **对象不可变，线程安全**
3. **适合参数多且大部分可选的场景**
4. **可以用于类层次结构**
5. **代码略显冗长，但收益大于成本**

## 运行示例

```bash
# 重叠构造器模式
javac chapter2/item2/Item2_TelescopingConstructor.java
java chapter2.item2.Item2_TelescopingConstructor

# JavaBeans 模式
javac chapter2/item2/Item2_JavaBeans.java
java chapter2.item2.Item2_JavaBeans

# Builder 模式
javac chapter2/item2/Item2_Builder.java
java chapter2.item2.Item2_Builder

# 层次化 Builder
javac chapter2/item2/Item2_HierarchicalBuilder.java
java chapter2.item2.Item2_HierarchicalBuilder

# 真实场景
javac chapter2/item2/Item2_Builder_RealWorld.java
java chapter2.item2.Item2_Builder_RealWorld
```
