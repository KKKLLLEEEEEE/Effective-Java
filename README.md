# Effective Java 学习笔记

## 📖 项目简介

本项目是《Effective Java》的学习笔记仓库。由于原书以理论为主，缺少具体代码示例，因此通过 AI 辅助，使用**动物相关的例子**来演示每个条目的核心原则，帮助更好地理解和记忆这些最佳实践。

## 🎯 项目目标

- 为《Effective Java》每个条目提供清晰的代码示例
- 使用统一的动物主题（狗、猫、鸟等）保持示例的一致性和趣味性
- 通过实际代码演示抽象概念，加深理解
- 建立可运行、可验证的学习资料

## 📁 项目结构

```
Effective-Java/
├── src/
│   ├── chapter2/          # 第2章：创建和销毁对象
│   │   └── item1/         # 第1条：用静态工厂方法代替构造器
│   │       ├── Item1_Advantage1_NamedMethod.java
│   │       ├── Item1_Advantage2_InstanceControl.java
│   │       ├── Item1_Advantage3_ReturnSubtype.java
│   │       ├── Item1_Advantage4_VaryByParameter.java
│   │       ├── Item1_Advantage5_ServiceProvider.java
│   │       ├── Item1_Disadvantages.java
│   │       └── README.md
│   └── Main.java
└── .amazonq/
    └── rules/
        └── coding-standards.md  # 编码规范
```

## 🐾 为什么用动物举例？

- **易于理解**：动物是日常熟悉的概念，降低认知负担
- **保持一致**：统一的主题让不同条目之间有连贯性
- **生动有趣**：比抽象的 Foo/Bar 更有记忆点

## 📚 学习进度

### 第2章：创建和销毁对象

- [x] **第1条：用静态工厂方法代替构造器**
  - ✅ 优势1：有名称
  - ✅ 优势2：不必每次创建新对象
  - ✅ 优势3：可以返回子类型
  - ✅ 优势4：返回对象类可随参数变化
  - ✅ 优势5：返回对象类可以不存在
  - ✅ 劣势：无法子类化、不易识别

## 🚀 如何使用

1. **按章节浏览**：进入对应的 `chapter[编号]` 目录
2. **阅读条目说明**：查看每个 item 目录下的 README.md
3. **运行代码示例**：每个 Java 文件都包含 main() 方法，可直接运行
4. **对比学习**：部分示例展示 Good/Bad 对比，理解原则的重要性

## 💡 代码规范

- 每个条目独立成包：`chapter[编号]/item[编号]`
- 文件命名：`Item[编号]_[主题].java`
- 必须使用动物相关示例
- 包含详细的 Javadoc 和注释
- 每个示例都可独立运行

详见：[.amazonq/rules/coding-standards.md](.amazonq/rules/coding-standards.md)

## 📝 示例代码片段

```java
// 优势1：静态工厂方法有名称，更易理解
Animal dog = Animal.newborn("狗", 2.5);      // 清晰：创建新生狗
Animal cat = Animal.adult("猫");             // 清晰：创建成年猫

// 对比构造器：意图不明确
Animal dog = new Animal("狗", 2.5, 0);       // 这些参数是什么意思？
```

## 🤝 贡献

本项目为个人学习笔记，持续更新中。

## 📖 参考资料

- 《Effective Java》第3版 - Joshua Bloch
- 使用 Amazon Q 辅助生成代码示例

---

**作者**: CN-JeffreyZhou  
**最后更新**: 2026年1月
