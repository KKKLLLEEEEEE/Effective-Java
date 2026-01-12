# 第6条：避免创建不必要的对象

## 核心原则

如果对象是不可变的，它就始终可以被重用。能够重用现有对象而不是创建新对象，通常既能提高性能，又能使代码更加简洁。

## 关键要点

### 1. 重用不可变对象
- **反例**：`String s = new String("狗");` - 每次都创建新对象
- **正例**：`String s = "狗";` - 重用字符串常量池中的对象
- **示例**：[Item6_StringCreation.java](Item6_StringCreation.java)

### 2. 缓存昂贵的对象
- 如果对象创建成本高（如正则表达式的 Pattern），应该缓存重用
- **反例**：`String.matches()` - 每次都创建新的 Pattern
- **正例**：将 Pattern 声明为 `static final` 常量
- **示例**：[Item6_ExpensiveObject.java](Item6_ExpensiveObject.java)

### 3. 警惕自动装箱
- 优先使用基本类型（int, long）而非包装类型（Integer, Long）
- 无意识的自动装箱会创建大量不必要的对象
- **示例**：[Item6_Autoboxing.java](Item6_Autoboxing.java)

### 4. 适配器模式的对象重用
- Map.keySet() 返回的是同一个 Set 视图，无需缓存
- 视图对象是原对象的"窗口"，不是独立副本
- **示例**：[Item6_AdapterPattern.java](Item6_AdapterPattern.java)

### 5. 对象池通常不是好主意
- 除非对象创建非常昂贵（如数据库连接）
- 现代 JVM 的垃圾回收器已经高度优化
- 自己维护对象池通常会使代码更复杂，性能更差

### 6. 不要误用本条目
- **防御性拷贝**是为了安全，**重用对象**是为了性能
- 当需要防御性拷贝时，不重用对象的代价远小于安全漏洞的代价
- **示例**：[Item6_DefensiveCopy.java](Item6_DefensiveCopy.java)

## 性能对比

| 场景 | 错误做法 | 正确做法 | 性能提升 |
|------|---------|---------|---------|
| 字符串创建 | `new String("狗")` | `"狗"` | ~10x |
| 正则验证 | `String.matches()` | 缓存 Pattern | ~5-10x |
| 数值计算 | Long 包装类型 | long 基本类型 | ~6x |

## 真实场景示例

[Item6_RealWorld.java](Item6_RealWorld.java) 展示了：
- 用户输入验证器（邮箱、手机、用户名）
- 统计计算系统
- 如何在实际应用中应用这些原则

## 运行示例

```bash
# 字符串创建对比
javac chapter2/item6/Item6_StringCreation.java
java chapter2.item6.Item6_StringCreation

# 昂贵对象重用
javac chapter2/item6/Item6_ExpensiveObject.java
java chapter2.item6.Item6_ExpensiveObject

# 自动装箱陷阱
javac chapter2/item6/Item6_Autoboxing.java
java chapter2.item6.Item6_Autoboxing

# 适配器模式
javac chapter2/item6/Item6_AdapterPattern.java
java chapter2.item6.Item6_AdapterPattern

# 防御性拷贝
javac chapter2/item6/Item6_DefensiveCopy.java
java chapter2.item6.Item6_DefensiveCopy

# 真实场景
javac chapter2/item6/Item6_RealWorld.java
java chapter2.item6.Item6_RealWorld
```

## 总结

- ✅ 重用不可变对象
- ✅ 缓存创建成本高的对象
- ✅ 优先使用基本类型
- ✅ 理解适配器模式的对象重用
- ❌ 不要为了性能而牺牲安全性（防御性拷贝）
- ❌ 不要盲目使用对象池

**记住**：本条目的重点不是"不要创建对象"，而是"不要创建**不必要**的对象"。
