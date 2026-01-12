# 第1条：用静态工厂方法代替构造器

## 五大优势

1. **有名称** - `Item1_Advantage1_NamedMethod.java`
   - 方法名清晰表达创建意图
   - 示例：动物的不同创建方式

2. **不必每次创建新对象** - `Item1_Advantage2_InstanceControl.java`
   - 返回缓存实例，节省内存
   - 示例：动物类型单例

3. **可以返回子类型** - `Item1_Advantage3_ReturnSubtype.java`
   - 隐藏实现类，只暴露接口
   - 示例：动物工厂

4. **返回对象类可随参数变化** - `Item1_Advantage4_VaryByParameter.java`
   - 根据参数返回不同实现
   - 示例：动物喂食策略

5. **返回对象类可以不存在** - `Item1_Advantage5_ServiceProvider.java`
   - 服务提供者框架
   - 示例：动物护理服务

## 两大劣势

**Item1_Disadvantages.java**
- 劣势1：无法子类化（解决：用组合）
- 劣势2：不易识别（解决：遵循命名约定）

## 常用命名约定
- `from` - 类型转换
- `of` - 聚合方法
- `valueOf` - 替代方法
- `getInstance` - 返回实例
- `newInstance` - 创建新实例
