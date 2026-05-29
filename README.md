# AF_SkinChanger (Deprecated)

> 该项目完工于 2025-7，文档内容由 DeepSeek 总结

一个基于 Paper API 的 Minecraft 物品皮肤更换插件，允许玩家在游戏中为物品更换外观皮肤。

## 功能特性

- 🎨 **物品皮肤更换**：通过 GUI 界面为物品更换皮肤外观
- 📖 **皮肤字典系统**：通过 `Dictionary.yml` 配置全局皮肤字典，支持按组分类
- 🔐 **权限控制**：支持为每个皮肤设置独立的权限节点，控制玩家可使用的皮肤
- 📑 **分页浏览**：皮肤选择 GUI 支持分页浏览，每页最多 36 个皮肤
- ⚙️ **灵活配置**：支持自定义 NBT 检测路径、默认展示材料等

## 环境要求

- Minecraft 服务端：**Paper 1.12.2**
- Java 版本：**JDK 1.8+**

## 依赖插件

本插件需要以下前置插件才能正常工作：

| 插件 | 说明 |
|------|------|
| [AzureFlow](https://github.com/rokuko/azureflow) | 自定义物品数据管理 API |
| [NBT-API](https://github.com/tr7zw/Item-NBT-API) | NBT 标签读写 API |

## 安装

1. 确保已安装所有依赖插件
2. 将 `AF_SkinChanger-0.0.1.jar` 放入服务器的 `plugins/` 目录
3. 重启服务器或执行 `plugman load AF_SkinChanger`
4. 编辑 `plugins/AF_SkinChanger/config.yml` 和 `Dictionary.yml` 进行配置

## 指令

主指令：`/afs`、`/af_skin` 或 `/af_skin_changer`

| 指令 | 说明 | 权限 |
|------|------|------|
| `/afs help` | 查看帮助信息 | 无 |
| `/afs skin` 或 `/afs s` | 进入物品皮肤选择模式 | `afsc.gui.open` |
| `/afs reload` 或 `/afs r` | 重载配置文件 | OP |
| `/afs toggle <listener>` | 开关指定监听器 | OP |

### 使用流程

1. 执行 `/afs skin` 进入换肤模式
2. 在物品栏中选择一个支持幻化的物品
3. 在打开的 GUI 界面中选择目标皮肤
4. 物品皮肤即刻更换完成

## 配置文件

### config.yml

```yaml
debug: false                           # 调试模式
default_material: "NAME_TAG"           # GUI 中皮肤项的默认展示材料
default_permission:                    # 默认权限节点
  - "afsc.gui.open"

detectKey: "AzureFlow.data.skin_dic"   # 物品 NBT 中检测字典键的路径
skinKey: "skin"                        # AzureFlow data 层中关联皮肤的键名
permKey: "permission"                  # AzureFlow data 层中关联幻化权限的键名
```

### Dictionary.yml

皮肤字典文件，键名为字典组名，值为皮肤列表：

```yaml
# 纯文本皮肤列表（默认使用命名牌展示）
dic1:
  - "皮肤1"
  - "皮肤2"
  - "皮肤3"

# 简写格式
dic2: ["皮肤1","皮肤2","皮肤3"]

# 带权限的皮肤映射
dic4:
  - "pf1": "use.pf1"
  - "pf2": "use.pf2"
```

- 纯文本皮肤：所有玩家均可选择
- 带权限的皮肤映射（`"皮肤名":"权限节点"`）：只有拥有对应权限的玩家才可选择

## 模块结构

```
src/main/java/org/lozin/af_skinchanger/
├── AF_SkinChanger.java          # 插件主类
├── Dictionary.java              # 皮肤字典管理
├── StringProcessor.java         # 字符串处理接口
├── Cache/                       # 缓存层
│   ├── GUICache.java            # GUI 缓存
│   ├── ItemSkinCache.java       # 物品皮肤缓存
│   ├── NBTCache.java            # NBT 路径缓存
│   └── PlayerCache.java         # 玩家状态缓存
├── Enums/                       # 枚举定义
│   ├── GUI_BACKGROUND.java      # GUI 背景按钮
│   └── LISTENERS.java           # 可切换监听器
├── Event/                       # 事件处理
│   ├── GUIListener.java         # GUI 点击/关闭事件
│   └── HeldEvent.java           # 手持物品切换事件
├── GUI/                         # GUI 界面
│   ├── GUIManager.java          # GUI 渲染管理器
│   ├── GUIService.java          # GUI 服务接口
│   └── SkinGUI.java             # 皮肤选择 GUI
└── Processor/                   # 业务处理层
    ├── Commands.java            # 指令处理
    ├── ConfigReader.java        # 配置文件读取
    ├── ItemProcessor.java       # 物品处理
    ├── NBTReader.java           # NBT 标签读取
    └── PermissionProcessor.java # 权限处理
```

## 权限节点

| 权限节点 | 说明 |
|----------|------|
| `afsc.gui.open` | 允许使用 `/afs skin` 打开换肤界面 |
| OP | 允许使用 `/afs reload` 和 `/afs toggle` |

## 构建

```bash
mvn clean package
```

生成的 JAR 文件位于 `target/` 目录。

## 作者

- **Lozin** — 联系方式：3531557655

## 许可证

本项目仅供学习交流使用。