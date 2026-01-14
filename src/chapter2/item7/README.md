# 第7条：消除过期的对象引用

## 核心原则

**内存泄漏**：程序无意中保持对象引用，导致垃圾回收器无法回收，造成内存占用持续增长。

## 三大常见内存泄漏场景

### 1. 自己管理内存（Item7_MemoryLeakStack.java）

**问题**：类自己管理内存（如数组、集合），过期元素未清空引用

```java
// ❌ 错误：弹出元素后，数组仍持有引用
public Object pop() {
    return elements[--size]; // 内存泄漏！
}

// ✅ 正确：清空过期引用
public Object pop() {
    Object result = elements[--size];
    elements[size] = null; // 消除过期引用
    return result;
}
```

**适用场景**：
- 自定义栈、队列、数组列表
- 对象池
- 任何自己管理存储空间的类

### 2. 缓存（Item7_CacheMemoryLeak.java）

**问题**：缓存中的对象外部不再使用，但缓存仍持有引用

```java
// ❌ 错误：使用 HashMap，缓存永不过期
Map<Key, Value> cache = new HashMap<>();

// ✅ 正确：使用 WeakHashMap，键不再被引用时自动删除
Map<Key, Value> cache = new WeakHashMap<>();
```

**其他解决方案**：
- 使用后台线程定期清理过期条目
- 使用 `LinkedHashMap.removeEldestEntry()` 限制缓存大小
- 使用专业缓存库（如 Guava Cache、Caffeine）

### 3. 监听器和回调（Item7_ListenerMemoryLeak.java）

**问题**：注册监听器后忘记注销，导致监听器无法被回收

```java
// ❌ 错误：只注册，不注销
eventSource.addListener(listener);
// ... 使用完毕后忘记注销

// ✅ 正确：显式注销
eventSource.addListener(listener);
// ... 使用
eventSource.removeListener(listener); // 必须注销
```

**其他解决方案**：
- 使用弱引用存储监听器
- 使用 try-finally 确保注销
- 提供自动注销机制

## 真实场景示例（Item7_RealWorld.java）

**场景**：用户会话管理系统

**问题**：会话过期后只标记状态，未从 Map 中删除，导致内存泄漏

**解决方案**：
1. 过期时立即从 Map 中删除
2. 清理会话内部数据
3. 定期扫描并清理过期会话

## 关键要点

1. **何时需要手动清空引用**：
   - 类自己管理内存时
   - 只有程序员知道哪些引用过期了

2. **不要过度担心**：
   - 大多数情况下，让对象超出作用域即可
   - 清空引用应该是例外，而不是规范

3. **最佳实践**：
   - 尽量在最小作用域内定义变量
   - 使用 WeakHashMap 作为缓存
   - 记得注销监听器和回调
   - 使用专业工具检测内存泄漏（如 VisualVM、JProfiler）

## 运行示例

```bash
# 栈内存泄漏
java chapter2.item7.Item7_MemoryLeakStack

# 缓存内存泄漏
java chapter2.item7.Item7_CacheMemoryLeak

# 监听器内存泄漏
java chapter2.item7.Item7_ListenerMemoryLeak

# 真实场景：会话管理
java chapter2.item7.Item7_RealWorld
```

## 参考

- Effective Java 第3版 - 第7条
- 内存泄漏检测工具：VisualVM、JProfiler、MAT
