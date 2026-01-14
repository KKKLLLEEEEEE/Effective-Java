# 第8条：避免使用终结方法和清除方法

## 核心原则

**永远不要使用 finalizer 和 cleaner 作为主要的资源清理手段**

## 为什么要避免？

### 1. 执行时机不确定
- finalize/cleaner 何时执行完全由 GC 决定
- 可能延迟很久才执行，甚至永远不执行
- 不同 JVM 实现行为不同，无法保证一致性

### 2. 性能严重下降
- 使用 finalize 会导致对象创建和销毁慢 50 倍以上
- 增加 GC 负担，影响整体性能

### 3. 安全问题
- **Finalizer 攻击**：恶意子类可以通过 finalize 复活对象
- 即使构造失败，finalize 仍可能执行
- 可能导致未授权访问

## 代码示例

### 示例1：执行时机不确定
[Item8_FinalizerProblems.java](Item8_FinalizerProblems.java)
- 演示 finalize 可能不执行
- 对比 AutoCloseable 的确定性清理

### 示例2：性能影响
[Item8_PerformanceImpact.java](Item8_PerformanceImpact.java)
- 测量使用 finalize 的性能损失
- 通常慢 50 倍以上

### 示例3：安全问题
[Item8_SecurityProblem.java](Item8_SecurityProblem.java)
- 演示 finalizer 攻击
- 展示如何防御（使用 final 类）

### 示例4：正确做法
[Item8_CorrectApproach.java](Item8_CorrectApproach.java)
- 实现 AutoCloseable 接口
- 使用 try-with-resources
- Cleaner 作为安全网

### 示例5：真实场景
[Item8_RealWorld.java](Item8_RealWorld.java)
- 数据库连接管理
- 连接泄漏的危害
- 带安全网的最佳实践

## 正确的资源清理方式

### 1. 实现 AutoCloseable（推荐）
```java
class Resource implements AutoCloseable {
    @Override
    public void close() {
        // 清理资源
    }
}

// 使用 try-with-resources
try (Resource r = new Resource()) {
    // 使用资源
} // 自动调用 close()
```

### 2. Cleaner 作为安全网（Java 9+）
```java
class Resource implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();
    
    private static class State implements Runnable {
        @Override
        public void run() {
            // 安全网清理
        }
    }
    
    private final Cleanable cleanable;
    
    Resource() {
        State state = new State();
        this.cleanable = cleaner.register(this, state);
    }
    
    @Override
    public void close() {
        cleanable.clean(); // 正常清理
    }
}
```

## 防御 Finalizer 攻击

### 方法1：使用 final 类
```java
final class SecureClass {
    // 无法被子类化
}
```

### 方法2：提供空的 final finalize
```java
class SecureClass {
    @Override
    protected final void finalize() throws Throwable {
        // 空实现，防止子类覆盖
    }
}
```

## 何时可以使用 Cleaner？

### 1. 作为安全网
- 客户端忘记调用 close() 时的最后防线
- 应该记录警告日志
- 不能作为主要清理手段

### 2. 管理本地对等体（Native Peer）
- 本地对象不受 GC 管理
- 如果本地对象不持有关键资源
- 但仍建议提供 close() 方法

## 关键要点

1. ✗ **永远不要依赖** finalize 或 cleaner 做主要清理
2. ✓ **优先使用** try-with-resources + AutoCloseable
3. ✓ **Cleaner 只作为安全网**，不是主要手段
4. ✓ **防御 finalizer 攻击**：使用 final 类或 final finalize
5. ✓ **记录警告**：安全网触发时应记录日志

## 运行示例

```bash
# 演示 finalize 问题
java chapter2.item8.Item8_FinalizerProblems

# 性能测试
java chapter2.item8.Item8_PerformanceImpact

# 安全问题
java chapter2.item8.Item8_SecurityProblem

# 正确做法
java chapter2.item8.Item8_CorrectApproach

# 真实场景
java chapter2.item8.Item8_RealWorld
```

## 参考
- Effective Java 第3版 - 第8条
- Java 9+ Cleaner API
- AutoCloseable 接口
