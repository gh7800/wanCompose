## ![](https://img.shields.io/badge/Compose-1.3.2-green)

## wanCompose

### MVI 架构模式
单向数据流：`View` 接收用户操作 → 转为 `Intent` 传给 ViewModel → ViewModel 更新 `State` 驱动 UI 刷新，`Event` 处理一次性副作用

**核心概念：**
- **State** — 页面状态数据模型（data class），暴露给 UI 观察，UI 只读不可修改
- **Intent** — 用户操作意图（sealed class），UI 发送 Intent 触发业务逻辑
- **Event** — 一次性副作用（sealed class），如弹 Toast、跳转页面，仅消费一次，通过 `Channel` 发送避免重复
- **Status** — 网络请求状态枚举，配合 `Resource<T>` 包装结果：`SUCCESS` / `ERROR` / `LOADING`

**数据流：**
```
UI → dispatch(Intent) → ViewModel 处理 → 更新 State → UI 重组
                           ↓
                    发送 Event → UI 消费（如跳转/弹窗）
```

**Channel 与 Flow 的配合：**
- Channel 用于发送一次性事件（`send()`），协程安全、容量有限时自动挂起
- `Channel.receiveAsFlow()` 将事件转为 Flow，供 `LaunchedEffect` 收集消费

**Channel 通道**
- Channel 本质上是一个协程安全的队列，用于在不同协程之间传递数据。它的核心特点是：
- 双向通信：支持发送（send）和接收（receive）操作
- 挂起特性：当通道满时，发送操作会挂起；当通道空时，接收操作会挂起
- 生命周期管理：支持关闭操作，关闭后无法再发送数据 close()

### 生命周期 Effect
- `LaunchedEffect` 协程副作用，页面创建时执行
- `SideEffect` 每帧渲染后执行
- `DisposableEffect` 页面销毁时执行清理

### 布局容器
- `Column` 垂直排列，等同 LinearLayout vertical
- `Row` 水平排列，等同 LinearLayout horizontal
- `Box` 层叠布局，等同 FrameLayout
- `ConstraintLayout` 约束布局，需添加 `implementation "androidx.constraintlayout:constraintlayout-compose:1.0.1"`

```kotlin
// Column / Row / Box
Column(modifier = Modifier.fillMaxSize()) {
    Text("顶部")
    Row {
        Text("左")
        Text("右")
    }
}

// ConstraintLayout
ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    val (title, content) = createRefs()
    Text("标题", modifier = Modifier.constrainAs(title) {
        top.linkTo(parent.top)
        centerHorizontallyTo(parent)
    })
}
```

### Modifier 修饰符
Compose 中所有组件均通过 `Modifier` 配置尺寸、内边距、背景、点击等属性

```kotlin
Modifier
    // 尺寸
    .fillMaxSize()           // 撑满父布局
    .fillMaxWidth()          // 撑满宽度
    .height(48.dp)           // 固定高度
    .size(50.dp)             // 固定宽高

    // 内边距
    .padding(16.dp)          // 四边相同
    .padding(horizontal = 16.dp)  // 水平
    .padding(start = 16.dp, end = 8.dp)  // 各边独立

    // 外边距（通过父容器 Spacer 实现）
    Spacer(modifier = Modifier.width(8.dp))  // 或 Column/Row 的 horizontalArrangement / verticalArrangement

    // 背景 & 形状
    .background(Color.Blue, RoundedCornerShape(8.dp))
    .clip(RoundedCornerShape(50))

    // 位置
    .align(Alignment.Center) // 在 Box/Column 内居中

    // 点击
    .clickable(onClick = { })
    .selectable(selected = true, onClick = { })

    // 组合使用（链式调用）
    Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(Color.Red)
        .clickable(onClick = { })
```

### 常用组件
- `Scaffold` 页面骨架，含 topBar / bottomBar / content
- `TopAppBar` 顶部导航栏
- `BottomNavigation` + `BottomNavigationItem` 底部导航
- `Text` 文本
- `Button` / `IconButton` 按钮
- `TextField` 输入框
- `Surface` 容器卡片，等同 CardView
- `Image` / `Icon` 图片/图标
- `BadgeBox` 角标徽章
- `Spacer` 空白间距
- `Divider` 分割线

```kotlin
// Scaffold + TopAppBar
Scaffold(
    topBar = {
        TopAppBar(
            title = { Text("标题") },
            navigationIcon = {
                IconButton(onClick = { /* 返回 */ }) {
                    Icon(Icons.Default.ArrowBack, "返回")
                }
            },
            actions = {
                IconButton(onClick = { /* 分享 */ }) {
                    Icon(Icons.Default.Share, "分享")
                }
            }
        )
    },
    bottomBar = { /* 底部导航 */ },
    content = { /* 主内容 */ }
)

// BottomNavigation
BottomNavigation {
    items.forEachIndexed { index, item ->
        BottomNavigationItem(
            selected = currentIndex == index,
            label = { Text(item.label) },
            icon = { Icon(item.icon, null) },
            onClick = { currentIndex = index }
        )
    }
}
```

